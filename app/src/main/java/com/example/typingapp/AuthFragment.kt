package com.example.typingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.typingapp.auth.SigninFragment
import com.example.typingapp.auth.SignupFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {
            val fragments = listOf(SigninFragment(), SignupFragment())
            val adapter = VPAdapter(requireActivity(), fragments)
            val vp = view.findViewById<ViewPager2>(R.id.vp2)
            val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
            vp.adapter = adapter

            TabLayoutMediator(tabLayout, vp) { tab, position ->
                tab.text = when (position) {
                    0 -> "Sign In"
                    1 -> "Sign Up"
                    else -> "Sign In"
                }
            }.attach()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AuthFragment()
    }
}
