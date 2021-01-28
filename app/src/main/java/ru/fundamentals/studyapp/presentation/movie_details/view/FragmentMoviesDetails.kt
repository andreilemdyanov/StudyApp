package ru.fundamentals.studyapp.presentation.movie_details.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.Actor
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesDetailsBinding
import ru.fundamentals.studyapp.presentation.movie_details.viewmodel.MovieDetailsViewModel
import ru.fundamentals.studyapp.presentation.movie_list.viewmodel.MoviesViewModel
import ru.fundamentals.studyapp.util.setRating
import kotlin.math.abs
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details),
    OnOffsetChangedListener {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)
    private var clickListener: ClickListener? = null
    private var mIsImageHidden = false
    private var mMaxScrollSize = 0
    private val viewModelMovies: MoviesViewModel by activityViewModels()
    private val viewModel: MovieDetailsViewModel by viewModels()
    lateinit var movie: MovieElement.Movie
    private var actors = mutableListOf<Actor>()
    private val adapter = ActorsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onStart() {
        super.onStart()
        viewModel.runTimeDto.observe(viewLifecycleOwner, {
            movie.runtime = it.runTime
            Log.d("M_FragmentMoviesDetails", "movie.runtime = ${movie.runtime}")
            with(binding) {
                if (movie.runtime == 0) tvRuntime.visibility =
                    View.GONE else View.VISIBLE

                tvRuntime.text = context?.getString(R.string.runtime, movie.runtime)
            }
        })

        viewModel.crewList.observe(viewLifecycleOwner, {
            val config = viewModelMovies.config.value!!
            actors.clear()
            it.filter { castItem ->
                castItem.profilePath != "default"
            }.map { castItem ->
                actors.add(
                    Actor(
                        castItem.id,
                        castItem.name,
                        config.imagesDto.secureBaseUrl + config.imagesDto.profileSizes[1] + castItem.profilePath
                    )
                )
            }
            adapter.submitList(actors)
            with(binding) {
                rvActorsList.adapter = adapter
                tvCast.visibility = if (adapter.itemCount > 0) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val movieId = getInt(MOVIE)
            viewModel.getMovieRuntime(movieId)
            viewModel.getMovieCrew(movieId)
            movie = viewModelMovies.getMovie(movieId) as MovieElement.Movie
            with(binding) {
                toolbar.setNavigationOnClickListener { clickListener?.onBackFragmentMoviesListClick() }
                appBar.addOnOffsetChangedListener(this@FragmentMoviesDetails)
                tvMinimumAge.text = getString(R.string.minimum_age, movie.minimumAge)
                collapsingToolbar.title = movie.title
                collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
                collapsingToolbar.setExpandedTitleColor(Color.WHITE)
                tvGenre.text = movie.genres.joinToString { it.name }
                tvNumberOfRatings.text =
                    getString(R.string.number_of_ratings, movie.numberOfRatings)
                tvStoryOverview.text = movie.overview
                ivBackdrop.load(movie.backdrop) {
                    crossfade(true)
                    error(R.color.dark_blue)
                    fallback(R.color.dark_blue)
                }
                rvActorsList.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rvActorsList.addItemDecoration(
                    LinearSpacingItemDecoration(
                        resources.getDimension(R.dimen.linearItemsDist).toInt()
                    )
                )
            }
            setRating(
                view,
                movie.ratings.roundToInt(),
                R.drawable.ic_star_icon_pink_12,
                R.drawable.ic_star_icon_gray_12
            )
        }
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
                ViewCompat.animate(binding.tvMinimumAge).scaleY(0f).scaleX(0f).start()
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false
                ViewCompat.animate(binding.tvMinimumAge).scaleY(1f).scaleX(1f).start()
            }
        }
    }

    companion object {
        private const val PERCENTAGE_TO_SHOW_IMAGE = 10
        private const val MOVIE = "movie"

        @JvmStatic
        fun newInstance(movieId: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply {
                putInt(MOVIE, movieId)
            }
        }
    }

    interface ClickListener {
        fun onBackFragmentMoviesListClick()
    }
}
