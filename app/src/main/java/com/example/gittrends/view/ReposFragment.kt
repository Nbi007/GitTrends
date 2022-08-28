package com.example.gittrends.view

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.example.gittrends.R
import com.example.gittrends.adapter.ReposAdapter
import com.example.gittrends.adapter.ReposLoadStateAdapter
import com.example.gittrends.databinding.FragmentReposBinding
import com.example.gittrends.model.Languages
import com.example.gittrends.model.ReposViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*
import java.util.*

@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {

    private val viewModel by viewModels<ReposViewModel>()

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        _binding = FragmentReposBinding.bind(view)
        val adapter = ReposAdapter()
        binding.apply {

            recycler.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = ReposLoadStateAdapter { adapter.retry() },
                    footer = ReposLoadStateAdapter { adapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.repos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                Glide.with(requireContext()).asGif().load(R.raw.retry_gif).into(binding.error)
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error               // retryAimation.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error
                emptyTv.isVisible = loadState.source.refresh is LoadState.Error

                // no results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recycler.isVisible = false
                    //emptyTv.isVisible = true
                } else {
                   // emptyTv.isVisible = false
                }
            }
        }

       // setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_repos, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        // Get SearchView autocomplete object
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setDropDownBackgroundResource(androidx.cardview.R.color.cardview_shadow_end_color)

        val newsAdapter: ArrayAdapter<String> = ArrayAdapter(
            this.requireContext(),
            R.layout.dropdown_item,
            Languages.data
        )
        searchAutoComplete.setAdapter(newsAdapter)

        // Listen to search view item on click event
        searchAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, itemIndex, _ ->
                val queryString = adapterView.getItemAtPosition(itemIndex) as String
                searchAutoComplete.setText(
                    String.format(
                        getString(R.string.search_query),
                        queryString
                    )
                )
                binding.recycler.scrollToPosition(0)
                val languageQuery = String.format(getString(R.string.query), queryString)
                viewModel.searchRepos(languageQuery)
                searchView.clearFocus()
                (activity as AppCompatActivity).supportActionBar?.title = queryString.capitalize(
                    Locale.ROOT
                )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
