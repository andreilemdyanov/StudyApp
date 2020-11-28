package ru.fundamentals.studyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MovieDetailsActivity : AppCompatActivity() {

    private var moviesFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        if (savedInstanceState == null) {
            moviesFragment = FragmentMoviesList.newInstance()
            moviesFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, this, MOVIES_FRAGMENT_TAG)
                    .commit()
            }
        } else {
            moviesFragment =
                supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT_TAG) as? FragmentMoviesList
        }
    }

    companion object {
        const val MOVIES_FRAGMENT_TAG = "moviesFragment"
    }
}