package com.example.transitapp.ui.home

//source => https://docs.mapbox.com/android/maps/guides/install/
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.transitapp.MainActivity
import com.example.transitapp.databinding.FragmentHomeBinding
import com.example.transitapp.ui.BaseFragment
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class HomeFragment : BaseFragment() {
    var latitude=0.0;
    var longitude = 0.0;

    //marker list for displaying markers/annotation
    var markerList : ArrayList<PointAnnotationOptions> = ArrayList();

    var mapView: MapView? = null;
    //source for annotations => https://www.youtube.com/watch?v=8P74zJZJ5hQ
    //create object for annotation
    var annotationApi : AnnotationPlugin? = null;
    lateinit var annotationConfig: AnnotationConfig
    var layer100 = "map_annotation";
    var pointAnnotationManager : PointAnnotationManager? = null;


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        this.latitude=ACTIVITY.latitude
        this.longitude=ACTIVITY.longitude


        ACTIVITY.loadInternalStorage();
        ACTIVITY.savedRoutesList= ACTIVITY.stringMyBusRoutes.split("\n") as ArrayList<String>

        return root
    }

    private fun ZoomCamera(){
        mapView!!.getMapboxMap().setCamera(
            CameraOptions.Builder().center(Point.fromLngLat(this.longitude,this.latitude))
                .zoom(14.0)
                .build()
        )
    }

    private fun createMarkerOnMapBlue(){
        val customAnnotationViewBlue: TextView = binding.annotationBlue;

        customAnnotationViewBlue.post(Runnable {
            for (i in 0 until ACTIVITY.routeList.size) {
                if(ACTIVITY.checkSavedRoutes(ACTIVITY.routeList.get(i))){
                    customAnnotationViewBlue.text= ACTIVITY.routeList.get(i);
                    customAnnotationViewBlue.setBackgroundColor(Color.BLUE)
                    println("imHere" + customAnnotationViewBlue.text)

                    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(ACTIVITY.longitudeList.get(i).toDouble(), ACTIVITY.latitudeList.get(i).toDouble()))
                        .withIconImage(getBitmapFromView(customAnnotationViewBlue)!!)
                    markerList.add(pointAnnotationOptions)
                }
            }
            pointAnnotationManager?.create(markerList)
        })
    }

    private fun createMarkerOnMap(){

        val customAnnotationView: TextView = binding.annotation;
        customAnnotationView.post(Runnable {
            for (i in 0 until ACTIVITY.routeList.size) {
                if(ACTIVITY.checkSavedRoutes(ACTIVITY.routeList.get(i))){
                    println("STILL"+customAnnotationView.text)
                }else{
                    customAnnotationView.text= ACTIVITY.routeList.get(i);

                    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(ACTIVITY.longitudeList.get(i).toDouble(), ACTIVITY.latitudeList.get(i).toDouble()))
                        .withIconImage(getBitmapFromView(customAnnotationView)!!)
                    markerList.add(pointAnnotationOptions)
                }
            }
            pointAnnotationManager?.create(markerList)
        })
    }

    //convert view to bitmap without "drawtobitmap before view loaded" error
    //source => https://stackoverflow.com/questions/2801116/converting-a-view-to-bitmap-without-displaying-it-in-android
    fun getBitmapFromView(view: View): Bitmap? {
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        view.draw(canvas)
        return returnedBitmap
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

        ACTIVITY.loadInternalStorage();
        ACTIVITY.savedRoutesList= ACTIVITY.stringMyBusRoutes.split("\n") as ArrayList<String>

        mapView = binding.mapView;

//        mapView?.annotations?.cleanup();


        mapView?.getMapboxMap()!!.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded{
                override fun onStyleLoaded(style: Style) {
                    ZoomCamera()
                    mapView?.annotations?.cleanup();


                    //adding markers
                    annotationApi = mapView?.annotations
                    annotationConfig = AnnotationConfig(
                        layerId = layer100
                    )

                    //initialize point annotation manager
//                    pointAnnotationManager?.deleteAll();

                    pointAnnotationManager = annotationApi?.createPointAnnotationManager(annotationConfig)

//                    pointAnnotationManager?.deleteAll();

                    createMarkerOnMap();
                    createMarkerOnMapBlue();
                }

            }
        )
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        _binding = null
    }
}