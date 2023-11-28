package com.example.tplogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tplogin.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    // menu
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var actionToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        initMenu(binding)
        navigateMenu(navView)


        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        if (user == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            binding.tvDetails.text = user!!.email.toString()
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun navigateMenu(navigationView: NavigationView) {
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.users -> {

                    val intent = Intent(this, user2::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.profile -> {
                    Toast.makeText(
                        baseContext,
                        "profile",
                        Toast.LENGTH_SHORT,
                    ).show()
                    true
                }

                R.id.logout -> {


                    auth.signOut()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)


                    true
                }
                else-> {false}
            }


        }

    }


    private fun initMenu(binding: ActivityMainBinding) {

            drawerLayout = binding.drawerLayout
            navView = binding.navView

            actionToggle = ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, 0, 0)
            drawerLayout.addDrawerListener(actionToggle)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

//make icon menu visible
            actionToggle.syncState()


    }
}