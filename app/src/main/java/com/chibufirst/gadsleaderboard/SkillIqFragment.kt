package com.chibufirst.gadsleaderboard

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chibufirst.gadsleaderboard.adapter.SkillIqRecyclerAdapter
import com.chibufirst.gadsleaderboard.databinding.FragmentSkillIqBinding
import com.chibufirst.gadsleaderboard.models.SkillIq
import com.chibufirst.gadsleaderboard.services.LeaderService
import com.chibufirst.gadsleaderboard.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SkillIqFragment : Fragment() {

    private lateinit var binding: FragmentSkillIqBinding
    private var requestList: List<SkillIq> = emptyList()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skill_iq, container, false)

        binding.recyclerSkillIqLeaders.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerSkillIqLeaders.setHasFixedSize(true)

        makeNetworkRequest()

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        lifecycleScope.launch {
                            connectionAvailable(true)
                        }
                    }

                    override fun onLost(network: Network) {
                        lifecycleScope.launch {
                            connectionAvailable(false)
                        }
                    }
                }
            )
        }

        return binding.root
    }

    private fun makeNetworkRequest() {
        binding.textNetworkCheck.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        val leaderService: LeaderService = ServiceBuilder().buildService(LeaderService::class.java)
        val skillIqRequest: Call<List<SkillIq>> = leaderService.getLeadersSkillIq()
        skillIqRequest.enqueue(object : Callback<List<SkillIq>> {
            override fun onFailure(call: Call<List<SkillIq>>, t: Throwable) {
                requestList = emptyList()
                if (t is IOException) {
                    binding.textNetworkCheck.visibility = View.VISIBLE
                } else {
                    binding.textNetworkCheck.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<SkillIq>>, response: Response<List<SkillIq>>) {
                requestList = response.body()!!
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    binding.recyclerSkillIqLeaders.adapter = response.body()?.let {
                        SkillIqRecyclerAdapter(
                            it
                        )
                    }
                    if (context != null) {
                        val animation: LayoutAnimationController =
                            AnimationUtils.loadLayoutAnimation(
                                context,
                                R.anim.layout_animation_fall_down
                            )
                        binding.recyclerSkillIqLeaders.layoutAnimation = animation
                    }
                }
            }

        })
    }

    private suspend fun connectionAvailable(isConnected: Boolean) {
        withContext(Dispatchers.Main) {
            if (isConnected) {
                if (requestList.isEmpty()) {
                    makeNetworkRequest()
                }
            }
        }
    }

}
