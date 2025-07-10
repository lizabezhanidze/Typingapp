package com.example.typingapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.typingapp.MainActivity
import com.example.typingapp.R
import com.example.typingapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupFragment : Fragment() {
    private val auth = Firebase.auth
    lateinit var binding : FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Enter Email and Password!")
                return@setOnClickListener
            }
            if (password.length < 6) {
                showToast("Password too short!")
                return@setOnClickListener
            }
            if (password != etPassword2.text.toString()) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            (requireActivity() as MainActivity).onLoginSuccess()
                        }
                    }
                } else {
                    showToast("Invalid Credentials!")
                }
            }
        }
    }

    private fun showToast(label: String) {
        Toast.makeText(requireContext(), label, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}
