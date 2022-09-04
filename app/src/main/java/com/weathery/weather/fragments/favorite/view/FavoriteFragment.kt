package com.weathery.weather.fragments.favorite.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weathery.weather.MyApplication
import com.weathery.weather.R
import com.weathery.weather.databinding.FragmentFavoriteBinding
import com.weathery.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.weathery.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory

class FavoriteFragment : Fragment() {

    val TAG="main"
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteAdapter: FavouriteAdapter
    lateinit var  viewModelTest:FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentFavoriteBinding.inflate(layoutInflater)


        val app = (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent
        //Dagger
        viewModelTest=ViewModelProvider(this,FavouriteViewModelFactory(app.getLocalRepo(),app.getRemoteRepo())).get(FavouriteViewModel::class.java)


        viewModelTest.getFvaouritesRoom()
        parentFragmentManager.setFragmentResultListener("show",this) { key,bundle->
            if (key=="show"){
                Log.i("main", "onCreate: ${bundle.getString("show")}")
            }
        }

        favoriteAdapter = FavouriteAdapter(mutableListOf(),{

            viewModelTest.deletFvaouriteRoom(it,requireContext())
        },{

            ShowDetailsFragment().also { dialog->
                dialog.isCancelable =true
                dialog.arguments = bundleOf(ShowDetailsFragment.CURRENT to it.current)
                dialog.show(parentFragmentManager,"details")
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModelTest.favouriteLiveData.observe(viewLifecycleOwner,{
            favoriteAdapter.list=it
            favoriteAdapter.notifyDataSetChanged()
        })

        viewModelTest.favouriteLiveDataSearch.observe(viewLifecycleOwner,{
            favoriteAdapter.list=it
            favoriteAdapter.notifyDataSetChanged()
        })

        binding.recyclFav.apply {

            layoutManager= LinearLayoutManager(activity)
            adapter=favoriteAdapter
        }

        binding.search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModelTest.search(newText ?: "")
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(TAG, "onQueryTextSubmit: $query")
                return false
            }
        })


        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.maps_fragment, bundleOf("from" to Tag))
        }

    }
    companion object {
        val Tag="favorite"
    }
}


