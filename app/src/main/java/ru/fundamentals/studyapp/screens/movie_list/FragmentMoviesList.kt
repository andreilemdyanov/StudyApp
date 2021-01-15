package ru.fundamentals.studyapp.screens.movie_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesListBinding
import ru.fundamentals.studyapp.screens.MoviesViewModel

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    lateinit var adapter: MoviesAdapter
    private var clickListener: ClickListener? = null
    private val viewModel: MoviesViewModel by activityViewModels()
    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(clickListenerItem)
        with(binding) {
            rvMoviesList.adapter = adapter
            rvMoviesList.adapter = adapter
            val manager =
                GridLayoutManager(requireContext(), resources.getInteger(R.integer.grid_count))
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.isHeader(position)) manager.spanCount else 1
                }
            }
            rvMoviesList.layoutManager = manager
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.moviesList.observe(
            this.viewLifecycleOwner, {
                adapter.submitList(viewModel.moviesList.value?.values?.toList())
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    private fun doOnClick(movieId: Int) {
        binding.rvMoviesList.let {
            clickListener?.onMoviesDetailsClick(movieId)
        }
    }

    private val clickListenerItem = object : MoviesAdapter.OnRecyclerMovieClicked {
        override fun onClick(movie: MovieElement) {
            if (movie is MovieElement.Movie)
                doOnClick(movie.id)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }

    interface ClickListener {
        fun onMoviesDetailsClick(movieId: Int)
    }
}



