package com.ener.kev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ener.kev.databinding.ActivityHomeListTempBinding

private lateinit var binding: ActivityHomeListTempBinding

class HomeListTemp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeListTempBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}