package com.chibufirst.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chibufirst.gadsleaderboard.databinding.FragmentLearningLeadersBinding

class LearningLeadersFragment : Fragment() {

    private lateinit var binding: FragmentLearningLeadersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_leaders, container, false)
        return binding.root
    }

}
