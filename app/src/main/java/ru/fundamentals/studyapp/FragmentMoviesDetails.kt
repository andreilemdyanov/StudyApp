package ru.fundamentals.studyapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.TransitionInflater

class FragmentMoviesDetails : Fragment() {

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

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesDetails()
    }

    interface ClickListener {
        fun onBackFragmentMoviesListClick()
    }
}