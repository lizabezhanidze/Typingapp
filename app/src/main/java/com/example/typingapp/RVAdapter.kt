package com.example.typingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.typingapp.R
import com.example.typingapp.databinding.RvItemMinBinding
import com.example.typingapp.dataclasses.RVItem

class RVMainAdapter : ListAdapter<RVItem, RVMainAdapter.Holder>(Comparator()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_min, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(
        holder: Holder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val binding = RvItemMinBinding.bind(view)
        fun bind(item: RVItem) = with(binding) {
            item.text = "Hello World"
            imgMain.setImageResource(R.drawable.logo)
//            pin.setImageDrawable(item.postType)
//            Glide.with(itemView.context).load(item.postImage).into(imgMain)
        }
    }

    class Comparator: DiffUtil.ItemCallback<RVItem>() {
        override fun areItemsTheSame(
            oldItem: RVItem,
            newItem: RVItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RVItem,
            newItem: RVItem
        ): Boolean {
            return oldItem == newItem
        }

    }
}
