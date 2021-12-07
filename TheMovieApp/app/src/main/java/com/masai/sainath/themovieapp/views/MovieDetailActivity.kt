package com.masai.sainath.themovieapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.masai.sainath.themovieapp.R
import com.masai.sainath.themovieapp.databinding.ActivityMovieDetailBinding
import com.masai.sainath.themovieapp.model.Movie
import com.masai.sainath.themovieapp.restclients.RestClient

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movie: Movie
    lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            movie = intent.getSerializableExtra("movie") as Movie
        }

        if (this::movie.isInitialized) {
            val stringUrl = RestClient.ORIGINAL_IMG_BASE_URL + movie.posterPath
            Glide.with(this)
                .load(stringUrl)
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                )
                .into(binding.ivMovieImage)
            binding.tvMovieName.text = movie.title
            binding.tvSummaryDescription.text = movie.overview
            binding.tvPremiere.text = " 160 mins | Premiered on ${movie.releaseDate}"


        }
    }
}