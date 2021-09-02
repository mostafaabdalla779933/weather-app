package com.example.weather.fragments.favorite.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.MyApplication
import com.example.weather.R
import com.example.weather.databinding.FragmentFavoriteBinding
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory
import com.example.weather.map.MapActivity
import com.example.weather.map.MapsFragment
import kotlinx.android.synthetic.main.activity_main.view.*

class FavoriteFragment : Fragment() {

    val TAG="main"
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteAdapter: FavouriteAdapter
    lateinit var  viewModelTest:FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentFavoriteBinding.inflate(layoutInflater)


        val app = (requireActivity().application as MyApplication).activiyComponent
        //Dagger
        viewModelTest=ViewModelProvider(this,FavouriteViewModelFactory(app.getLocalRepo(),app.getRemoteRepo())).get(FavouriteViewModel::class.java)


        viewModelTest.getFvaouritesRoom()

        favoriteAdapter = FavouriteAdapter(mutableListOf(),{

            viewModelTest.deletFvaouriteRoom(it,requireContext())
        },{

            ShowDetailsFragment(it.current).show(parentFragmentManager,"details")
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


            //val navController = Navigation.findNavController(view)
            //navController.navigate(R.id.maps_fragment)
          // childFragmentManager.beginTransaction().replace(R.id.fragment_containers,MapsFragment()).addToBackStack("").commitNowAllowingStateLoss()
           startActivity(Intent(requireActivity(), MapActivity::class.java).putExtra("from",Tag))
        }

    }
    companion object {
        val Tag="favorite"
    }
}


