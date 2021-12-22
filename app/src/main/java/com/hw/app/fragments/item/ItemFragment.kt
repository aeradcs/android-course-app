package com.hw.app.fragments.item

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hw.app.R
import com.hw.app.database.Share
import com.hw.app.database.ShareViewModel
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment() {

    private val args by navArgs<ItemFragmentArgs>()
    val model: ShareViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)

        view.ticker_item_tv.setText(args.currentItem.ticker)
        view.name_item_tv.setText(args.currentItem.name)
        view.price_item_tv.setText(args.currentItem.price.toString())
        view.day_change_item_tv.setText(args.currentItem.dayChange.toString())

        if(args.currentItem.dayChange < 0){
            view.day_change_item_tv.setTextColor(Color.parseColor("#fc1d0d"))
            view.percent_item_tv.setTextColor(Color.parseColor("#fc1d0d"))
        }else{
            view.day_change_item_tv.setTextColor(Color.parseColor("#27c42f"))
            view.percent_item_tv.setTextColor(Color.parseColor("#27c42f"))
        }

        view.add_to_fav_button.setOnClickListener {
            insertItemInDatabase()
        }

        view.del_from_fav_button.setOnClickListener {
            deleteItemFromDatabase()
        }

        return view
    }

    private fun deleteItemFromDatabase() {
        val ticker = ticker_item_tv.text.toString()
        val name = name_item_tv.text.toString()
        val priceText = price_item_tv.text.toString()
        val dayChangeText = day_change_item_tv.text.toString()

        if(isValidInput(ticker, name, priceText, dayChangeText)){
            val price = priceText.toFloat()
            val dayChange = dayChangeText.toFloat()
            val share = Share(ticker, name, price, dayChange)
            model.deleteShare(share)
            findNavController().navigate(R.id.action_item_fragment_to_favorite_fragment)
        }
    }

    private fun insertItemInDatabase() {
        val ticker = ticker_item_tv.text.toString()
        val name = name_item_tv.text.toString()
        val priceText = price_item_tv.text.toString()
        val dayChangeText = day_change_item_tv.text.toString()

        if(isValidInput(ticker, name, priceText, dayChangeText)){
            val price = priceText.toFloat()
            val dayChange = dayChangeText.toFloat()
            val share = Share(ticker, name, price, dayChange)
            model.insertShare(share)
            findNavController().navigate(R.id.action_item_fragment_to_favorite_fragment)
        }
    }

    private fun isValidInput(ticker: String, name: String, price: String, dayChange: String):Boolean{
        return (!TextUtils.isEmpty(ticker) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(dayChange))
    }
}