package com.example.weather.map

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weather.MyApplication
import com.example.weather.R
import com.example.weather.data.repos.LocalRepo
import com.example.weather.data.repos.RemoteRepo
import com.example.weather.databinding.FragmentMapsBinding
import com.example.weather.databinding.SnackbarBinding
import com.example.weather.fragments.favorite.view.FavoriteFragment
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.example.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory
import com.example.weather.fragments.setting.view.SettingFragment
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.main.viewmodel.MainViewModelFactory
import com.example.weather.model.Favourite
import com.example.weather.model.isOnline

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment(){

//    private val callback = OnMapReadyCallback { googleMap ->
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//
//
//
//        googleMap.setOnMapClickListener(GoogleMap.OnMapClickListener {
//
//            var marker:MarkerOptions= MarkerOptions()
//            marker.position(it)
//            marker.title(it.latitude.toString() + ":" +it.longitude )
//
//        })
//    }

    lateinit var binding: FragmentMapsBinding
    lateinit var viewModel: MainViewModel
    lateinit var viewModelTest:FavouriteViewModel

    val TAG="main"
    var from : String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bundle: Bundle? =this.arguments

        from=bundle?.getString("from")

        Log.i(TAG, "onCreateView: "+bundle?.getString("from"))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Dagger
        viewModel = ViewModelProvider(
                this,
                MainViewModelFactory(
                        (requireActivity().application as MyApplication).activiyComponent.getRemoteRepo(),
                        (requireActivity().application as MyApplication).activiyComponent.getLocalRepo())
        ).get(MainViewModel::class.java)

        viewModelTest=ViewModelProvider(this,
                FavouriteViewModelFactory(
                        (requireActivity().application as MyApplication).activiyComponent.getLocalRepo(),
                        (requireActivity().application as MyApplication).activiyComponent.getRemoteRepo())
        ).get(FavouriteViewModel::class.java)
        binding= FragmentMapsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        mapFragment?.getMapAsync(object :OnMapReadyCallback{
            override fun onMapReady(googleMap: GoogleMap?) {

                googleMap!!.setOnMapClickListener(object :GoogleMap.OnMapClickListener{
                    override fun onMapClick(latLng: LatLng?) {

                        if(isOnline(MyApplication.getContext())) {

                            var marker: MarkerOptions = MarkerOptions()

                            marker.position(latLng!!)
                            showSaveLocationSnackbar(latLng)

                            marker.title("" + latLng.latitude + ":" + latLng.longitude)

                            googleMap.clear()

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))

                            googleMap.addMarker(marker)

                        }
                    }
                })
            }
        })
    }




//    override fun onMapReady(googleMap: GoogleMap) {
//        googleMap.addMarker(
//                MarkerOptions()
//                        .position(LatLng(0.0, 0.0))
//                        .title("Marker")
//        )
//    }


    private fun showSaveLocationSnackbar( latLng:LatLng?) {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
        val customSnack = layoutInflater.inflate(R.layout.snackbar, null)
        val snackbarBinding = SnackbarBinding.bind(customSnack)
        //snackbar.view.setBackgroundColor(R.color.black)
        val snackbarLayout = snackbar.view as (Snackbar.SnackbarLayout)
        // Set padding to snackbar layout
        snackbarLayout.setPadding(0, 0, 0, 0)
        // Set the name of selected location
        var geocoder=Geocoder(activity?.applicationContext)
        var list:List<Address> = geocoder.getFromLocation(latLng?.latitude!!, latLng?.longitude!!,1)
        val favName=list.get(0).countryName
        snackbarBinding.txt.text = favName
        // Save selected location as favorite place
        snackbarBinding.addbtn .setOnClickListener {
            when(from){
                SettingFragment.Tag->{viewModel.setLocationFromMap(latLng.latitude.toFloat(),latLng.longitude.toFloat()) }

                FavoriteFragment.Tag->{viewModelTest.addFvaouriteToRoom(Favourite(favName,latLng?.longitude!!, latLng.latitude,null,null), requireActivity().applicationContext)}
            }
            requireActivity().finish()
        }
        snackbarLayout.addView(customSnack)
        snackbar.show()
    }
}