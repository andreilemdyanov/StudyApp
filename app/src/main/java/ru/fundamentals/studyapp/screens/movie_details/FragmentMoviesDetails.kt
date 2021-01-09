package ru.fundamentals.studyapp.screens.movie_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.Actor
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesDetailsBinding
import ru.fundamentals.studyapp.screens.MainActivity
import ru.fundamentals.studyapp.util.setRating
import kotlin.math.abs
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details),
    OnOffsetChangedListener {

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private var clickListener: ClickListener? = null
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actors: List<Actor> = emptyList()
        binding.toolbar.setNavigationOnClickListener { clickListener?.onBackFragmentMoviesListClick() }
        binding.appBar.addOnOffsetChangedListener(this)
        arguments?.apply {
            val movieId = getInt(MOVIE)
            val movie =
                (activity as MainActivity).viewModel.getMovie(movieId) as MovieElement.Movie
            binding.tvMinimumAge.text = getString(R.string.minimum_age, movie.minimumAge)
            binding.collapsingToolbar.title = movie.title
            binding.tvGenre.text = movie.genres.joinToString { it.name }
            binding.tvNumberOfRatings.text =
                getString(R.string.number_of_ratings, movie.numberOfRatings)
            binding.tvStoryOverview.text = movie.overview
            binding.ivBackdrop.load(movie.backdrop) {
                crossfade(true)
                error(R.color.dark_blue)
                fallback(R.color.dark_blue)
            }
            setRating(
                view,
                movie.ratings.roundToInt(),
                R.drawable.ic_star_icon_pink_12,
                R.drawable.ic_star_icon_gray_12
            )
            binding.tvRuntime.text = context?.getString(R.string.runtime, movie.runtime)
//            actors = movie.actors
//            binding.tvCast.visibility = if (movie.actors.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.rvActorsList.setHasFixedSize(true)
        binding.rvActorsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorsAdapter()
        adapter.submitList(actors)
        binding.rvActorsList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.motionLayout.transitionToEnd()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
