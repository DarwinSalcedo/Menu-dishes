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
import com.uala.challenge.framework.NMeal
import com.uala.challenge.framework.NetworktSource
import com.uala.data.MealsRepository
import com.uala.usecase.GetListMeals
import com.uala.usecase.GetRandomMeal
import timber.log.Timber

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {


    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application

        val binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this


        val mealsRepository = MealsRepository(NetworktSource())
        val viewModelFactory = HomeViewModelFactory(
            GetListMeals(mealsRepository),
            GetRandomMeal(mealsRepository), application
        )

        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.svSearch.setOnQueryTextListener(this)

        binding.photosGrid.adapter = MealGridAdapter(MealGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            it?.let {
                Timber.e("navigate::$it")
                findNavController().navigate(
                    HomeFragmentDirections.actionShowDetail(
                        NMeal(
                            it.id,
                            it.name,
                            it.image,
                            it.instructions,
                            it.category,
                            it.url
                        )
                    )
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