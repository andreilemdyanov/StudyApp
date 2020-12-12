package ru.fundamentals.studyapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Actor
import ru.fundamentals.studyapp.ui.ActorsCallback
import ru.fundamentals.studyapp.util.loadImageRoundCorners

class ActorsAdapter(
    context: Context,
) : ListAdapter<Actor, ActorHolder>(ActorsCallback()) {

    private var inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
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
        loadImageRoundCorners(image, actor.picture, R.drawable.ic_placeholder_actor_24)
    }
}