package com.example.externaldatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.externaldatabase.databinding.ActivityDisplayQuotesBinding

@Suppress("NAME_SHADOWING")
class DisplayQuotes : AppCompatActivity() {

    lateinit var binding: ActivityDisplayQuotesBinding
    var categoryid: String? = null
    var id: Int? = null
    var shayriid: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onResume() {
        super.onResume()

        var type = intent.getStringExtra("category")
        Log.e("TAG", "onCreate__type:== $type")


        binding.txtSetcategoty.text = type


        var id = intent.getIntExtra("id", 0)
        Log.e("TAG", "onCreate:== $id")
        binding.imgBack.setOnClickListener {
            finish()
        }

        var myDatabase = MyDatabase(this)
        var shayri = myDatabase.readShayriData()

        var shayriAdapter =
            ShayriAdapter(this, shayri, shayriItemClick = { categoryid, id ->
                this.categoryid = categoryid
                this.id = id
            }, DeleteClick = { Id ->
                MyDatabase(this).deleteData(Id)
                onResume()
            })
        val layoutmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvShayri.layoutManager = layoutmanager
        binding.rcvShayri.adapter = shayriAdapter
    }
}