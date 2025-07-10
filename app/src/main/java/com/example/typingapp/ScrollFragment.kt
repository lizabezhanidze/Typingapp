package com.example.typingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.typingapp.databinding.FragmentScrollBinding
import com.example.typingapp.dataclasses.RVItem

val CAT_IMAGES = listOf<String>(
    "https://unsplash.com/photos/yMSecCHsIBc/download?ixid=M3wxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNzUyMDk4NjM3fA&force=true",
    "https://unsplash.com/photos/o6RbK3y7mK4/download?ixid=M3wxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNzUyMDk5Njk5fA&force=true",
    "https://images.unsplash.com/photo-1541781774459-bb2af2f05b55?q=80&w=1160&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1616792039102-024474062c2b?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1596455127833-73c14fbef2e5?q=80&w=1167&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1664037178354-2e118062c450?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
)

val WORD_FILE_NAME = "CatWords.txt"

class ScrollFragment : Fragment(R.layout.fragment_scroll) {
    lateinit var binding: FragmentScrollBinding
    lateinit var adapter: RVMainAdapter

    private var lastCatUrl: String = CAT_IMAGES[0]
    private val list = arrayListOf<RVItem>(RVItem(lastCatUrl))

    private var currentWord: String = ""
    lateinit var words: List<String>

    private fun getRandomCat(): String {
        val availableCats = CAT_IMAGES.filter { it != lastCatUrl }
        return if (availableCats.isNotEmpty()) {
            availableCats.random().also { lastCatUrl = it }
        } else {
            CAT_IMAGES.random().also { lastCatUrl = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun loadWords(): List<String> {
        return try {
            requireContext().assets.open(WORD_FILE_NAME).use { stream ->
                stream.bufferedReader().use { reader ->
                    reader.readLines()
                        .filter { it.isNotBlank() }
                        .map { it.lowercase().trim() }
                }
            }
        } catch (e: Exception) {
            Log.e("WordLoad", "Error loading words: ${e.message}")
            listOf("fallback", "words", "if", "file", "missing")
        }
    }

    private fun showNewRandomWord() {
        currentWord = words.random()
        binding.label.text = currentWord
    }

    private fun addCatImage() {
        val newList = ArrayList(list).apply {
            add(RVItem(getRandomCat()))
        }
        adapter.submitList(newList) {
            binding.rvList.smoothScrollToPosition(adapter.itemCount - 1)
        }
        list.clear()
        list.addAll(newList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        words = loadWords()
//
        adapter = RVMainAdapter()
        rvList.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, true)
        rvList.adapter = adapter

        wordInput.addTextChangedListener {
            val typedText = it.toString()

            if (typedText.endsWith(' ')) {
                Log.d("ScrollFragment", "Word typed: $typedText")
                if (typedText.trim() == currentWord) {
                    addCatImage()
                    showNewRandomWord()
                } else {
                    wordInput.error = "Try again!"
                }
                wordInput.text.clear()
            }
        }

        adapter.submitList(list)
    }
}
