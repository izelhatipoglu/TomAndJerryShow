package com.izelhatipoglu.tomandjerryshow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.izelhatipoglu.tomandjerryshow.model.Pictures
import com.izelhatipoglu.tomandjerryshow.R
import kotlinx.android.synthetic.main.recycler_row.view.*


class JerryAdapter(private var jerryList : ArrayList<Pictures>, val onItemClick : (Pictures) -> Unit) : RecyclerView.Adapter<JerryAdapter.JerryViewHolder>() {


    class JerryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var photo : ImageView = view.jerryView
        fun bind(model : Pictures, onItemClick: (Pictures) -> Unit){
            itemView.setOnClickListener {
                onItemClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JerryViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent,false)
        return JerryViewHolder(view)
    }

    override fun onBindViewHolder(holder: JerryViewHolder, position: Int) {
        holder.photo.setImageResource(jerryList[position].image)
        holder.bind(jerryList[position],onItemClick)

    }

    override fun getItemCount(): Int = jerryList.size
}