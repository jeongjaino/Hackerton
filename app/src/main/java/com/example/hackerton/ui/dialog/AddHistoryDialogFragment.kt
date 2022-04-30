package com.example.hackerton.ui.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.hackerton.App
import com.example.hackerton.R
import com.example.hackerton.databinding.DialogFragmentAddHistoryBinding
import com.example.hackerton.reqeust.career.HistoryRequest
import com.example.hackerton.ui.MainActivity
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class AddHistoryDialogFragment : DialogFragment() {

    private val binding by lazy{DialogFragmentAddHistoryBinding.inflate(layoutInflater)}

    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onStart() {
        super.onStart()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.saveHistoryButton.setOnClickListener{
            requestHistory()
            dismiss()
            getGps()
        }
        binding.endTimeEditText.setOnClickListener{
            val dialog = CalendarDialogFragment()
            dialog.setButtonClickListener(object: CalendarDialogFragment.OnButtonClickListener{
                override fun onSaveButtonClicked(date: String) {
                    binding.endTimeEditText.text = date
                }
            })
            dialog.show(mainActivity!!.supportFragmentManager, "calendarDialog")
        }

        binding.startTimeEditText.setOnClickListener{
            val dialog = CalendarDialogFragment()
            dialog.setButtonClickListener(object: CalendarDialogFragment.OnButtonClickListener{
                override fun onSaveButtonClicked(date: String) {
                    binding.startTimeEditText.text = date
                }
            })
            dialog.show(mainActivity!!.supportFragmentManager, "calendarDialog")
        }
        return binding.root
    }
    private fun requestHistory(){
        val workSpot = binding.workSpotEditText.text.toString()
        val workType = binding.workTypeEditText.text.toString()
        val startTime = binding.startTimeEditText.text.toString()
        val endTime = binding.endTimeEditText.text.toString()
        if(workSpot.isNotEmpty() && workType.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val response = RetrofitUtil.workService.postWorkHistory(
                        HistoryRequest(workSpot, workType, startTime, endTime)
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        if (response.isSuccessful) {
                            Toast.makeText(App.applicationContext(),
                                "이력을 입력하였습니다!",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(App.applicationContext(),
                                "네트워크 연결을 확인해 주세요",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
    private fun getGps(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = RetrofitUtil.gpsService.getGps()
                if(response.isSuccessful){
                    response.body()?.let{ body ->
                        Log.d("tag",body.lag + ", " +body.lag)
                    }
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
}