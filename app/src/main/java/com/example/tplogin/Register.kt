package com.example.tplogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tplogin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
class Register : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
// Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        binding.loginNow.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            binding.progress.visibility = View.VISIBLE
            if (email.isEmpty()) {
                Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.GONE
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.GONE
                return@setOnClickListener
            }
      /*      val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).{6,}\$")
            if (!password.matches(passwordRegex)) {
                Toast.makeText(this, "Password must be at least 6 characters long, contain at least one uppercase letter, and one digit.", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.GONE
                return@setOnClickListener
            }*/
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.progress.visibility = View.GONE
                    if (task.isSuccessful) {
// Sign in success, update UI with the signed-in user's
                        Toast.makeText(
                            baseContext,
                            "Account created.",
                            Toast.LENGTH_SHORT,

                            ).show()

                        val intent = Intent(this, MainActivity::class.java)

                        startActivity(intent)

                        finish()
                    } else {
// If sign-in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,

                            "Authentication failed.",
                            Toast.LENGTH_SHORT,

                            ).show()
                    }
                }
        }
    }
}