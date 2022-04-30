package com.example.hackerton.ui.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.hackerton.App
import com.example.hackerton.R
import com.example.hackerton.databinding.FragmentCalendarBinding
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate

class CalendarFragment : Fragment() {

    private val binding by lazy{FragmentCalendarBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.calendarInCardView.visibility = View.GONE

        binding.calendarView2.setOnDateChangeListener(object: CalendarView.OnDateChangeListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSelectedDayChange(p0: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
                val pickDate = LocalDate.of(year,month+1,dayOfMonth)
                val dateNow = LocalDate.now()
                if(pickDate.month <= dateNow.month) {
                    if(pickDate.dayOfMonth < dateNow.dayOfMonth) {
                        val date = "$year-${month(month)}-$dayOfMonth"
                        getCalendarTime(date)
                    }else{
                        Toast.makeText(App.applicationContext(),
                            "미래의 시간을 선택하세요!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return binding.root
    }
    private fun getCalendarTime(date: String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                CoroutineScope(Dispatchers.Main).launch {
                    val response = RetrofitUtil.workService.getCalendarDetail(date)
                    if (response.isSuccessful) {
                        binding.calendarInCardView.visibility = View.VISIBLE
                        response.body()?.let { body ->
                            val startTime= trimDate(body.startTime)
                            val finishTime = trimDate(body.finishTime)
                            binding.calendarDateText.text = "${startTime} - ${finishTime}"
                        }
                    } else {
                        binding.calendarInCardView.visibility = View.GONE
                    }
                }
            } catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
    private fun month(month: Int): String{
        if (month < 9){
            return "0${month+1}"
        }else{
            return "${month+1}"
        }
    }
    private fun trimDate(date: String?): String{
        date?.let {
            return date.substring(11, 16)
        }
        return ""
    }

}