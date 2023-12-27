package ca.dal.cs.csci4176.rental


import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import java.io.IOException
import kotlin.collections.ArrayList

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var databaseReference: DatabaseReference
    private val postList: MutableList<Posting?> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        init()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun init() {
        databaseReference = FirebaseDatabase.getInstance().reference.child("Posting")
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                postList.clear()
                for (snapshot in dataSnapshot.children) {
                    val posting = snapshot.getValue(Posting::class.java)
                    posting!!.postId = snapshot.key
                    postList.add(posting)

                    dropPins(posting.address!!, posting.title!!)


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun dropPins(address: String, postName: String){

        val geocoder = Geocoder(this)
        var addressList: MutableList<Address>? = ArrayList()
        addressList = geocoder.getFromLocationName(address, 2)

        try{

            if(addressList != null){

                var pin = LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude())


                map.addMarker(MarkerOptions().position(pin).title(postName))
            }

        }catch (e : IOException){

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val halifax = LatLng(44.6674303, -63.6060867)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(halifax, 12F))
        enableMyLocation()
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}
