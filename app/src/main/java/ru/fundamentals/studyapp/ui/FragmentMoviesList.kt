package ru.fundamentals.studyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.MovieElement
import ru.fundamentals.studyapp.data.loadMovies
import ru.fundamentals.studyapp.databinding.FragmentMoviesDetailsBinding
import ru.fundamentals.studyapp.databinding.FragmentMoviesListBinding
import ru.fundamentals.studyapp.ui.adapters.MoviesAdapter


class FragmentMoviesList : Fragment(R.layout.fragment_movies_list), Observer<List<MovieElement>> {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MoviesViewModel
    lateinit var adapter: MoviesAdapter
    private var clickListener: ClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.moviesList.observe(this.viewLifecycleOwner, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(requireContext(), clickListenerItem)
        binding.rvMoviesList.adapter = adapter
        val manager =
            GridLayoutManager(requireContext(), resources.getInteger(R.integer.grid_count))
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.isHeader(position)) manager.spanCount else 1
            }
        }
        binding.rvMoviesList.layoutManager = manager
        binding.rvMoviesList.setHasFixedSize(true)
    }

    override fun onChanged(t: List<MovieElement>?) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.submitList(viewModel.moviesList.value)
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doOnClick(movie: MovieElement.Movie) {
        binding.rvMoviesList.let {
            clickListener?.onMoviesDetailsClick(movie)
        }
    }

    private val clickListenerItem = object : MoviesAdapter.OnRecyclerMovieClicked {
        override fun onClick(movie: MovieElement) {
            if (movie is MovieElement.Movie)
                doOnClick(movie)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }

    interface ClickListener {
        fun onMoviesDetailsClick(movie: MovieElement.Movie)
    }
}



