package com.chibufirst.gadsleaderboard.services

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SubmissionService {

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    fun submitProject(
        @Field("entry.1824927963") email_address: String,
        @Field("entry.1877115667") first_name: String,
        @Field("entry.2006916086") last_name: String,
        @Field("entry.284483984") project_link: String
    ): Call<Void>

}