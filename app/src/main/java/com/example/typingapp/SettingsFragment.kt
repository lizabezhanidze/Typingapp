package com.example.typingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.core.content.edit
import com.example.typingapp.utils.AnimationHelper

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val db = FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timeSpinner = view.findViewById<Spinner>(R.id.time_spinner)
        val diffSpinner = view.findViewById<Spinner>(R.id.difficulty_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_item)
            timeSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.difficulty_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_item)
            diffSpinner.adapter = adapter
        }

        val popupBackground = ContextCompat.getDrawable(requireContext(), R.drawable.spinner_popup_background)
        timeSpinner.setPopupBackgroundDrawable(popupBackground)
        diffSpinner.setPopupBackgroundDrawable(popupBackground)

        val sharedItemListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val settings = requireActivity().getSharedPreferences("Settings", 0)
                val selectedValueString = parent.getItemAtPosition(position).toString()

                when (parent.id) {
                    R.id.time_spinner -> {
                        val timeLong = selectedValueString.toLongOrNull()
                        if (timeLong != null) {
                            settings.edit {
                                putLong("Time", timeLong)
                            }
                            Log.d("SettingsFragment", "Time saved: $timeLong")
                        } else {
                            Log.e("SettingsFragment", "Invalid time value: $selectedValueString")
                        }
                    }
                    R.id.difficulty_spinner -> {
                        val selectedDifficultyEnum = DifficultyLevel.fromString(selectedValueString)
                        val difficultyValue = selectedDifficultyEnum.value

                        settings.edit {
                            putInt("Difficulty", difficultyValue)
                        }
                        Log.d("SettingsFragment", "Difficulty saved: $selectedValueString")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("TimeSpinner", "Nothing selected.")
            }
        }

        timeSpinner.onItemSelectedListener = sharedItemListener
        diffSpinner.onItemSelectedListener = sharedItemListener

        setupAnimations(view)
    }

    private fun setupAnimations(view: View) {
        val viewsToAnimate = listOf<View>(
            view.findViewById(R.id.logo),
            view.findViewById(R.id.settings),
            view.findViewById(R.id.subheading),
            view.findViewById(R.id.gameSettingsList),
            view.findViewById(R.id.audioSettings),
            view.findViewById(R.id.notiSettings),
        )

        AnimationHelper.staggerAnimateViews(viewsToAnimate, 20L, 100L)
    }
}
