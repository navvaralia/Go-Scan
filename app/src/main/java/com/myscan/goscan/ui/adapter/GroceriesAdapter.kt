package com.myscan.goscan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myscan.goscan.R
import com.myscan.goscan.data.item_class.GroceriesItem
import com.myscan.goscan.databinding.GroceriesListBinding
import io.grpc.internal.JsonUtil.getString
import java.text.SimpleDateFormat
import java.util.*

class GroceriesAdapter :
    ListAdapter<GroceriesItem, GroceriesAdapter.GroceriesViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroceriesViewHolder {
        val groceriesBinding =
            GroceriesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceriesViewHolder(groceriesBinding)
    }

    override fun onBindViewHolder(holder: GroceriesViewHolder, position: Int) {
        val groceriesListItem = getItem(position)
        holder.bind(groceriesListItem)
    }

    inner class GroceriesViewHolder(private val binding: GroceriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groceriesItem: GroceriesItem) {
            binding.tvGroceriesBrand.text = groceriesItem.groceriesName
            binding.tvGroceriesPrice.text = binding.root.context.getString(
                R.string.rupiah,
                groceriesItem.groceriesPrice.toString()
            )
            binding.tvGroceriesTotal.text = groceriesItem.groceriesTotal.toString()
            Glide.with(itemView.context)
                .load(groceriesItem.groceriesImage)
                .into(binding.shapeableImageView)

            val todayDate = Date()
            val formatDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(todayDate)
            binding.tvDate.text = formatDate
        }

    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<GroceriesItem> =
            object : DiffUtil.ItemCallback<GroceriesItem>() {
                override fun areItemsTheSame(
                    oldItem: GroceriesItem,
                    newItem: GroceriesItem
                ): Boolean {
                    return oldItem.groceriesName == newItem.groceriesName
                }

                override fun areContentsTheSame(
                    oldItem: GroceriesItem,
                    newItem: GroceriesItem
                ): Boolean {
                    return oldItem == newItem
                }
            }

    }
}