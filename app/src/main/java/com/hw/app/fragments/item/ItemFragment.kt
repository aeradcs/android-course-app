package com.hw.app.fragments.item

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hw.app.R
import com.hw.app.database.Share
import com.hw.app.database.ShareViewModel
import com.hw.app.fragments.list.ListAdapter
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*


class ItemFragment : Fragment() {

    private val args by navArgs<ItemFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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

        return view
    }


}