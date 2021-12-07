package com.masai.sainath.themovieapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.sainath.themovieapp.R
import com.masai.sainath.themovieapp.adapters.MovieAdapter
import com.masai.sainath.themovieapp.databinding.ActivityMainBinding
import com.masai.sainath.themovieapp.model.Movie
import com.masai.sainath.themovieapp.viewmodels.MovieViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieAdapter.Interaction {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var layoutManager: GridLayoutManager
    lateinit var binding: ActivityMainBinding


    /**
     * using paging for loading and proccesing the data
     * This operator is usually used with  [onCompletion] and [catch] operators to process all emitted values and
     * handle an exception that might occur in the upstream flow or during processing
     */

    @InternalCoroutinesApi
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieAdapter = MovieAdapter(this)
        layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)  // TODO: 15-08-2020   should set the span counnt as per device width
       binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = movieAdapter

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        lifecycleScope.launch {
            movieViewModel.movieData.collect {
                if (this@MainActivity::movieAdapter.isInitialized) {
                    movieAdapter.submitData(it)
                }
            }
        }
    }

    override fun onItemSelected(position: Int, item: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", item)
        startActivity(intent)
    }
}