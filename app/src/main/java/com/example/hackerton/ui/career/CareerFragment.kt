package com.example.hackerton.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackerton.App
import com.example.hackerton.R
import com.example.hackerton.adapter.CareerAdapter
import com.example.hackerton.databinding.FragmentCareerBinding
import com.example.hackerton.response.career.HistoryResponse
import com.example.hackerton.ui.dialog.AddHistoryDialogFragment
import com.example.hackerton.utils.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class CareerFragment : Fragment() {

    private val binding by lazy{ FragmentCareerBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.addHistoryButton.setOnClickListener{
            val dialog = AddHistoryDialogFragment()
            dialog.show(activity!!.supportFragmentManager, "AddHistoryDialog")
        }

        loadCareer()

        return binding.root
    }

    private fun loadCareer(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = RetrofitUtil.workService.getWorkHistory()
                if(response.isSuccessful){
                    CoroutineScope(Dispatchers.Main).launch {
                        val adapter= CareerAdapter()
                        binding.historyRecyclerView.adapter = adapter
                        binding.historyRecyclerView.layoutManager = LinearLayoutManager(context)
                        adapter.careerList = response.body()!!
                        adapter.notifyDataSetChanged()
                    }
                }else{
                    Toast.makeText(App.applicationContext(),
                        "네트워크 연결을 확인해 주세요",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}