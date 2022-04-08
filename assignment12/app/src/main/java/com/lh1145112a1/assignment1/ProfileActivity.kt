package com.lh1145112a1.assignment1

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.lh1145112a1.assignment1.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val authDB = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.termsTextView.movementMethod = ScrollingMovementMethod()

        if (authDB.currentUser == null) {
            logout()
        }
        else {
            authDB.currentUser?.let{
                binding.usersNameTextView.text = it.displayName
                binding.emailTextView.text = it.email
            }
        }
        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_home -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            R.id.action_add_new_score -> {
                startActivity(Intent(applicationContext, AddScoreActivity::class.java))
                return true
            }
            R.id.action_edit_profile -> {
//                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        authDB.signOut()
        finish()
        startActivity(Intent(this, SigninActivity::class.java))
    }
}