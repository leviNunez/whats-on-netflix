package com.android.course.whatsonnetflix.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.course.whatsonnetflix.R
import timber.log.Timber

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("Hello from MoviesFragment")
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

}