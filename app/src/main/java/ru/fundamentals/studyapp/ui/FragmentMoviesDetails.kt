package ru.fundamentals.studyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Actor
import ru.fundamentals.studyapp.data.MovieElement
import ru.fundamentals.studyapp.ui.adapters.ActorsAdapter
import ru.fundamentals.studyapp.util.loadImage
import ru.fundamentals.studyapp.util.setRating
import kotlin.math.abs
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details),
    OnOffsetChangedListener {

    private var recycler: RecyclerView? = null
    private var clickListener: ClickListener? = null

    private var tvAge: TextView? = null
    private var mIsImageHidden = false
    private var mMaxScrollSize = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actors: List<Actor> = emptyList()
        tvAge = view.findViewById(R.id.tv_minimum_age)
        val tvTitle = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val tvGenre = view.findViewById<TextView>(R.id.tv_genre)
        val tvNumRatings = view.findViewById<TextView>(R.id.tv_number_of_ratings)
        val tvOverview = view.findViewById<TextView>(R.id.tv_story_overview)
        val tvCast = view.findViewById<TextView>(R.id.tv_cast)
        val ivBackdrop = view.findViewById<ImageView>(R.id.iv_backdrop)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { clickListener?.onBackFragmentMoviesListClick() }
        view.findViewById<AppBarLayout>(R.id.app_bar).addOnOffsetChangedListener(this)
        arguments?.apply {
            val movie = getParcelable<MovieElement.Movie>(MOVIE)
            tvAge?.text = getString(R.string.minimum_age, movie?.minimumAge)
            tvTitle.title = movie?.title
            tvGenre.text = movie?.genres?.joinToString { it.name }
            tvNumRatings.text = getString(R.string.number_of_ratings, movie?.numberOfRatings)
            tvOverview.text = movie?.overview
            loadImage(ivBackdrop, movie?.backdrop!!)
            setRating(
                view,
                movie.ratings.roundToInt(),
                R.drawable.ic_star_icon_pink_12,
                R.drawable.ic_star_icon_gray_12
            )
            actors = movie.actors
            tvCast.visibility = if (movie.actors.isEmpty()) View.GONE else View.VISIBLE
        }
        recycler = view.findViewById(R.id.rv_actors_list)
        recycler?.setHasFixedSize(true)
        recycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorsAdapter(requireContext())
        adapter.submitList(actors)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout!!.totalScrollRange

        val currentScrollPercentage: Int = (abs(verticalOffset) * 100
                / mMaxScrollSize)

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true
                ViewCompat.animate(tvAge!!).scaleY(0f).scaleX(0f).start()
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false
                ViewCompat.animate(tvAge!!).scaleY(1f).scaleX(1f).start()
            }
        }
    }

    companion object {
        private const val PERCENTAGE_TO_SHOW_IMAGE = 10
        private const val MOVIE = "movie"

        @JvmStatic
        fun newInstance(movie: MovieElement.Movie) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE, movie)
            }
        }
    }

    interface ClickListener {
        fun onBackFragmentMoviesListClick()
    }
}
