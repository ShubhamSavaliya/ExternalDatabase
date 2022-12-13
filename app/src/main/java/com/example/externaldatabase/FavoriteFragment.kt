package com.example.externaldatabase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FavoriteFragment : Fragment() {

    lateinit var v: View
    lateinit var rcv_fcvdisplay:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_favorite, container, false)
        initView()
        return v
    }

    private fun initView() {

        rcv_fcvdisplay=v.findViewById(R.id.rcv_fcvdisplay)
        var myDatabase = MyDatabase(context)
        var favorite = myDatabase.LikeShayriData()
        var shayriAdapter =
            context?.let {
                ShayriAdapter(it, favorite, shayriItemClick = { string, int ->

                }, DeleteClick = {

                })
            }
        val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_fcvdisplay.layoutManager = layoutmanager
        rcv_fcvdisplay.adapter = shayriAdapter
    }

}