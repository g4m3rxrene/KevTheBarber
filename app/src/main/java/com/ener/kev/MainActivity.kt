package com.ener.kev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
            startActivity(Intent(this,AddCustomer::class.java))
        }



    }

    private fun FragmentSetUp() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_layout) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)
    }

}


    private fun UiFixes() {

        binding.bottomNavBar.background = null
        binding.bottomNavBar.menu.getItem(1).isEnabled = false
        binding.bottomNavContainer.isEnabled = false
        binding.bottomNavBar.menu.getItem(2).isEnabled = false
    }




 class CustomerData(val name:String,val surname:String, val email:String,val phone:String, val service:String,val products:String,val price:String, val payment:String,val date:String){

    constructor() : this("","","","","","","","","")



}