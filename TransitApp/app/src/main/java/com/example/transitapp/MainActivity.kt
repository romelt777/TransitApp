package com.example.transitapp

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.transitapp.databinding.ActivityMainBinding
import com.example.transitapp.ui.dashboard.DashboardFragment
import com.example.transitapp.ui.home.HomeFragment
import com.example.transitapp.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    //get location from startActivity intent
    var latitude=0.0;
    var longitude = 0.0;

    private lateinit var binding: ActivityMainBinding

    //file to save to
    val filename = "myRoutes.txt";

    var numberRoutes = 0;


    var latitudeList : ArrayList<Float> = ArrayList()
    var longitudeList : ArrayList<Float> = ArrayList()
    var routeList : ArrayList<String> = ArrayList()

    //saved routes
    var savedRoutesList : ArrayList<String> = ArrayList();
    var stringMyBusRoutes :String ="";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Allow network operations on the main thread
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val intent = intent
        this.latitude = intent.getDoubleExtra("latitude", 0.0)
        this.longitude = intent.getDoubleExtra("longitude", 0.0)
        Log.i("Location","Latitude: "+latitude + " Longitude: " + longitude);


        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
        val feed = FeedMessage.parseFrom(url.openStream())
        //for loops => https://kotlinlang.org/docs/control-flow.html#for-loops
        for ((index,entity) in feed.entityList.withIndex()) {
//                println("EEE: ${entity}")
//                println("Vehicle #${index}")
            println("Route ID: ${entity.vehicle.trip.routeId}")
            println("Latitude: ${entity.vehicle.position.latitude}")
            println("Longitude: ${entity.vehicle.position.longitude}")
            routeList.add(entity.vehicle.trip.routeId);
            latitudeList.add(entity.vehicle.position.latitude)
            longitudeList.add(entity.vehicle.position.longitude)

            if (entity.hasTripUpdate()) {
                println("haha" + entity.tripUpdate)
            }
        }

        //select nav fragment by default, setting to home
        replaceFragment(HomeFragment())

        //setting navigation bar
        binding.navView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_dashboard -> replaceFragment(DashboardFragment())
                R.id.navigation_notifications -> replaceFragment(NotificationsFragment())

                else -> {

                }
            }
            true
        }

        loadInternalStorage();
        this.savedRoutesList= stringMyBusRoutes.split("\n") as ArrayList<String>

    }


    fun loadInternalStorage() {
        var fis = this.openFileInput(filename); //open input stream with file.
        try {
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder() //string to store
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
                numberRoutes++;
            }
            stringMyBusRoutes = sb.toString();
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }


    public fun getSavedRoutes(myBusRoutesTextView:TextView,numberRoutes:Int){
        //set to string
        val myBusRoutesString:String = myBusRoutesTextView.text.toString();

        //split the string to an array.
        this.savedRoutesList= myBusRoutesString.split("\n") as ArrayList<String>
    }

    public fun checkSavedRoutes(route:String):Boolean{
        for(i in 0 until this.savedRoutesList.size - 1){
            if(this.savedRoutesList.get(i) == route){
                return true;
            }
        }
        return false;
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        //replace framlayout with fragment
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

}