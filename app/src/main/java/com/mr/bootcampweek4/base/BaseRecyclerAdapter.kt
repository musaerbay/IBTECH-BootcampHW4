package com.mr.bootcampweek4.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(
    private val mList: List<T>,
    private val block: (T) -> Unit = { }
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = getViewHolder(parent)
    override fun onBindViewHolder(holder: VH, position: Int) {
        bind(mList[position], holder)
    }

    abstract fun bind(bindedObject: T, holder: VH)
    abstract fun getViewHolder(container: ViewGroup): VH
    override fun getItemCount(): Int = this.mList.size

    open inner class BaseViewHolder(val rootView: View, block: (T) -> Unit) :
        RecyclerView.ViewHolder(rootView) {
        open fun setData(model: T) {}

        open fun setOnClickListener(clickedItem: T, block: (T) -> Unit) {
            rootView.setOnClickListener {
                block.invoke(clickedItem)
            }

        }
    }
}