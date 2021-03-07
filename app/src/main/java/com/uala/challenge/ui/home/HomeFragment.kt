package com.uala.challenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uala.challenge.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {


    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.svSearch.setOnQueryTextListener(this)

        binding.photosGrid.adapter = MealGridAdapter(MealGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            it?.let {
                Timber.e("navigate::$it")
                findNavController().navigate(
                    HomeFragmentDirections.actionShowDetail(it)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.e(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.e(newText)
        newText?.let {
            viewModel.getListMeals(it)
        }
        return false
    }
}