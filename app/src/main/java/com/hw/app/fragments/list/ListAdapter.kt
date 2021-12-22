package com.hw.app.fragments.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hw.app.R
import com.hw.app.database.Share
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var shareList = emptyList<Share>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

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
            holder.itemView.percent_tv.setTextColor(Color.parseColor("#fc1d0d"))
        }else{
            holder.dayChange?.setTextColor(Color.parseColor("#27c42f"))
            holder.itemView.percent_tv.setTextColor(Color.parseColor("#27c42f"))
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ticker: TextView? = null
        var name: TextView? = null
        var price: TextView? = null
        var dayChange: TextView? = null

        init{
            ticker = itemView.ticker_tv
            name = itemView.name_tv
            price = itemView.price_tv
            dayChange = itemView.day_change_tv
        }
    }
}