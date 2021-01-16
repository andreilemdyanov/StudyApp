package ru.fundamentals.studyapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.MovieElement

class MovieDetailsActivity : AppCompatActivity(), FragmentMoviesList.ClickListener,
    FragmentMoviesDetails.ClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

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

    override fun onMoviesDetailsClick(movie: MovieElement.Movie) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                FragmentMoviesDetails.newInstance(movie)
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