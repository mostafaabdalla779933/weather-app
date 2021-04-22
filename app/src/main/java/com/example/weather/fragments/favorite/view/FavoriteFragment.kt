package com.example.weather.fragments.favorite.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.MyApplication
import com.example.weather.R
import com.example.weather.data.repos.LocalRepo
import com.example.weather.data.repos.RemoteRepo
import com.example.weather.databinding.FragmentFavoriteBinding
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory
import com.example.weather.main.view.MainActivity
import com.example.weather.map.MapActivity
import com.example.weather.map.MapsFragment
import com.example.weather.model.Favourite
import kotlinx.android.synthetic.main.fragment_maps.view.*


class FavoriteFragment : Fragment() {

    val TAG="main"
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteAdapter: FavouriteAdapter
    lateinit var  viewModelTest:FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentFavoriteBinding.inflate(layoutInflater)


        //Dagger
        viewModelTest=ViewModelProvider(this,FavouriteViewModelFactory((requireActivity().application as MyApplication).activiyComponent.getLocalRepo(),(requireActivity().application as MyApplication).activiyComponent.getRemoteRepo())).get(FavouriteViewModel::class.java)


        viewModelTest.getFvaouritesRoom()

        favoriteAdapter = FavouriteAdapter(mutableListOf(),{

            viewModelTest.deletFvaouriteRoom(it,requireContext())
        },{

            ShowDetailsFragment(it.current).show(parentFragmentManager,"details")
        })
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

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener(View.OnClickListener {


           // val navController = Navigation.findNavController(view)
           // navController.navigate(R.id.maps_fragment)
           // childFragmentManager.beginTransaction().replace(,MapsFragment()).addToBackStack("").commitNow()
           startActivity(Intent(requireActivity(), MapActivity::class.java).putExtra("from",Tag))
        })

    }
    companion object {
        val Tag="favorite"
    }

}