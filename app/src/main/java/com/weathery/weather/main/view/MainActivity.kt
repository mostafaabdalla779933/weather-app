package com.weathery.weather.main.view

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weathery.weather.databinding.ActivityMainBinding
import com.weathery.weather.dynamic.DeepLinkingUtil
import com.weathery.weather.util.showMessage


class  MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        intent?.let {
            DeepLinkingUtil.handleIntent(intent.data, this) { uri ->
                this.showMessage("2 ${uri.toString()}")
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //**************ask permission***********///
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            //when screen is black but not locked it will light-up
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)) {
                checkDrawOverAppsPermissionsDialog()
            }
        }
        runBackgroundPermissions()
        firebaseDynamicLinks(savedInstanceState)

    }

    private fun firebaseDynamicLinks(savedInstanceState: Bundle?){

        //Handel deep links from dynamic links
        DeepLinkingUtil.listenToDynamicLinks(savedInstanceState, intent, this) { uri ->
            this.showMessage("1 ${uri.toString()}")
        }
        DeepLinkingUtil.handleNestedIntent(intent, this)
    }

    //methods permissions
    private fun checkDrawOverAppsPermissionsDialog(){
        AlertDialog.Builder(this).setTitle("Permission request").setCancelable(false).setMessage("please allow Draw Over Apps permission to be able to use application properly")
            .setPositiveButton("Yes") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                drawOverAppPermission()

            }.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                errorWarningForNotGivingDrawOverAppsPermissions()

            }.show()

    }

    private fun errorWarningForNotGivingDrawOverAppsPermissions(){
        AlertDialog.Builder(this).setTitle("Warning").setCancelable(false).setMessage("Unfortunately the display over other apps permission" +
                " is not granted so the application might not behave properly \nTo enable this permission kindly restart the application" )
            .setPositiveButton("Yes") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            }
    }

     private fun runBackgroundPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (Build.BRAND.equals("xiaomi",true) ) {
                val intent = Intent()
                intent.component = ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
                startActivity(intent)
            } else if (Build.BRAND.equals("Honor",true) || Build.BRAND.equals("HUAWEI",true)) {
                val intent = Intent()
                intent.component = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")
                startActivity(intent)
            }
        }
    }

    private fun drawOverAppPermission (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName"))
                startActivityForResult(intent, 80)
            }
        }
    }
}