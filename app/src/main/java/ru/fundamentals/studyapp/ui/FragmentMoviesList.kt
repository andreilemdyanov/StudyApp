package ru.fundamentals.studyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Movie
import ru.fundamentals.studyapp.ui.adapters.MoviesAdapter
import ru.fundamentals.studyapp.util.DataUtil


class FragmentMoviesList : Fragment() {

    private var recycler: RecyclerView? = null
    private var clickListener: ClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener)
            clickListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rv_movies_list)
        val movies = DataUtil.generateMovies()
        val adapter = MoviesAdapter(requireContext(), movies, clickListenerItem)
        val manager =
            GridLayoutManager(requireContext(), resources.getInteger(R.integer.grid_count))
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.isHeader(position)) manager.spanCount else 1
            }
        }
        recycler?.layoutManager = manager
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    private fun doOnClick(movie: Movie) {
        recycler?.let {
            clickListener?.onMoviesDetailsClick(movie)
        }
    }

    private val clickListenerItem = object : MoviesAdapter.OnRecyclerMovieClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }

    interface ClickListener {
        fun onMoviesDetailsClick(movie: Movie)
    }
}



