package ru.fundamentals.studyapp.presentation.movie_list.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesListBinding
import ru.fundamentals.studyapp.presentation.movie_list.viewmodel.MoviesViewModel

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var clickListener: ClickListener? = null
    private val viewModel: MoviesViewModel by activityViewModels()
    private val binding by viewBinding(FragmentMoviesListBinding::bind)
    private val adapter by lazy { MoviesAdapter(clickListenerItem) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvMoviesList.adapter = adapter
            val manager =
                GridLayoutManager(requireContext(), resources.getInteger(R.integer.grid_count))
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.isHeader(position)) manager.spanCount else 1
                }
            }

            rvMoviesList.addItemDecoration(
                GridSpacingItemDecoration(
                    resources.getDimension(R.dimen.gridItemsDist).toInt()
                )
            )
            rvMoviesList.layoutManager = manager
        }
    }

    override fun onStart() {
        super.onStart()
       CoroutineScope(Dispatchers.Main).launch {
            viewModel.moviesPs.collectLatest {
                adapter.submitData(it)
            }
        }
        viewModel.error.observe(
            this.viewLifecycleOwner
        ) {
            Toast.makeText(requireContext(), it.e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    private fun doOnClick(movie: MovieElement) {
        binding.rvMoviesList.let {
            clickListener?.onMoviesDetailsClick(movie)
        }
    }

    private val clickListenerItem = { movie: MovieElement ->
        if (movie is MovieElement.Movie)
            doOnClick(movie)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }

    interface ClickListener {
        fun onMoviesDetailsClick(movie: MovieElement)
    }
}



