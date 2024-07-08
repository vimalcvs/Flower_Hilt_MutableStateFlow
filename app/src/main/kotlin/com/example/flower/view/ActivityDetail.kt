package com.example.flower.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.flower.R
import com.example.flower.databinding.ActivityDetailBinding
import com.example.flower.model.ModelFlower
import com.example.flower.util.Constant.EXTRA_FLOWER
import com.example.flower.util.Utils.getParcelableExtraCompat

class ActivityDetail : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        val flower: ModelFlower? = intent.getParcelableExtraCompat(EXTRA_FLOWER)
        flower?.let {
            binding.tvTitle.text = flower.name
            binding.toolbar.title = flower.name
            binding.tvDescription.text = flower.description
            binding.ivImage.load(flower.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.app_icon)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
