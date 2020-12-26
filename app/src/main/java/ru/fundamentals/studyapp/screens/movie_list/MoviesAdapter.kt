package ru.fundamentals.studyapp.screens.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.util.APP_ACTIVITY
import ru.fundamentals.studyapp.util.loadImageRoundCorners
import ru.fundamentals.studyapp.util.setRating
import kotlin.math.roundToInt

class MoviesAdapter(
    private val clickListener: OnRecyclerMovieClicked
) : ListAdapter<MovieElement, ViewHolder>(MoviesCallback()) {

    private var inflater = LayoutInflater.from(APP_ACTIVITY)

    fun isHeader(position: Int) = position == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Type.HEADER.ordinal -> ViewHolder.Header(
                inflater.inflate(
                    R.layout.view_holder_movie_header,
                    parent,
                    false
                )
            )
            Type.MOVIE.ordinal -> ViewHolder.Movie(
                inflater.inflate(
                    R.layout.view_holder_movie,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.Header -> holder.bind()
            is ViewHolder.Movie -> {
                holder.bind(getItem(position) as MovieElement.Movie)
                holder.itemView.setOnClickListener {
                    clickListener.onClick(getItem(position))
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> Type.HEADER.ordinal
            else -> Type.MOVIE.ordinal
        }
    }

    enum class Type {
        HEADER,
        MOVIE
    }

    interface OnRecyclerMovieClicked {
        fun onClick(movie: MovieElement)
    }
}

sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class Movie(itemView: View) : ViewHolder(itemView) {
        private val context = itemView.context
        private val preview = itemView.findViewById<ImageView>(R.id.iv_movie_preview)
        private val minimumAge = itemView.findViewById<TextView>(R.id.tv_minimum_age)
        private val genres = itemView.findViewById<TextView>(R.id.tv_genre)
        private val numberOfRatings = itemView.findViewById<TextView>(R.id.tv_number_of_ratings)
        private val title = itemView.findViewById<TextView>(R.id.tv_title_name)
        private val runtime = itemView.findViewById<TextView>(R.id.tv_runtime)

        fun bind(movie: MovieElement.Movie) {
            loadImageRoundCorners(preview, movie.poster, R.drawable.ic_placeholder_movies_24)
            minimumAge.text = context.getString(R.string.minimum_age, movie.minimumAge)
            genres.text = movie.genres.joinToString { it.name }
            numberOfRatings.text =
                context.getString(R.string.number_of_ratings, movie.numberOfRatings)
            title.text = movie.title
            runtime.text = context.getString(R.string.runtime, movie.runtime)
            setRating(
                itemView,
                movie.ratings.roundToInt(),
                R.drawable.ic_star_icon_pink_8,
                R.drawable.ic_star_icon_gray_8
            )
        }
    }

    class Header(itemView: View) : ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.iv_target)
        private val header = itemView.findViewById<TextView>(R.id.tv_header)

        fun bind() {
            image.setImageResource(R.drawable.ic_combined_shape)
            header.text = "Movies List"
        }
    }
}

