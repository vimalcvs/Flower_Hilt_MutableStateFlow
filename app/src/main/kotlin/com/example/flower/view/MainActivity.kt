package com.example.flower.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flower.R
import com.example.flower.databinding.ActivityMainBinding
import com.example.flower.util.Constant.EXTRA_FLOWER
import com.example.flower.view.adapter.AdapterFlower
import com.example.flower.viewmodel.ViewModelFlower
import dagger.hilt.android.AndroidEntryPoint

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
        viewModelFlower.flowerData.observe(this) { dataList ->
            adapter.setData(dataList)
        }

        viewModelFlower.isLoading.observe(this) { isLoading ->
            binding.pvProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModelFlower.isNoNetwork.observe(this) { isNoNetwork ->
            binding.tvNoNetwork.visibility = if (isNoNetwork) View.VISIBLE else View.GONE
        }

        viewModelFlower.isEmpty.observe(this) { isEmpty ->
            binding.tvNoData.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
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
