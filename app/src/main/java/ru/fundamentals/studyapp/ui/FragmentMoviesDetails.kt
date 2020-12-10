package ru.fundamentals.studyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Actor
import ru.fundamentals.studyapp.data.Movie
import ru.fundamentals.studyapp.ui.adapters.ActorsAdapter
import ru.fundamentals.studyapp.util.loadImage
import ru.fundamentals.studyapp.util.setRating
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment() {

    private var recycler: RecyclerView? = null
    private var clickListener: ClickListener? = null

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false).apply {
            findViewById<TextView>(R.id.tv_back).apply {
                setOnClickListener {
                    clickListener?.onBackFragmentMoviesListClick()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actors: List<Actor> = emptyList()
        val tvAge = view.findViewById<TextView>(R.id.tv_minimum_age)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title_name)
        val tvGenre = view.findViewById<TextView>(R.id.tv_genre)
        val tvNumRatings = view.findViewById<TextView>(R.id.tv_number_of_ratings)
        val tvOverview = view.findViewById<TextView>(R.id.tv_story_overview)
        val tvCast = view.findViewById<TextView>(R.id.tv_cast)
        val ivBackdrop = view.findViewById<ImageView>(R.id.iv_backdrop)

        arguments?.apply {
            val movie = getParcelable<Movie>(MOVIE)
            tvAge.text = getString(R.string.minimum_age, movie?.minimumAge)
            tvTitle.text = movie?.title
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
        recycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler?.adapter = ActorsAdapter(requireContext(), actors)
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    companion object {
        private const val MOVIE = "movie"

        @JvmStatic
        fun newInstance(movie: Movie) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE, movie)
            }
        }
    }

    interface ClickListener {
        fun onBackFragmentMoviesListClick()
    }
}
