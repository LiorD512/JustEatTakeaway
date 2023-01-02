package com.liordahan.justeattakeaway.features.restaurants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.liordahan.justeattakeaway.R
import com.liordahan.justeattakeaway.data.remote.models.Restaurant
import com.liordahan.justeattakeaway.databinding.FragmentRestaurantsBinding
import com.liordahan.justeattakeaway.extensions.handleProgressBar
import com.liordahan.justeattakeaway.extensions.showErrorToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantsFragment : Fragment(R.layout.fragment_restaurants), RestaurantsAdapter.RestaurantAdapterListener {

    private val viewModel by viewModels<RestaurantsViewModel>()
    private val binding by viewBinding(FragmentRestaurantsBinding::bind)
    private lateinit var restaurantsAdapter: RestaurantsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        observeViewModelState()
        observeViewModelEvents()
    }

    private fun initUi(){
        initAdapter()
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        updateInputsWithState(state)
                    }
                }
            }
        }
    }

    private fun observeViewModelEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        RestaurantsEvents.Error -> {
                            showErrorToast(getString(R.string.failed_to_load_restaurants))
                        }
                    }
                }
            }
        }
    }

    private fun updateInputsWithState(state: RestaurantsState) {
        updateRestaurantList(state.restaurants)
        binding.restaurantFragmentProgressBar.handleProgressBar(state.inProgress)
    }

    private fun initAdapter(){
        binding.restaurantFragmentRv.apply {
            restaurantsAdapter = RestaurantsAdapter(this@RestaurantsFragment)
            adapter = restaurantsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = object : DefaultItemAnimator() {
                override fun animateChange(
                    oldHolder: RecyclerView.ViewHolder,
                    newHolder: RecyclerView.ViewHolder,
                    preInfo: ItemHolderInfo,
                    postInfo: ItemHolderInfo
                ): Boolean {
                    if (preInfo.top == postInfo.top) {
                        dispatchChangeFinished(oldHolder, true)
                        dispatchChangeFinished(newHolder, true)
                        return true
                    }
                    return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
                }
            }
        }
    }

    private fun updateRestaurantList(restaurants: List<Restaurant>?){
        restaurantsAdapter.submitList(restaurants)
    }

    override fun onFavoriteClicked(isChecked: Boolean, restaurantId: Long) {
        viewModel.onFavoriteClicked(isChecked, restaurantId)
    }

}