package com.chibufirst.gadsleaderboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chibufirst.gadsleaderboard.adapter.ViewPagerAdapter
import com.chibufirst.gadsleaderboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(
            LearningLeadersFragment(),
            getString(R.string.learning_leaders)
        )
        viewPagerAdapter.addFragment(SkillIqFragment(), getString(R.string.skill_iq_leaders))

        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.textSubmit.setOnClickListener {
            startActivity(Intent(this, ProjectSubmissionActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }
}
