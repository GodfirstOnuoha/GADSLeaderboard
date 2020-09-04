package com.chibufirst.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chibufirst.gadsleaderboard.adapter.LeaderRecyclerAdapter
import com.chibufirst.gadsleaderboard.databinding.FragmentLearningLeadersBinding
import com.chibufirst.gadsleaderboard.models.Learning
import com.chibufirst.gadsleaderboard.services.LeaderService
import com.chibufirst.gadsleaderboard.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LearningLeadersFragment : Fragment() {

    private lateinit var binding: FragmentLearningLeadersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_leaders, container, false)

        binding.recyclerLearningLeaders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerLearningLeaders.setHasFixedSize(true)

        val leaderService: LeaderService = ServiceBuilder().buildService(LeaderService::class.java)
        val leaderRequest: Call<List<Learning>> = leaderService.getLeadersLearningHours()
        leaderRequest.enqueue(object : Callback<List<Learning>> {
            override fun onFailure(call: Call<List<Learning>>, t: Throwable) {
                Toast.makeText(context, "Failed to retrieve. ${t.message}", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<Learning>>, response: Response<List<Learning>>) {
                if (response.isSuccessful) {
                    binding.recyclerLearningLeaders.adapter = response.body()?.let {
                        LeaderRecyclerAdapter(
                            it
                        )
                    }
                }
                else {
                    Toast.makeText(context, "Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            }

        })

        return binding.root
    }

}
