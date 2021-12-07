package com.masai.sainath.themovieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.masai.sainath.themovieapp.model.MovieDb
import com.masai.sainath.themovieapp.model.MovieRemoteMediator
import com.masai.sainath.themovieapp.utils.MyApplication

class MovieViewModel : ViewModel() {

    /**
    Paging is a part of the Android Jetpack and is used to load and display
    small amounts of data from the remote server.
     By doing so, it reduces the use of network bandwidth
     */

    @ExperimentalPagingApi
    val movieData = Pager(PagingConfig(20), remoteMediator = MovieRemoteMediator()) {
        MovieDb.invoke(MyApplication.context).movieDao().getAllMoviesPaged()
    }.flow
}