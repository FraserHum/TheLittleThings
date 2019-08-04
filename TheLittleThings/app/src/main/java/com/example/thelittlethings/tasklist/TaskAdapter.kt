package com.example.thelittlethings.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thelittlethings.R
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.databinding.TextItemViewBinding
import com.example.thelittlethings.generated.callback.OnClickListener

class TaskAdapter(val clickListener: TaskListener): ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TextItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task, clickListener: TaskListener){
            binding.task = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TextItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskID == newItem.taskID
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskListener(val clickListener: (taskId: Long) -> Unit){
    fun onClick(task: Task) = clickListener(task.taskID)
}