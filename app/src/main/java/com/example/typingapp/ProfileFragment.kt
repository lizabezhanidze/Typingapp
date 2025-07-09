package com.example.typingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.androidplot.xy.*
import com.example.typingapp.utils.AnimationHelper

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("Settings", 0)
        val scores = prefs.getString("Scores", "50,75,50")!!
        val scoreArray = scores.split(',')

        view.findViewById<TextView>(R.id.avgwpmLabel).text = getAverageWPM(scoreArray).toString()
        view.findViewById<TextView>(R.id.bestwpmLabel).text = getBestWPM(scoreArray).toString()
        view.findViewById<TextView>(R.id.totalLabel).text = getTotalRounds(scoreArray).toString()

        initAnims(view)
    }

    private fun initAnims(view: View) {
        val viewsToAnimate = listOf<View>(
            view.findViewById(R.id.logo),
            view.findViewById(R.id.heading),
            view.findViewById(R.id.subheading),
            view.findViewById(R.id.avgwpm),
            view.findViewById(R.id.bestwpm),
            view.findViewById(R.id.accuracy),
            view.findViewById(R.id.total),
        )

        AnimationHelper.staggerAnimateViews(viewsToAnimate, 20L, 100L)
    }

    private fun getTotalRounds(scores: List<String>): Int = scores.size
    private fun getBestWPM(scores: List<String>): Int = scores.max().toInt()
    private fun getAverageWPM(scores: List<String>): Int = scores.sumOf { i -> i.toInt() } / scores.size
}