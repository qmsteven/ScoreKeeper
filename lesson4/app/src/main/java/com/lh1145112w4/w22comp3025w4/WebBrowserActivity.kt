package com.lh1145112w4.w22comp3025w4

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lh1145112w4.w22comp3025w4.databinding.ActivityWebBrowserBinding

class WebBrowserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWebBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navigate back to the main activity
        binding.webBackToMain.setOnClickListener {
            finish()
        }

        //read from the edit text and send the url into the browser application
        //This is an "implicit" intent. We will just call a service and that service will actually
        //figure out what activity to run
        binding.loadUrlButton.setOnClickListener {
            var url = binding.urlEditText.text.toString()

            if (url.isNotEmpty()) {
                //prepend it with http: and call the browser
                if (!(url.startsWith("http://")) || (url.startsWith("https://")))
                    url = "http://" + url

                //create an implicit intent
                val intent = Intent(Intent.ACTION_VIEW)

                //add date to the intent
                intent.data = Uri.parse(url)

                //launch the intent
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Enter URL and try again", Toast.LENGTH_LONG).show()
            }
        }
    }
}