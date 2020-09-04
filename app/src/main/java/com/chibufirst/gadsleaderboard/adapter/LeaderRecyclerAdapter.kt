package com.chibufirst.gadsleaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chibufirst.gadsleaderboard.R
import com.chibufirst.gadsleaderboard.models.Learning

class LeaderRecyclerAdapter(learning: List<Learning>) :
    RecyclerView.Adapter<LeaderRecyclerAdapter.LeaderViewHolder>() {

    private val mLearning: List<Learning> = learning

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderViewHolder {
        return LeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.leaders_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mLearning.size
    }

    override fun onBindViewHolder(holder: LeaderViewHolder, position: Int) {
        val learningLeaders: Learning = mLearning[position]
        holder.bind(learningLeaders)
    }

    class LeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageLeaders: ImageView = itemView.findViewById(R.id.image_leaders)
        private val textLeadersName: TextView = itemView.findViewById(R.id.text_leaders_name)
        private val textLeadersDesc: TextView = itemView.findViewById(R.id.text_leaders_desc)

        fun bind(learning: Learning) {
            Glide.with(itemView.context)
                .load(learning.badgeUrl)
                .into(imageLeaders)
            textLeadersName.text = learning.name
            val desc = "${learning.hours} learning hours, ${learning.country}."
            textLeadersDesc.text = desc
        }
    }
}