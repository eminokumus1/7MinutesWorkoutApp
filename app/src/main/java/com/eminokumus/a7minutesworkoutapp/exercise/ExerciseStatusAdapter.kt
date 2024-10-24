package com.eminokumus.a7minutesworkoutapp.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import com.eminokumus.a7minutesworkoutapp.databinding.ItemExerciseStatusBinding
import com.eminokumus.a7minutesworkoutapp.vo.Exercise

class ExerciseStatusAdapter(val itemList: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ExerciseStatusViewHolder>() {

    inner class ExerciseStatusViewHolder(val binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(exercise: Exercise) {
            binding.itemTextView.text = exercise.id.toString()
        }

        fun setItemBackground(exercise: Exercise, itemView: View) {
            when {
                exercise.isSelected -> {
                    binding.root.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.item_circular_thin_accent_border_bg
                    )
                }

                exercise.isCompleted -> {
                    binding.root.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.item_circular_color_accent_bg
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseStatusViewHolder {
        val binding =
            ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseStatusViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ExerciseStatusViewHolder, position: Int) {
        val item = itemList[position]
        holder.bindItem(item)
        holder.setItemBackground(item, holder.itemView)
    }
}