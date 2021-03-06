package com.example.hackerton.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hackerton.App
import com.example.hackerton.databinding.ActivityLoginBinding
import com.example.hackerton.reqeust.login.SignInInfo
import com.example.hackerton.reqeust.login.SignUpInfo
import com.example.hackerton.ui.MainActivity
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityLoginBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*
        binding.signUpButton.setOnClickListener {
            signUp()
        }*/
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }
/*
    private fun signUp(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(name.isNotEmpty() &&email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val response = RetrofitUtil.userService.userSignUp(
                        SignUpInfo(
                            name = name,
                            email = email,
                            password = password
                        )
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { response ->
                                Toast.makeText(this@LoginActivity,
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity,"??????????????? ?????????????????????.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Toast.makeText(this@LoginActivity,"?????? ????????? ???????????????!.", Toast.LENGTH_SHORT
            ).show()
        }
    }*/
    private fun signIn(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val response = RetrofitUtil.userService.userSignIn(
                        SignInInfo(
                            email = email,
                            password = password
                        )
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { response ->
                                App.sharedPreferenceManager.token = response.token
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity,"???????????? ?????????????????????.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Toast.makeText(this@LoginActivity,"?????? ????????? ???????????????!.", Toast.LENGTH_SHORT
            ).show()
        }
    }
}