// TodoAdapter.kt
package com.example.practice_iii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_iii.databinding.TodoItemBinding

class TodoAdapter(
    private val todoList: MutableList<TodoItem>,
    private val onItemClick: (TodoItem) -> Unit,
    private val onDeleteClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItem) {
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
            binding.deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount() = todoList.size

    fun removeItem(item: TodoItem) {
        val position = todoList.indexOf(item)
        if (position != -1) {
            todoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addItem(item: TodoItem) {
        todoList.add(0, item)
        notifyItemInserted(0)
    }
}
