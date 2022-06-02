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
import com.hw.app.database.ListViewModel
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import androidx.lifecycle.Observer
import com.hw.app.database.FavoriteViewModel


class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val recyclerview = view.recycler_view_fav
        val adapter = FavoriteAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val model: FavoriteViewModel by viewModels()
        model.getSharesFromDatabase().observe(viewLifecycleOwner, Observer{ shares ->
            adapter.refreshShares(shares)
        })

        view.stock_tv_fav.setOnClickListener{
            findNavController().navigate(R.id.action_favorite_fragment_to_list_fragment)
        }

        return view
    }
}