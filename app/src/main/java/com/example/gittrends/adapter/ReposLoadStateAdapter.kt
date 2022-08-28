package com.example.gittrends.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gittrends.databinding.ReposeLoadStateBinding


class ReposLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ReposLoadStateAdapter.LoadStateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ReposeLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ReposeLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            /*binding.btnRetry.setOnClickListener{
                retry.invoke()
            }*/

        }

        fun bind(loadState: LoadState) {
            binding.apply {


               /* progress.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                error.isVisible = loadState !is LoadState.Loading*/
            }
        }

    }

}