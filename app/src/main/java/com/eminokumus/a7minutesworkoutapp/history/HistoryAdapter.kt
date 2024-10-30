package com.eminokumus.a7minutesworkoutapp.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ItemHistoryBinding
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity

class HistoryAdapter(val workoutHistoryList: List<WorkoutHistoryEntity>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(position: Int, workoutHistoryEntity: WorkoutHistoryEntity){
            binding.positionTextView.text = (position + 1).toString()
            binding.workoutHistoryText.text = workoutHistoryEntity.date
        }
        fun setBackgroundBasedOn(position: Int){
            if (position%2 == 0){
                binding.root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.lightGrey))
            }else{
                binding.root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workoutHistoryList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val workoutItem = workoutHistoryList[position]
        holder.bindItem(position, workoutItem)
        holder.setBackgroundBasedOn(position)
    }
}