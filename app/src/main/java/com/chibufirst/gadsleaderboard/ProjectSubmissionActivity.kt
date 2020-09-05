package com.chibufirst.gadsleaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.chibufirst.gadsleaderboard.databinding.ActivityProjectSubmissionBinding

class ProjectSubmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSubmissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_submission)

        binding.imageBack.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.textSubmitProject.setOnClickListener { validateInputs() }
    }

    private fun validateInputs() {
        when {
            TextUtils.isEmpty(binding.editFirstName.text) -> {
                binding.editFirstName.error = getString(R.string.error_text)
                binding.editFirstName.requestFocus()
            }
            TextUtils.isEmpty(binding.editLastName.text) -> {
                binding.editLastName.error = getString(R.string.error_text)
                binding.editLastName.requestFocus()
            }
            TextUtils.isEmpty(binding.editEmailAddress.text) -> {
                binding.editEmailAddress.error = getString(R.string.error_text)
                binding.editEmailAddress.requestFocus()
            }
            TextUtils.isEmpty(binding.editProjectLink.text) -> {
                binding.editProjectLink.error = getString(R.string.error_text)
                binding.editProjectLink.requestFocus()
            }
            else -> {
                showConfirmDialog()
            }
        }
    }

    private fun showConfirmDialog() {
        TODO("Not yet implemented")
    }
}
