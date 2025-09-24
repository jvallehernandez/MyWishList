package com.example.mywishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    private val items: MutableList<WishlistItem>,
    private val onClick: (WishlistItem, View) -> Unit,
    private val onLongClickRemove: (Int) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishVH>() {

    inner class WishVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        private val tvUrl: TextView = view.findViewById(R.id.tvUrl)

        fun bind(item: WishlistItem) {
            tvName.text = item.name
            tvPrice.text = String.format("$%.2f", item.price)
            tvUrl.text = item.url

            itemView.setOnClickListener { onClick(item, it) }
            itemView.setOnLongClickListener {
                // remove by position
                onLongClickRemove(bindingAdapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlist, parent, false)
        return WishVH(v)
    }

    override fun onBindViewHolder(holder: WishVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
