package com.samedtemiz.kotlinmaps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samedtemiz.kotlinmaps.databinding.RecyclerRowBinding
import com.samedtemiz.kotlinmaps.model.Place
import com.samedtemiz.kotlinmaps.view.MapsActivity

class PlaceAdapter(val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.PlaceHolder>() {

    class PlaceHolder(val recyclerRowBinding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(recyclerRowBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val recyclerRowBinding =
            RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PlaceHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.recyclerRowBinding.recyclerTextView.text = placeList.get(position).placeName
        holder.itemView.setOnClickListener{

            val intent  = Intent(holder.itemView.context, MapsActivity::class.java)
            intent.putExtra("place", placeList.get(position))
            intent.putExtra("info", "old")
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}