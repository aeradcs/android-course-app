package com.hw.app.fragments.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hw.app.R
import com.hw.app.database.Share
import com.hw.app.database.ShareViewModel
import com.hw.app.fragments.list.ListAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import androidx.lifecycle.Observer


class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val data = ArrayList<Share>()
        for (i in 1..20) {
            data.add(Share("ticker" + i.toString(), "name", i.toFloat(), i.toFloat()))
        }

        val recyclerview = view.recycler_view_fav
        val adapter = FavoriteAdapter(data)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val model: ShareViewModel by viewModels()
        model.getShares().observe(viewLifecycleOwner, Observer{ shares ->
            adapter.refreshShares(shares)
        })

        view.stock_tv_fav.setOnClickListener{
            findNavController().navigate(R.id.action_favorite_fragment_to_list_fragment)
        }

        return view
    }


}