package com.example.navigationcomponent.ui.adapters

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponent.R
import com.example.navigationcomponent.data.remote.models.Todo
import com.example.navigationcomponent.databinding.TodoItemBinding
import com.squareup.picasso.Picasso

class ToDoAdapter(
    private var toDosList: List<Todo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {

    fun setToDosList(toDosList: List<Todo>) {
        this.toDosList = toDosList
    }

    class ToDoHolder(val todoItemBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(todoItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        return ToDoHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val item = toDosList[position]

        holder.todoItemBinding.textViewTitle.text = item.todo
        holder.todoItemBinding.textViewCompleted.text = "Completed"

        val iconResource = if (item.completed == true) {
            R.drawable.completed_icon
        } else {
            R.drawable.incomplete_icon
        }

        val colorId = if (item.completed == true) {
            R.color.green
        } else {
            R.color.red
        }

        val color = ContextCompat.getColor(holder.itemView.context, colorId)

        holder.todoItemBinding.imageViewCompleted.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        holder.todoItemBinding.imageViewCompleted.setImageResource(iconResource)
        Picasso.get().load(item.image).into(holder.todoItemBinding.imageView)
    }

    override fun getItemCount(): Int {
        return toDosList.size
    }
}