package ru.fundamentals.studyapp.util

import android.view.View
import android.widget.ImageView
import ru.fundamentals.studyapp.R

fun setRating(view: View, numStars: Int, iconSelect: Int, iconUnselect: Int) {
    val iv1 = view.findViewById<ImageView>(R.id.iv_star_1)
    val iv2 = view.findViewById<ImageView>(R.id.iv_star_2)
    val iv3 = view.findViewById<ImageView>(R.id.iv_star_3)
    val iv4 = view.findViewById<ImageView>(R.id.iv_star_4)
    val iv5 = view.findViewById<ImageView>(R.id.iv_star_5)
    when (numStars) {
        0 -> {
            iv1.setImageResource(iconUnselect)
            iv2.setImageResource(iconUnselect)
            iv3.setImageResource(iconUnselect)
            iv4.setImageResource(iconUnselect)
            iv5.setImageResource(iconUnselect)
        }
        1 -> {
            iv1.setImageResource(iconSelect)
            iv2.setImageResource(iconUnselect)
            iv3.setImageResource(iconUnselect)
            iv4.setImageResource(iconUnselect)
            iv5.setImageResource(iconUnselect)
        }
        2 -> {
            iv1.setImageResource(iconSelect)
            iv2.setImageResource(iconSelect)
            iv3.setImageResource(iconUnselect)
            iv4.setImageResource(iconUnselect)
            iv5.setImageResource(iconUnselect)
        }
        3 -> {
            iv1.setImageResource(iconSelect)
            iv2.setImageResource(iconSelect)
            iv3.setImageResource(iconSelect)
            iv4.setImageResource(iconUnselect)
            iv5.setImageResource(iconUnselect)
        }
        4 -> {
            iv1.setImageResource(iconSelect)
            iv2.setImageResource(iconSelect)
            iv3.setImageResource(iconSelect)
            iv4.setImageResource(iconSelect)
            iv5.setImageResource(iconUnselect)
        }
        5 -> {
            iv1.setImageResource(iconSelect)
            iv2.setImageResource(iconSelect)
            iv3.setImageResource(iconSelect)
            iv4.setImageResource(iconSelect)
            iv5.setImageResource(iconSelect)
        }
    }
}
