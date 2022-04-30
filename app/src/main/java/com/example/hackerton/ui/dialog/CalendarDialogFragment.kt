package com.example.hackerton.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.hackerton.App
import com.example.hackerton.R
import com.example.hackerton.databinding.DialogFragmentCalendarBinding
import com.example.hackerton.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat

class CalendarDialogFragment : DialogFragment() {

    private val binding by lazy{ DialogFragmentCalendarBinding.inflate(layoutInflater)}

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
        binding.calendarView.setOnDateChangeListener(object: CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(p0: CalendarView, year: Int, Month: Int, dayOfMonth: Int) {
                binding.calendarTextView.text = "$year. ${Month+1}. $dayOfMonth"
            }
        })
        binding.imageButton2.setOnClickListener {
            if (binding.calendarTextView.text.isEmpty()) {
                Toast.makeText(App.applicationContext(), "날짜를 선택해 주세요!", Toast.LENGTH_SHORT).show()
            } else {
                buttonClickListener.onSaveButtonClicked(binding.calendarTextView.text.toString())
                dismiss()
            }
        }
        return binding.root
    }

    interface OnButtonClickListener{
        fun onSaveButtonClicked(date: String)
    }

    private lateinit var buttonClickListener: OnButtonClickListener
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener){
        this.buttonClickListener = buttonClickListener
    }

}