package ru.fundamentals.studyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Actor
import ru.fundamentals.studyapp.data.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesDetailsBinding
import ru.fundamentals.studyapp.ui.adapters.ActorsAdapter
import ru.fundamentals.studyapp.util.loadImage
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
            val movie = getParcelable<MovieElement.Movie>(MOVIE)
            binding.tvMinimumAge.text = getString(R.string.minimum_age, movie?.minimumAge)
            binding.collapsingToolbar.title = movie?.title
            binding.tvGenre.text = movie?.genres?.joinToString { it.name }
            binding.tvNumberOfRatings.text =
                getString(R.string.number_of_ratings, movie?.numberOfRatings)
            binding.tvStoryOverview.text = movie?.overview
            loadImage(binding.ivBackdrop, movie?.backdrop!!)
            setRating(
                view,
                movie.ratings.roundToInt(),
                R.drawable.ic_star_icon_pink_12,
                R.drawable.ic_star_icon_gray_12
            )
            actors = movie.actors
            binding.tvCast.visibility = if (movie.actors.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.rvActorsList.setHasFixedSize(true)
        binding.rvActorsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorsAdapter(requireContext())
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
