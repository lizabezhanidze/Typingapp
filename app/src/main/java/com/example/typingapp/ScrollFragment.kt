package com.example.typingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.typingapp.databinding.FragmentScrollBinding
import com.example.typingapp.dataclasses.RVItem

class ScrollFragment : Fragment(R.layout.fragment_scroll) {
    lateinit var binding: FragmentScrollBinding
    lateinit var adapter: RVMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RVMainAdapter()
        rvList.layoutManager = GridLayoutManager(context, 3)
        rvList.adapter = adapter

        val list = (1..50).map { i ->
            RVItem(
//                "https://images.unsplash.com/photo-1743527133813-36f6bb63ddd5?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "HELLO WORLD!",
            )
        }

        adapter.submitList(list)
    }
}
