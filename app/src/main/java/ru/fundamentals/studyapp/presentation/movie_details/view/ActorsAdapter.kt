package ru.fundamentals.studyapp.presentation.movie_details.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.models.Actor
import ru.fundamentals.studyapp.presentation.movie_details.view.ActorsDiffCallback

class ActorsAdapter : ListAdapter<Actor, ActorHolder>(ActorsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ActorHolder(inflater.inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ActorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name = itemView.findViewById<TextView>(R.id.tv_name_actor)
    private val image = itemView.findViewById<ImageView>(R.id.iv_photo_actor)

    fun bind(actor: Actor) {
        name.text = actor.name
        image.load(actor.picture) {
            error(R.drawable.ic_baseline_person_24)
            fallback(R.drawable.ic_baseline_person_24)
            transformations(RoundedCornersTransformation(10f))
            crossfade(true)
        }
    }
}