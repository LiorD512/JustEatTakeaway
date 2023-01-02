package com.liordahan.justeattakeaway.features.restaurants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liordahan.justeattakeaway.R
import com.liordahan.justeattakeaway.data.remote.models.Restaurant
import com.liordahan.justeattakeaway.databinding.ListItemRestaurantBinding

class RestaurantsAdapter(
    private val listener: RestaurantAdapterListener
) : ListAdapter<Restaurant, RecyclerView.ViewHolder>(DiffCallback()) {

    interface RestaurantAdapterListener{
        fun onFavoriteClicked(isChecked: Boolean, restaurantId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantsViewHolder(
            ListItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val itemViewHolder = holder as RestaurantsViewHolder
        holder.favoriteCb.isChecked = item.isChecked
        // Delegating the responsibility to update the object to the view model
        itemViewHolder.favoriteCb.setOnCheckedChangeListener { _, isChecked ->
            item.restaurantId?.let {
                listener.onFavoriteClicked(isChecked, it)
            }
        }
        itemViewHolder.bind(item)
    }


    private class DiffCallback : DiffUtil.ItemCallback<Restaurant>() {

        override fun areItemsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return oldItem.restaurantId == newItem.restaurantId
        }

        override fun areContentsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class RestaurantsViewHolder(private val binding: ListItemRestaurantBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val favoriteCb = binding.listItemResFavoriteCb

    fun bind(restaurant: Restaurant) {

        binding.apply {
            Glide.with(binding.root.context)
                .load(restaurant.coverImage)
                .placeholder(R.drawable.res_placeholder)
                .into(listItemResCoverImage)

            listItemResName.text = restaurant.restaurantName
            listItemResOpenNow.isVisible = restaurant.isOpen == true

        }
    }
}