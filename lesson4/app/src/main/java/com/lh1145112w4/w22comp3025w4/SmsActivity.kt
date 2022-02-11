package com.lh1145112w4.w22comp3025w4

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lh1145112w4.w22comp3025w4.databinding.ActivitySmsBinding

class SmsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.smsBackToMain.setOnClickListener {
            finish()
        }

        binding.imageButton.setOnClickListener {
            var phoneNum = binding.phoneNumEditText.text.toString()
            var message = binding.messageEditText.text.toString()

            if (phoneNum.isNotEmpty() && message.isNotEmpty()) {
                val uri = Uri.parse("smsto:$phoneNum")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", message)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Enter phone and message info", Toast.LENGTH_LONG).show()
            }
        }
    }
}