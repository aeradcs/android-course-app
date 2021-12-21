package com.hw.app.fragments.favourite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hw.app.R
import com.hw.app.database.Share
import com.hw.app.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item_fav.view.*

class FavoriteAdapter(private var shareList: List<Share>): RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_fav, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shareList[position]
        holder.ticker?.text = currentItem.ticker
        holder.name?.text = currentItem.name
        holder.price?.text = currentItem.price.toString()
        holder.dayChange?.text = currentItem.dayChange.toString()
        if(currentItem.dayChange.toString().toFloat() < 0){
            holder.dayChange?.setTextColor(Color.parseColor("#fc1d0d"))
            holder.itemView.percent_tv_fav.setTextColor(Color.parseColor("#fc1d0d"))
        }else{
            holder.dayChange?.setTextColor(Color.parseColor("#27c42f"))
            holder.itemView.percent_tv_fav.setTextColor(Color.parseColor("#27c42f"))
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ticker: TextView? = null
        var name: TextView? = null
        var price: TextView? = null
        var dayChange: TextView? = null

        init{
            ticker = itemView.ticker_tv_fav
            name = itemView.name_tv_fav
            price = itemView.price_tv_fav
            dayChange = itemView.day_change_tv_fav
        }
    }
}