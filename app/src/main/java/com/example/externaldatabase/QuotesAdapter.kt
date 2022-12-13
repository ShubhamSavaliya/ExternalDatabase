package com.example.externaldatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.externaldatabase.MyDatabase.valueOfQuotes
import com.example.externaldatabase.databinding.QuotesFileBinding
import java.util.ArrayList

class QuotesAdapter(val context : Context,var list: ArrayList<QuotesModalClass>) :
    RecyclerView.Adapter<QuotesAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: QuotesFileBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuotesFileBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtCategory.text = list[position].quotes
        holder.binding.txtCategory.setOnClickListener {
            val intent=Intent(context,DisplayQuotes::class.java)
            valueOfQuotes= list[position].id
            intent.putExtra("category", list[position].quotes)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}