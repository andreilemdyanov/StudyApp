package ru.fundamentals.studyapp.presentation.movie_list.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesListBinding
import ru.fundamentals.studyapp.presentation.movie_list.viewmodel.MoviesViewModel
import java.net.UnknownHostException

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var clickListener: ClickListener? = null
    private val viewModel: MoviesViewModel by activityViewModels()
    private val binding by viewBinding(FragmentMoviesListBinding::bind)
    private val adapter by lazy { MoviesAdapter(clickListenerItem) }
    lateinit var job: Job

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvMoviesList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MoviesLoaderStateAdapter(),
                footer = MoviesLoaderStateAdapter()
            )
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
            adapter.addLoadStateListener { state: CombinedLoadStates ->
                rvMoviesList.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
        }
    }

    override fun onStart() {
        super.onStart()
        job = CoroutineScope(Dispatchers.Main).launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_FragmentMoviesList", "$exception")
            when (exception) {
                is UnknownHostException -> Snackbar.make(
                    binding.root,
                    "Нет соединения с интернетом",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }) {
            viewModel.moviesPs.collectLatest {
                adapter.submitData(it)
            }
        }
        viewModel.error.observe(
            this.viewLifecycleOwner
        ) {
            when (it) {
                is UnknownHostException -> Snackbar.make(
                    binding.root,
                    "Нет соединения с интернетом",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
        job.cancel()
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



