package com.example.hackerton.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hackerton.App
import com.example.hackerton.databinding.ActivityLoginBinding
import com.example.hackerton.reqeust.SignInInfo
import com.example.hackerton.reqeust.SignUpInfo
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
        binding.signUpButton.setOnClickListener {
            signUp()
        }
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signUp(){
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
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
                            Toast.makeText(this@LoginActivity,"회원가입에 실패하였습니다.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Toast.makeText(this@LoginActivity,"모든 정보를 입력하세요!.", Toast.LENGTH_SHORT
            ).show()
        }
    }
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
                                App.tokenManager.token = response.token
                            }
                        }
                    } else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity,"로그인에 실패하였습니다.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Toast.makeText(this@LoginActivity,"모든 정보를 입력하세요!.", Toast.LENGTH_SHORT
            ).show()
        }
    }
}