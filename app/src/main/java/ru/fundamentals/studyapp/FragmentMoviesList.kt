package ru.fundamentals.studyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.google.android.material.card.MaterialCardView

class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val movieCard = view.findViewById<MaterialCardView>(R.id.cv_movie_card)
        movieCard.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    FragmentMoviesDetails.newInstance(),
                    MOVIES_LIST_FRAGMENT_TAG
                )
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "FragmentMoviesList"

        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}