package com.ener.kev

import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ener.kev.databinding.ActivityAddCustomerBinding
import java.text.DateFormat
import java.util.*

import android.widget.*
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern


class AddCustomer : AppCompatActivity() {
    private lateinit var binding: ActivityAddCustomerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseDatabase.getInstance().reference

        initializeSpinners()


        binding.submitButton.isEnabled = false
        binding.termsCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.submitButton.isEnabled = isChecked
        }

        binding.submitButton.setOnClickListener {

            //A very long list of code making checks
            if (binding.firstName.text.toString().trim().isEmpty()) {
                binding.firstName.setError("Please enter your name!")
                binding.firstName.requestFocus()
                return@setOnClickListener
            }
            if (binding.firstName.text.toString().length <= 3) {
                binding.firstName.setError("Name should be more than 3 characters")
                binding.firstName.requestFocus()

                return@setOnClickListener
            }

            if (binding.surname.text.toString().trim().isEmpty()) {
                binding.surname.setError("Please enter your surname!")
                binding.surname.requestFocus()
                return@setOnClickListener
            }
            if (binding.surname.text.toString().length <= 3) {
                binding.surname.setError("Surname should be more than 3 characters")
                binding.surname.requestFocus()
                return@setOnClickListener
            }

            if (binding.email.text.toString().trim().isEmpty()) {
                binding.email.setError("Enter your email address")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()) {
                binding.email.setError("Enter a valid email address")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().trim().isEmpty()) {
                binding.phone.setError("Enter your phone number!")
                binding.phone.requestFocus()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().length != 10) {
                binding.phone.setError("Phone number should be more than 10 digits")
                binding.phone.requestFocus()
                return@setOnClickListener
            }

            if (binding.serviceSpinner.selectedItem.toString() == "Service") {
                val errorText = binding.serviceSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.productSpinner.selectedItem.toString() == "Products") {
                val errorText = binding.productSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.priceSpinner.selectedItem.toString() == "Price") {
                val errorText = binding.priceSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.paymentMethodSpinner.selectedItem.toString() == "Payment Method") {
                val errorText = binding.paymentMethodSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            // The code ends here,lol,that was a handful
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
            binding.progressBar.isVisible = true

            val customerdata = CustomerData(
                binding.firstName.text.toString(),
                binding.surname.text.toString(),
                binding.email.text.toString(),
                binding.phone.text.toString(),
                binding.serviceSpinner.selectedItem.toString(),
                binding.productSpinner.selectedItem.toString(),
                binding.priceSpinner.selectedItem.toString(),
                binding.paymentMethodSpinner.selectedItem.toString(),
                getDate()
            )

            db.child("CustomerData").push().setValue(customerdata).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.progressBar.isVisible = false
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    finish()
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(
                        this,
                        "Oops, Something wrong happened.Try again...",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }

        }


    }


    private fun initializeSpinners() {
        var services = arrayOf("Service", "Haircut", "Shave", "Haircut + Shave")
        binding.serviceSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, services)

        var products = arrayOf("Products", "Beard Oil", "Shaving Cream", "Hair Food")
        binding.productSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, products)

        var prices = arrayOf("Price", "R80", "R60", "R30")
        binding.priceSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, prices)

        var paymentMethods = arrayOf("Payment Method", "Cash", "Bank Transfer", "Credit Card")
        binding.paymentMethodSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, paymentMethods)


        // Remember to turn off, based of availabiltuy of stock(talk to kev)
        binding.productSpinner.isEnabled = true

    }


    private fun getDate(): String {
        var calender = Calendar.getInstance()
        return DateFormat.getDateInstance(DateFormat.FULL).format(calender.time)

    }

}