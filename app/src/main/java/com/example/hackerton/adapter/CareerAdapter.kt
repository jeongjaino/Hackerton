package com.example.hackerton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackerton.databinding.ItemCareerBinding
import com.example.hackerton.response.career.HistoryResponse

class CareerAdapter: RecyclerView.Adapter<CareerAdapter.Holder>() {

    var careerList = mutableListOf<HistoryResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemCareerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val history = careerList.get(position)
        holder.setHistory(history)
    }

    override fun getItemCount(): Int {
        return careerList.size
    }

    inner class Holder(val binding: ItemCareerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setHistory(history: HistoryResponse) {
            binding.attitudeRateText.text = "받은평가 " + history.attributeRate.toString()
            binding.officeTimeText.text = history.officeName
            binding.roleText.text = history.role
            binding.workTimeText.text = trimDate(history.startDate) + " - " + trimDate(history.endDate)
        }
    }
    private fun trimDate(date: String?): String{
        date?.let {
            return date.substring(0, 10)
        }
        return ""
    }
}