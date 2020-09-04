package com.chibufirst.gadsleaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chibufirst.gadsleaderboard.R
import com.chibufirst.gadsleaderboard.models.SkillIq

class SkillIqRecyclerAdapter(skillIqList: List<SkillIq>) :
    RecyclerView.Adapter<SkillIqRecyclerAdapter.SkillIqViewHolder>() {

    private val mSkillIqList: List<SkillIq> = skillIqList
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillIqViewHolder {
        return SkillIqViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.leaders_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mSkillIqList.size
    }

    override fun onBindViewHolder(holder: SkillIqViewHolder, position: Int) {
        val skillIq: SkillIq = mSkillIqList[position]
        holder.bind(skillIq)
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = 500
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }

    class SkillIqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageLeaders: ImageView = itemView.findViewById(R.id.image_leaders)
        private val textLeadersName: TextView = itemView.findViewById(R.id.text_leaders_name)
        private val textLeadersDesc: TextView = itemView.findViewById(R.id.text_leaders_desc)

        fun bind(skillIq: SkillIq) {
            Glide.with(itemView.context)
                .load(skillIq.badgeUrl)
                .into(imageLeaders)
            textLeadersName.text = skillIq.name
            val desc = "${skillIq.score} Skill IQ Score, ${skillIq.country}."
            textLeadersDesc.text = desc
        }
    }
}