package com.example.flower.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.flower.R
import com.example.flower.databinding.ActivityMainBinding
import com.example.flower.util.Constant.EXTRA_FLOWER
import com.example.flower.view.adapter.AdapterFlower
import com.example.flower.viewmodel.ViewModelFlower
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelFlower: ViewModelFlower by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterFlower()
        binding.recyclerView.adapter = adapter


        LinearSnapHelper().attachToRecyclerView(binding.recyclerView)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFlower.flowerData.collectLatest { dataList ->
                    adapter.setData(dataList)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFlower.isLoading.collectLatest { isLoading ->
                    binding.pvProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFlower.isNoNetwork.collectLatest { isNoNetwork ->
                    binding.tvNoNetwork.visibility = if (isNoNetwork) View.VISIBLE else View.GONE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFlower.isEmpty.collectLatest { isEmpty ->
                    binding.tvNoData.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
                }
            }
        }

        viewModelFlower.fetchFlower()

        adapter.setOnItemClickListener { flower ->
            Intent(this, ActivityDetail::class.java).apply {
                putExtra(EXTRA_FLOWER, flower)
                startActivity(this)
            }
        }
    }
}
