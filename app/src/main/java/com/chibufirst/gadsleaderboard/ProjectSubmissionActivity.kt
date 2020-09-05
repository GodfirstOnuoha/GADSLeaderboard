package com.chibufirst.gadsleaderboard

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.chibufirst.gadsleaderboard.databinding.ActivityProjectSubmissionBinding
import com.chibufirst.gadsleaderboard.services.SubmissionService
import com.chibufirst.gadsleaderboard.services.SubmissionServiceBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.confirm_dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectSubmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSubmissionBinding
    private var networkIsAvailable: Boolean = false

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_submission)

        binding.imageBack.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.textSubmitProject.setOnClickListener { validateInputs() }

        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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
            !Patterns.EMAIL_ADDRESS.matcher(binding.editEmailAddress.text).matches() -> {
                binding.editEmailAddress.error = getString(R.string.valid_email)
                binding.editEmailAddress.requestFocus()
            }
            TextUtils.isEmpty(binding.editProjectLink.text) -> {
                binding.editProjectLink.error = getString(R.string.error_text)
                binding.editProjectLink.requestFocus()
            }
            else -> {
                if (networkIsAvailable) {
                    showConfirmDialog()
                }
                else {
                    Snackbar.make(binding.root, getString(R.string.no_connection), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showConfirmDialog() {
        binding.linearForm.visibility = View.GONE
        val confirmDialogView: View = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(confirmDialogView)
            .setCancelable(false)
        val alertDialog: AlertDialog = builder.show()
        confirmDialogView.image_cancel.setOnClickListener {
            binding.linearForm.visibility = View.VISIBLE
            alertDialog.dismiss()
        }
        confirmDialogView.text_yes.setOnClickListener {
            alertDialog.dismiss()
            submitForm()
        }
    }

    private fun submitForm() {
        val loadingDialogView: View = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null)
        val loadingBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(loadingDialogView)
            .setCancelable(false)
        val loadingAlertDialog = loadingBuilder.show()

        val submissionService = SubmissionServiceBuilder().buildService(SubmissionService::class.java)
        val projectSubmission: Call<Void> = submissionService.submitProject(
            binding.editEmailAddress.text.toString(),
            binding.editFirstName.text.toString(),
            binding.editLastName.text.toString(),
            binding.editProjectLink.text.toString()
        )
        projectSubmission.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                loadingAlertDialog.dismiss()
                showFailureDialog()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                loadingAlertDialog.dismiss()
                if (response.isSuccessful) {
                    showSuccessDialog()
                    clearFields()
                }
            }

        })
    }

    private fun clearFields() {
        binding.editFirstName.text = null
        binding.editLastName.text = null
        binding.editEmailAddress.text = null
        binding.editProjectLink.text = null
    }

    private fun showSuccessDialog() {
        val successDialogView: View = LayoutInflater.from(this).inflate(R.layout.success_dialog, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(successDialogView)
        builder.setOnDismissListener {
            binding.linearForm.visibility = View.VISIBLE
        }
        builder.show()
    }

    private fun showFailureDialog() {
        binding.linearForm.visibility = View.INVISIBLE
        val successDialogView: View = LayoutInflater.from(this).inflate(R.layout.failure_dialog, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(successDialogView)
        builder.setOnDismissListener {
            binding.linearForm.visibility = View.VISIBLE
        }
        builder.show()
    }

    private suspend fun connectionAvailable(isConnected: Boolean) {
        withContext(Dispatchers.Main) {
            networkIsAvailable = isConnected
        }
    }
}
