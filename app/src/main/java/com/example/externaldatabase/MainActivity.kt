package com.example.externaldatabase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.externaldatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var isBackPress : Boolean = false
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }
    override fun onBackPressed() {
        if (isBackPress) { super.onBackPressed()
            return}
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        isBackPress = true
        Handler().postDelayed({ isBackPress = false }, 2000)
    }


    private fun initView() {
        binding.imgMenu.setOnClickListener {
            binding.drawerOpen.openDrawer(Gravity.LEFT)

        }


        val firstFragment = CategoryFragment()
        val secondFragment = FavoriteFragment()


        setCurrentFragment(firstFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.category -> setCurrentFragment(firstFragment)
                R.id.favorite -> setCurrentFragment(secondFragment)
            }
            true
        }
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_load, fragment)
            commit()
        }


}