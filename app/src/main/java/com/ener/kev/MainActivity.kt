package com.ener.kev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.ener.kev.databinding.ActivityMainBinding
import java.io.Serializable

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UiFixes()
        FragmentSetUp()
        binding.fab.setOnClickListener {
            val intent = Intent(this,AddCustomer::class.java)
            startActivity(intent)
        }



    }

    private fun FragmentSetUp() {

        binding.bottomNavBar.setOnItemSelectedListener {

                item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.commit { replace(R.id.fragment_layout, Home()) }
                    true
                }
                R.id.search -> {
                    supportFragmentManager.commit { replace(R.id.fragment_layout, Search()) }
                    true
                }
                else -> false
            }
    }

}


    private fun UiFixes() {

        supportFragmentManager.commit {
            detach(Home()).attach(Home())
        }

        binding.bottomNavBar.background = null
        binding.bottomNavBar.menu.getItem(1).isEnabled = false
        binding.bottomNavContainer.isEnabled = false
        binding.bottomNavBar.menu.getItem(2).isEnabled = false
    }



}
 class CustomerData(val name:String,val surname:String, val email:String,val phone:String, val service:String,val products:String,val price:String, val payment:String,val date:String){

    constructor() : this("","","","","","","","","")



}