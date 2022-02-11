package com.lh1145112w4.w22comp3025w4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lh1145112w4.w22comp3025w4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //configure a view binding to connect view objects with the controller class
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //connect binding variable with the actual ActivityMainBinding class
        binding = ActivityMainBinding.inflate(layoutInflater)

        //configure the content view to use the binding class instead of the activity_main.xml
        setContentView(binding.root)

        //click listener to pass the users' name into a different Activity
        binding.passDetailsButton.setOnClickListener {
            var name = binding.nameEditText.text.toString()

            if (name.isNotEmpty()) {
                //pass the information to the PassDetailsActivity
                //create an explicit intent to navigate to the PassDetailsActivity
                var intent = Intent(this, PassDetailsActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            }
        }

        //navigate to the WebActivity
        binding.webButton.setOnClickListener {
            startActivity(Intent(this, WebBrowserActivity::class.java))
        }

        //navigate to the sms Activity
        binding.smsButton.setOnClickListener {
            startActivity(Intent(this, SmsActivity::class.java))
        }
    }
}