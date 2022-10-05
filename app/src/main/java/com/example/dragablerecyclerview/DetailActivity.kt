package com.example.dragablerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dragablerecyclerview.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSports = intent.getParcelableExtra<Sports>(EXTRA_DATA)

        supportActionBar?.title = dataSports?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.apply {
            Glide.with(this@DetailActivity)
                .load(dataSports?.image)
                .into(imgPhoto)
            binding.tvSportInfo.text = dataSports?.info
            binding.tvNews.text = dataSports?.news
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}