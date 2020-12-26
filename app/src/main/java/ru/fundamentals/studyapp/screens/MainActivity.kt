package ru.fundamentals.studyapp.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.api.ApiHelper
import ru.fundamentals.studyapp.data.api.ApiServiceImpl
import ru.fundamentals.studyapp.data.Repository
import ru.fundamentals.studyapp.screens.movie_details.FragmentMoviesDetails
import ru.fundamentals.studyapp.screens.movie_list.FragmentMoviesList
import ru.fundamentals.studyapp.util.APP_ACTIVITY

class MainActivity : AppCompatActivity(), FragmentMoviesList.ClickListener,
    FragmentMoviesDetails.ClickListener {

    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        APP_ACTIVITY = this
        viewModel = ViewModelProvider(
            APP_ACTIVITY, ViewModelFactory(
                Repository(
                    ApiHelper(
                        ApiServiceImpl()
                    )
                )
            )
        ).get(MoviesViewModel::class.java)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    FragmentMoviesList.newInstance(),
                    MOVIES_FRAGMENT_TAG
                )
                .commit()
        else supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT_TAG) as? FragmentMoviesList
    }

    override fun onMoviesDetailsClick(movieId: Long) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                FragmentMoviesDetails.newInstance(movieId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onBackFragmentMoviesListClick() {
        supportFragmentManager.popBackStack()
    }

    companion object {
        const val MOVIES_FRAGMENT_TAG = "moviesFragment"
    }
}