package com.hw.app.fragments.list

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
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item_fav.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var shareList = emptyList<Share>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shareList[position]
        holder.itemView.ticker_tv.text = currentItem.ticker
        holder.itemView.name_tv.text = currentItem.name
        holder.itemView.price_tv.text = currentItem.price.toString()
        holder.itemView.day_change_tv.text = currentItem.dayChange.toString()
        if(currentItem.dayChange.toString().toFloat() < 0){
            holder.itemView.day_change_tv.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.red, null))
            holder.itemView.percent_tv.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.red, null))
        }else{
            holder.itemView.day_change_tv.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.green, null))
            holder.itemView.percent_tv.setTextColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.green, null))
        }
        if(currentItem.logo != ""){
            Picasso.with(holder.itemView.context)
                .load(currentItem.logo)
                .placeholder(R.drawable.ic_no_image_foreground)
                .error(R.drawable.ic_no_image_foreground)
                .into(holder.itemView.logo);
        }else{
            Picasso.with(holder.itemView.context)
                .load(R.drawable.ic_no_image_foreground)
                .placeholder(R.drawable.ic_no_image_foreground)
                .error(R.drawable.ic_no_image_foreground)
                .into(holder.itemView.logo);
        }

        holder.itemView.list_item.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToItemFragment(currentItem)
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