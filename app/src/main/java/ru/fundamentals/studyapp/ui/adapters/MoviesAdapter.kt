package ru.fundamentals.studyapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Movie
import ru.fundamentals.studyapp.util.setRating
import java.lang.IllegalArgumentException

class MoviesAdapter(
    context: Context,
    var movies: List<Movie>,
    private val clickListener: OnRecyclerMovieClicked
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var inflater = LayoutInflater.from(context)

    fun isHeader(position: Int) = position == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderHolder(
                inflater.inflate(
                    R.layout.view_holder_movie_header,
                    parent,
                    false
                )
            )
            TYPE_MOVIE -> MovieHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is HeaderHolder -> holder.bind()
            is MovieHolder -> {
                holder.bind(getItem(position))
                holder.itemView.setOnClickListener {
                    clickListener.onClick(getItem(position))
                }
            }
        }
    }

    override fun getItemCount(): Int = movies.size + 1

    private fun getItem(position: Int): Movie = movies[position - 1]

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_MOVIE
        }
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_MOVIE = 1
    }

    interface OnRecyclerMovieClicked {
        fun onClick(movie: Movie)
    }
}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieHolder(itemView: View) : BaseViewHolder(itemView) {
    private val preview = itemView.findViewById<ImageView>(R.id.iv_movie_preview)
    private val ageRate = itemView.findViewById<TextView>(R.id.tv_age_rate)
    private val genre = itemView.findViewById<TextView>(R.id.tv_genre)
    private val countReviews = itemView.findViewById<TextView>(R.id.tv_counter_reviews)
    private val title = itemView.findViewById<TextView>(R.id.tv_title_name)
    private val duration = itemView.findViewById<TextView>(R.id.tv_duration)

    fun bind(movie: Movie) {
        preview.setImageResource(movie.preview)
        ageRate.text = movie.ageRate
        genre.text = movie.genre
        countReviews.text = movie.countReviews
        title.text = movie.title
        duration.text = movie.duration
        setRating(
            itemView,
            movie.rating,
            R.drawable.ic_star_icon_pink_8,
            R.drawable.ic_star_icon_gray_8
        )
    }


}

class HeaderHolder(itemView: View) : BaseViewHolder(itemView) {
    private val image = itemView.findViewById<ImageView>(R.id.iv_target)
    private val header = itemView.findViewById<TextView>(R.id.tv_header)

    fun bind() {
        image.setImageResource(R.drawable.ic_combined_shape)
        header.text = "Movies List"
    }
}

