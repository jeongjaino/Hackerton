package com.example.hackerton.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.hackerton.App
import com.example.hackerton.reqeust.home.WorkEndRequest
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel:ViewModel() {

    fun workStart(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitUtil.workService.workStart()
                if(response.isSuccessful){
                    App.sharedPreferenceManager.id = response.body()?.id
                    Toast.makeText(App.applicationContext(),
                        "출근 하였습니다!",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    CoroutineScope(Dispatchers.Main).launch{
                        Toast.makeText(App.applicationContext(), "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun workEnd(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                App.sharedPreferenceManager.id?.let { id ->
                    val response = RetrofitUtil.workService.workEnd(WorkEndRequest(id))
                    CoroutineScope(Dispatchers.Main).launch {
                        if (response.isSuccessful) {
                            Toast.makeText(App.applicationContext(),
                                "퇴근 하였습니다!",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("tag2", response.code().toString())
                            Toast.makeText(App.applicationContext(),
                                "네트워크 연결을 확인해 주세요",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
}