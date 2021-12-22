package com.hw.app.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hw.app.R
import com.hw.app.database.Share
import com.hw.app.database.ShareViewModel
import com.hw.app.database.data.Data
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

//        val data = ArrayList<Share>()
//        val tickers = Data.getTop15SP500Tickers()
//        for (i in 0..14) {
//            data.add(Share(tickers.get(i), "name", i.toFloat(), i.toFloat()))
//        }

        val recyclerview = view.recycler_view
        val adapter = ListAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val model: ShareViewModel by viewModels()
        model.loadTop15SP500Shares()
        model.getShares().observe(viewLifecycleOwner, Observer{ shares ->
            adapter.refreshShares(shares)
        })

        view.search_button.setOnClickListener {
            if(!view.find_et.text.isEmpty()){
                model.loadSharesFromApi(view.find_et.text.toString())
            }
        }

        view.favorite_tv.setOnClickListener{
            findNavController().navigate(R.id.action_list_fragment_to_favorite_fragment)
        }

        return view
    }
}