package hilay.edu.locationaware;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Android2017 on 7/4/2017.
 */

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final int RC_LOCATION = 10;
    private GoogleMap mMap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMyLocation();
        addNessMarker();
    }

    private void addNessMarker(){
        //latitude, longitude
        LatLng ness = new LatLng(32.1143876, 34.8397601);
        mMap.addMarker(new MarkerOptions().position(ness));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ness, 17));
    }

    private boolean checkLocationPermission(){
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
        //If No Permission-> Request the permission and return false.
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), permissions, RC_LOCATION);
            return false;
        }
        return true;//return true if we have a permission
    }

    private void addMyLocation(){
        if (!checkLocationPermission())return;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Location myLocation = mMap.getMyLocation();
                Toast.makeText(getActivity(), "" + myLocation.getLatitude(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //noinspection MissingPermission
            addMyLocation();
        }
    }
}
