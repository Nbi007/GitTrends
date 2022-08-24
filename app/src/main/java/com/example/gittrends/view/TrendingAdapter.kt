package com.example.gittrends.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gittrends.R
import com.example.gittrends.model.TrendingListDTO
import java.util.*

class TrendingAdapter constructor(context:Context,
                                  dataList:ArrayList<TrendingListDTO>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var mContext: Context? = null
    private var listData: ArrayList<TrendingListDTO> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_trending_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {

        }
    }

    override fun getItemCount(): Int {
        return if (listData.size > 3)
            3
        else
            listData.size
    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}