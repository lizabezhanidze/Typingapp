package com.example.typingapp.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation

object AnimationHelper {
    fun staggerAnimateViews(
        views: List<View>,
        initialDelayMs: Long = 0,
        staggerDelayMs: Long = 150
    ) {
        views.forEachIndexed { index, view ->
            animateItem(
                view = view,
                delayMs = initialDelayMs + (index * staggerDelayMs)
            )
        }
    }

    fun animateItem(
        view: View,
        delayMs: Long = 0,
        slideFromY: Float = 200f,
        overshootTension: Float = 1.5f
    ) {
        view.visibility = View.INVISIBLE

        val slideAnim = TranslateAnimation(
            0f, 0f,
            slideFromY, 0f
        ).apply {
            duration = 250
            interpolator = OvershootInterpolator(overshootTension)
        }

        val fadeAnim = AlphaAnimation(0f, 1f).apply {
            duration = 250
        }

        view.visibility = View.VISIBLE

        AnimationSet(false).apply {
            addAnimation(slideAnim)
            addAnimation(fadeAnim)
            startOffset = delayMs
        }.also { view.startAnimation(it) }
    }
}
