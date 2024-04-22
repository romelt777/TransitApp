package com.example.transitapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class StartActivity : AppCompatActivity() {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_CODE: Int = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //initialize provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        //verify the permission granted
        if (requestCode == REQUEST_CODE) {
            //only one request, so length should be one
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //now calling getLocation because the location should be granted
                getLocation()
            }
        }
    }

    private fun getLocation() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            //permission granted - get location from device
            Log.i("TESTING", "Permission granted!!!")


            fusedLocationProviderClient!!.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    val latitude = location.latitude
                    val longitude = location.longitude

                    //android convention to pass one activiity to another.
                    //1.click button --> open browser, (let phone determine intent of browser)
                    //2.click --> explicity intent, opens another activity/module in your app

                    //needs context then need activity its going to invoke.
                    val intent = Intent(this@StartActivity, MainActivity::class.java)

                    //similar to bundle, passing data to activity.
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)

                    startActivity(intent)
                }
        } else{
            askPermission()
        }
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE);
    }

}