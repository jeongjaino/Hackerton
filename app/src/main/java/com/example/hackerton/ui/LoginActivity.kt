package com.example.hackerton.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hackerton.databinding.ActivityLoginBinding
import com.example.hackerton.reqeust.SignUpInfo
import com.example.hackerton.response.SignUpResponse
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityLoginBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.signUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
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
                        body?.let{ response ->
                            Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else{
                        Toast.makeText(this@LoginActivity,"회원가입에 실패하였습니다.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}