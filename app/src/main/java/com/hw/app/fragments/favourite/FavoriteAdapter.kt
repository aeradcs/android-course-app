package com.hw.app.fragments.favourite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hw.app.R
import com.hw.app.database.Share
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_fav.view.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var shareList = emptyList<Share>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_fav, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shareList[position]
        holder.itemView.ticker_tv_fav.text = currentItem.ticker
        holder.itemView.name_tv_fav.text = currentItem.name
        holder.itemView.price_tv_fav.text = currentItem.price.toString()
        holder.itemView.day_change_tv_fav.text = currentItem.dayChange.toString()
        if(currentItem.dayChange.toString().toFloat() < 0){
            holder.itemView.day_change_tv_fav.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.red, null))
            holder.itemView.percent_tv_fav.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.red, null))
        }else{
            holder.itemView.day_change_tv_fav.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.green, null))
            holder.itemView.percent_tv_fav.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.green, null))
        }
        if(currentItem.logo != ""){
            Picasso.with(holder.itemView.context)
                .load(currentItem.logo)
                .placeholder(R.drawable.ic_no_image_foreground)
                .error(R.drawable.ic_no_image_foreground)
                .into(holder.itemView.logo_fav);
        }else{
            Picasso.with(holder.itemView.context)
                .load(R.drawable.ic_no_image_foreground)
                .placeholder(R.drawable.ic_no_image_foreground)
                .error(R.drawable.ic_no_image_foreground)
                .into(holder.itemView.logo_fav);
        }

        holder.itemView.list_item_fav.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToItemFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return shareList.size
    }

    fun refreshShares(shareList: List<Share>){
        this.shareList = shareList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}