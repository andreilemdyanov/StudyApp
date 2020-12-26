package ru.fundamentals.studyapp.screens.movie_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.databinding.FragmentMoviesListBinding
import ru.fundamentals.studyapp.screens.MainActivity

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list),
    Observer<Map<Long, MovieElement>> {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: MoviesAdapter
    private var clickListener: ClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(clickListenerItem)
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

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).viewModel.moviesList.observe(
            this.viewLifecycleOwner,
            this
        )
    }

    override fun onChanged(t: Map<Long, MovieElement>?) {
        adapter.submitList((activity as MainActivity).viewModel.moviesList.value?.values?.toList())
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doOnClick(movieId: Long) {
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
        fun onMoviesDetailsClick(movieId: Long)
    }
}



