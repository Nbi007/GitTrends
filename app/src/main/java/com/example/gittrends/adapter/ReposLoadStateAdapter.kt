package com.example.gittrends.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gittrends.GitTrendApplication
import com.example.gittrends.R
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
            binding.btnRetry.setOnClickListener{
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                skeletonLayout.startLoading()
                Glide.with(GitTrendApplication.appContext).asGif().load(R.raw.retry_gif).into(binding.error)
                btnRetry.isVisible = loadState is LoadState.Error
                error.isVisible = loadState is LoadState.Error
                emptyTv.isVisible = loadState is LoadState.Error
                skeletonLayout.isVisible =loadState is LoadState.Loading
                if (loadState is LoadState.Error==true){
                    skeletonLayout.isVisible =false
                }

            }
        }

    }

}