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
        arguments?.apply {
            val movie = getParcelable<Movie>(MOVIE)
            view.findViewById<TextView>(R.id.tv_age_rate).text = movie?.ageRate
            view.findViewById<TextView>(R.id.tv_title_name).text = movie?.title
            view.findViewById<TextView>(R.id.tv_genre).text = movie?.genre
            view.findViewById<TextView>(R.id.tv_counter_reviews).text = movie?.countReviews
            setRating(view, movie?.rating!!)
            actors = movie.actors
        }
        recycler = view.findViewById(R.id.rv_actors_list)
        val adapter = ActorsAdapter(requireContext(), actors)
        recycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    private fun setRating(view: View, numStars: Int) {
        val iv1 = view.findViewById<ImageView>(R.id.iv_star_1)
        val iv2 = view.findViewById<ImageView>(R.id.iv_star_2)
        val iv3 = view.findViewById<ImageView>(R.id.iv_star_3)
        val iv4 = view.findViewById<ImageView>(R.id.iv_star_4)
        val iv5 = view.findViewById<ImageView>(R.id.iv_star_5)
        when (numStars) {
            0 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv2.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv3.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv4.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv5.setImageResource(R.drawable.ic_star_icon_gray_12)
            }
            1 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv2.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv3.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv4.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv5.setImageResource(R.drawable.ic_star_icon_gray_12)
            }
            2 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv2.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv3.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv4.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv5.setImageResource(R.drawable.ic_star_icon_gray_12)
            }
            3 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv2.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv3.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv4.setImageResource(R.drawable.ic_star_icon_gray_12)
                iv5.setImageResource(R.drawable.ic_star_icon_gray_12)
            }
            4 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv2.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv3.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv4.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv5.setImageResource(R.drawable.ic_star_icon_gray_12)
            }
            5 -> {
                iv1.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv2.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv3.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv4.setImageResource(R.drawable.ic_star_icon_pink_12)
                iv5.setImageResource(R.drawable.ic_star_icon_pink_12)
            }

        }

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
