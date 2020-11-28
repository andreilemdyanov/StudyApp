package ru.fundamentals.studyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.TransitionInflater

class FragmentMoviesDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        val btnBack = view.findViewById<TextView>(R.id.tv_back)
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesDetails()
    }
}