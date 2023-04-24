package com.weathery.weather.map

import android.location.Geocoder
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weathery.weather.R
import com.weathery.weather.databinding.FragmentMapsBinding
import com.weathery.weather.databinding.SnackbarBinding
import com.weathery.weather.fragments.favorite.view.FavoriteFragment
import com.weathery.weather.fragments.favorite.viewmodel.FavouriteViewModel
import com.weathery.weather.fragments.favorite.viewmodel.FavouriteViewModelFactory
import com.weathery.weather.fragments.setting.view.SettingFragment
import com.weathery.weather.main.viewmodel.MainViewModel
import com.weathery.weather.main.viewmodel.MainViewModelFactory
import com.weathery.weather.model.Favourite
import com.weathery.weather.model.isOnline
import com.google.android.gms.maps.CameraUpdateFactory
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
    lateinit var snackbar :Snackbar
    var from : String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            from= it.getString("from")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Dagger
        viewModel = ViewModelProvider(
                this,
                MainViewModelFactory(
                        (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent.getRemoteRepo(),
                        (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent.getLocalRepo())
        ).get(MainViewModel::class.java)

        viewModelTest=ViewModelProvider(this,
                FavouriteViewModelFactory(
                        (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent.getLocalRepo(),
                        (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent.getRemoteRepo())
        ).get(FavouriteViewModel::class.java)
        binding= FragmentMapsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)


        mapFragment?.getMapAsync { googleMap ->
            googleMap!!.setOnMapClickListener { latLng ->
                if (isOnline(com.weathery.weather.MyApplication.getContext())) {
                    val marker = MarkerOptions()
                    marker.position(latLng!!)
                    showSaveLocationSnackbar(latLng)
                    marker.title("" + latLng.latitude + ":" + latLng.longitude)
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
                    googleMap.addMarker(marker)

                }
            }
        }
    }

 /*   override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
                MarkerOptions()
                        .position(LatLng(0.0, 0.0))
                        .title("Marker")
        )
    }*/

    private fun showSaveLocationSnackbar(latLng:LatLng?){

        SnackbarBinding.bind(layoutInflater.inflate(R.layout.snackbar, null)).let { snackbarBinding ->
            (snackbar.view as ViewGroup).removeAllViews()
            (snackbar.view as ViewGroup).addView(snackbarBinding.root)

            snackbar.view.setPadding(20, 10, 20, 30)
            snackbar.view.setBackgroundResource(R.color.blue)
            snackbar.view.elevation = 0f

            snackbar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), android.R.color.transparent)
            )

            var favName :String? = ""
            try {
                val geocoder = Geocoder(requireActivity().applicationContext)
                val list=
                    geocoder.getFromLocation(latLng?.latitude ?: 0.0, latLng?.longitude ?: 0.0, 1)
                favName = list?.getOrNull(0)?.countryName

                snackbarBinding.txt.text = favName
                snackbarBinding.addbtn.setOnClickListener {
                    when (from) {
                        SettingFragment.Tag -> {
                            viewModel.setLocationFromMap(latLng?.latitude?.toFloat() ?: 0F, latLng?.longitude?.toFloat() ?: 0F)
                        }
                        FavoriteFragment.Tag -> {
                            viewModelTest.addFvaouriteToRoom(Favourite(favName ?: "", latLng?.longitude!!, latLng.latitude, null, null), requireContext())
                        }
                    }
                    snackbar.dismiss()
                    findNavController().popBackStack()
                }
                snackbar.show()
            } catch (e: Exception) { }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(snackbar.isShown)
          snackbar.dismiss()
    }

}