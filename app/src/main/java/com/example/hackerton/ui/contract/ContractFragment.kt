package com.example.hackerton.ui.contract

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.hackerton.App
import com.example.hackerton.databinding.FragmentContractBinding
import com.example.hackerton.ui.MainActivity
import com.example.hackerton.ui.dialog.AddHistoryDialogFragment
import com.example.hackerton.ui.dialog.CalendarDialogFragment
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ContractFragment : Fragment() {

    private val binding by lazy{ FragmentContractBinding.inflate(layoutInflater)}
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity =context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        getSummary()

        binding.calendarCardView.setOnClickListener{
            val dialog = CalendarDialogFragment()
            dialog.setButtonClickListener(object: CalendarDialogFragment.OnButtonClickListener{
                override fun onSaveButtonClicked(date: String) {
                    binding.contractDateText.text = date
                }
            })
            dialog.show(mainActivity!!.supportFragmentManager, "calendarDialog")
        }
        return binding.root
    }
    private fun getSummary(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = RetrofitUtil.workService.getWorkSummary()
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            binding.serviceTimeText.text = "근속기간 ${body.serviceTime} 일"
                            binding.workingTimeText.text = "하루 ${body.workingTime}시간"
                            binding.severancePayText.text = (body.severancePay / 10000).toString() + "만" + (body.severancePay % 10000).toString() + "원"
                        }
                    } else {
                        Log.d("tag2", response.code().toString())
                        Toast.makeText(App.applicationContext(),
                            "네트워크 연결을 확인해 주세요",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}