package com.example.typingapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter(fa: FragmentActivity, val list: List<Fragment>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): Fragment = list[position]
}