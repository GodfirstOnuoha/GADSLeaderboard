package com.chibufirst.gadsleaderboard.services

import com.chibufirst.gadsleaderboard.models.Learning
import com.chibufirst.gadsleaderboard.models.SkillIq
import retrofit2.Call
import retrofit2.http.GET

interface LeaderService {

    @GET("api/hours")
    fun getLeadersLearningHours(): Call<List<Learning>>

    @GET("api/skilliq")
    fun getLeadersSkillIq(): Call<List<SkillIq>>

}