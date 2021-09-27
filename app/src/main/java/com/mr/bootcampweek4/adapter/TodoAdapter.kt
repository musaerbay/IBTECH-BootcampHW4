package com.mr.bootcampweek4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mr.bootcampweek4.R
import com.mr.bootcampweek4.base.BaseRecyclerViewItemClickListener
import com.mr.bootcampweek4.model.Todo
import kotlinx.android.synthetic.main.row_todo.view.*

class TodoAdapter(var list: MutableList<Todo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: BaseRecyclerViewItemClickListener<Todo>? = null

    constructor(
        list: MutableList<Todo>,
        itemClickListener: BaseRecyclerViewItemClickListener<Todo>
    ) : this(list) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todo = this.list[position]
        (holder as TodoViewHolder).setData(todo)
        holder.onClickListener(todo, this.itemClickListener!!)
    }

    override fun getItemCount(): Int = this.list.size
}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var completion: ImageView = itemView.row_Todo_image
    private var description: TextView = itemView.row_Todo_text

    fun setData(todo: Todo) {
        changeCompletion(todo)
        description.text = todo.description
    }

    // for click complated or delete butons
    fun onClickListener(
        todo: Todo,
        itemClickListener: BaseRecyclerViewItemClickListener<Todo>?
    ) {
        itemView.complete_btn.setOnClickListener {
            itemClickListener!!.onItemClicked(todo, it.id)
            changeCompletion(todo)
        }
        itemView.delete_btn.setOnClickListener {
            itemClickListener!!.onItemClicked(todo, it.id)
        }
    }

    // change selected task
    private fun changeCompletion(todo: Todo) {
        if (todo.completed) completion.setImageResource(R.drawable.marked)
        else completion.setImageResource(R.drawable.unmarked)
    }
}

