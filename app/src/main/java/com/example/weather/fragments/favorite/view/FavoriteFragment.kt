package com.example.weather.fragments.favorite.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.FragmentFavoriteBinding
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory
import com.example.weather.map.MapActivity
import com.example.weather.model.Favourite

class FavoriteFragment : Fragment(), OnClickFav {

    val TAG="main"
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteAdapter: FavouriteAdapter
    lateinit var  viewModelTest:FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentFavoriteBinding.inflate(layoutInflater)

        viewModelTest=ViewModelProvider(this,FavouriteViewModelFactory()).get(FavouriteViewModel::class.java)

        viewModelTest.getFvaouritesRoom(requireContext())

        favoriteAdapter = FavouriteAdapter(mutableListOf(),this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        viewModelTest.favouriteLiveData.observe(viewLifecycleOwner, Observer {

            favoriteAdapter.list=it
            favoriteAdapter.notifyDataSetChanged()
        })

        binding.recyclFav.apply {

            layoutManager= LinearLayoutManager(activity)
            adapter=favoriteAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
           startActivity(Intent(requireActivity(),MapActivity::class.java).putExtra("from",Tag))
        })

    }
    companion object {
        val Tag="favorite"
    }

    override fun onItemDelete(favourite: Favourite) {
        viewModelTest.deletFvaouriteRoom(favourite,requireContext())
    }

    override fun onItemClick(favourite: Favourite) {
        ShowDetailsFragment(favourite.current).show(parentFragmentManager,"details")
    }
}