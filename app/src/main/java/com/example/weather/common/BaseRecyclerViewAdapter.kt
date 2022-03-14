package com.example.weather.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @copyright This source code written by Majid Arabi and
 * you don't access to use any part of this in another project
 * or publish that for any person.
 * Date: 2/15/2021 AD
 */
abstract class BaseRecyclerViewAdapter<T, VH : BaseRecyclerViewAdapter<T, VH>.BaseViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    //private fun isValidPosition(position: Int) = currentList.isValidPosition(position)

}