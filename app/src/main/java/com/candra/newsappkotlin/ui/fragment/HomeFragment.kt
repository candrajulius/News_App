package com.candra.newsappkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.databinding.FragmentHomeLayoutBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.ui.adapter.AllAdapter
import com.candra.newsappkotlin.ui.adapter.LoadStateAdapter
import com.candra.newsappkotlin.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment()
{
    private var _binding: FragmentHomeLayoutBinding? = null
    private val binding get() = _binding!!
    private val adapterAll by lazy { AllAdapter(requireActivity()) }
    private val homeViewModel by viewModels<HomeViewModel>()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        addToRecyclerView()

        setComponentAll()


    }

    private fun setAdapter(){
        val adapterLoading = adapterAll.withLoadStateFooter(
            footer = LoadStateAdapter{adapterAll.retry()}
        )

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
            adapter = adapterLoading
        }
    }

    private fun setComponentAll() {
        homeViewModel.getTopHeadlinesNewsByCountryAndCategory(Constant.ID, Constant.HOME)
        readAllData()
    }


    private fun addToRecyclerView(){

        lifecycleScope.launch {
            adapterAll.loadStateFlow.collect{
                when(it.refresh){
                    is LoadState.Loading -> {
                        isLoading = true
                        homeViewModel.setLoading(isLoading)
                    }
                    is LoadState.Error -> {Toast.makeText(requireActivity(),getString(R.string.something_wrong),Toast.LENGTH_SHORT).show()}
                    is LoadState.NotLoading -> {
                        isLoading = false
                       homeViewModel.setLoading(isLoading)
                    }
                }
            }
        }

    }

    private fun readAllData(){
        homeViewModel.isLoading.observe(viewLifecycleOwner,this::populateLoading)
        homeViewModel.topHeadlineNews.observe(viewLifecycleOwner,this::populateNews)
    }

    private fun populateLoading(isLoading: Boolean){
        with(binding) {
            if (isLoading) {
                shimmerEffect.startShimmer()
                shimmerEffect.visibility = View.VISIBLE
                rvHome.visibility = View.GONE
            }else{
                shimmerEffect.hideShimmer()
                shimmerEffect.visibility = View.GONE
                rvHome.visibility = View.VISIBLE
            }
        }
    }


    private fun populateNews(it: PagingData<NewsModel>){
        adapterAll.submitData(lifecycle,it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}