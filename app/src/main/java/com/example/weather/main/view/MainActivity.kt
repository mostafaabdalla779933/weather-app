package com.example.weather.main.view

import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.MyApplication
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.di.ActivityComponent
import com.example.weather.di.DaggerActivityComponent
import java.util.*


class  MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG="main"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       setLocale((application as MyApplication).activiyComponent.getLocalRepo().getlanguge())

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //**************ask permission***********///
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            //when screen is black but not locked it will light-up
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)) {
                checkDrawOverAppsPermissionsDialog();
            }
        }
        runBackgroundPermissions()

    }


    //methods permissions
    private fun checkDrawOverAppsPermissionsDialog(){
        AlertDialog.Builder(this).setTitle("Permission request").setCancelable(false).setMessage("please allow Draw Over Apps permission to be able to use application properly")
            .setPositiveButton("Yes") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                drawOverAppPermission()

            }.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                errorWarningForNotGivingDrawOverAppsPermissions();

            }.show()

    }

    private fun errorWarningForNotGivingDrawOverAppsPermissions(){
        AlertDialog.Builder(this).setTitle("Warning").setCancelable(false).setMessage("Unfortunately the display over other apps permission" +
                " is not granted so the application might not behave properly \nTo enable this permission kindly restart the application" )
            .setPositiveButton("Yes") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            }
    }

     fun runBackgroundPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (Build.BRAND.equals("xiaomi",true) ) {
                var intent = Intent()
                intent.setComponent(ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"))
                startActivity(intent);
            } else if (Build.BRAND.equals("Honor",true) || Build.BRAND.equals("HUAWEI",true)) {
                var intent = Intent()
                intent.setComponent(ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                startActivity(intent);
            }
        }
    }

    fun drawOverAppPermission (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                var intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 80);
            }
        }
    }


    fun setLocale(lang:String ) {
        val resources: Resources = this.getResources()
        val locale = Locale(lang)
        Locale.setDefault(locale);
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

}