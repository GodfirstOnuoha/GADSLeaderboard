package com.chibufirst.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chibufirst.gadsleaderboard.databinding.FragmentSkillIqBinding

class SkillIqFragment : Fragment() {

    private lateinit var binding: FragmentSkillIqBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skill_iq, container, false)
        return binding.root
    }

}
