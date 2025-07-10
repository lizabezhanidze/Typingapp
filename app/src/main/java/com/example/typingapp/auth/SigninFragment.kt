package com.example.typingapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.typingapp.MainActivity
import com.example.typingapp.R
import com.example.typingapp.databinding.FragmentSigninBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninFragment : Fragment() {
    private val auth = Firebase.auth
    lateinit var binding : FragmentSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        signInBtn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Email and Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    (requireActivity() as MainActivity).onLoginSuccess()
                } else {
                    Toast.makeText(requireContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SigninFragment()
    }
}
