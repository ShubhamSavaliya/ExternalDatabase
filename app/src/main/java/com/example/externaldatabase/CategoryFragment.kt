package com.example.externaldatabase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CategoryFragment : Fragment() {

    lateinit var rcv_quotes: RecyclerView
    lateinit var v : View
    override fun onCreateView(
        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_category, container, false)

        initView()
        return v
    }

    private fun initView() {
        var myDatabase = MyDatabase(context)
        var list = myDatabase.readData()

        rcv_quotes =v.findViewById(R.id.rcv_quotes)
        var quotesAdapter = context?.let { QuotesAdapter(it, list) }
        val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_quotes.layoutManager = layoutmanager
        rcv_quotes.adapter = quotesAdapter
    }

}