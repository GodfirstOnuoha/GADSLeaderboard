package com.chibufirst.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chibufirst.gadsleaderboard.adapter.SkillIqRecyclerAdapter
import com.chibufirst.gadsleaderboard.databinding.FragmentSkillIqBinding
import com.chibufirst.gadsleaderboard.models.SkillIq
import com.chibufirst.gadsleaderboard.services.LeaderService
import com.chibufirst.gadsleaderboard.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SkillIqFragment : Fragment() {

    private lateinit var binding: FragmentSkillIqBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skill_iq, container, false)

        binding.recyclerSkillIqLeaders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerSkillIqLeaders.setHasFixedSize(true)

        val leaderService: LeaderService = ServiceBuilder().buildService(LeaderService::class.java)
        val skillIqRequest: Call<List<SkillIq>> = leaderService.getLeadersSkillIq()
        skillIqRequest.enqueue(object : Callback<List<SkillIq>> {
            override fun onFailure(call: Call<List<SkillIq>>, t: Throwable) {
                Toast.makeText(context, "Failed to retrieve.", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<SkillIq>>, response: Response<List<SkillIq>>) {
                if (response.isSuccessful) {
                    binding.recyclerSkillIqLeaders.adapter = response.body()?.let {
                        SkillIqRecyclerAdapter(
                            it
                        )
                    }
                }
                binding.progressBar.visibility = View.GONE
            }

        })

        return binding.root
    }

}
