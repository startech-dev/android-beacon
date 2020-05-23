package com.example.android.flavor;

import android.os.Bundle;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
// reference link
//https://docs.mapbox.com/android/maps/overview/location-component/
public class MapActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_map);


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        this.enableLocationComponent(style);
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }

                    @SuppressWarnings( {"MissingPermission"})
                    private void enableLocationComponent(Style style) {

                        // Check if permissions are enabled and if not request
                        if (PermissionsManager.areLocationPermissionsGranted(MapActivity.this)) {

                            // Get an instance of the component
                            LocationComponent locationComponent = mapboxMap.getLocationComponent();

                            // Activate with a built LocationComponentActivationOptions object
                            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(MapActivity.this, style).build());

                            // Enable to make component visible
                            locationComponent.setLocationComponentEnabled(true);

                            // Set the component's camera mode
                            locationComponent.setCameraMode(CameraMode.TRACKING_GPS);

                            // Set the component's render mode
                            locationComponent.setRenderMode(RenderMode.COMPASS);
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(MainActivity.currentLoc.getLatitude(), MainActivity.currentLoc.getLongitude()))
                                    .zoom(18)
                                    .tilt(20)
                                    .build();
                            mapboxMap.setCameraPosition(position);

                        } else {

                            PermissionsManager permissionsManager;
//                            permissionsManager = new PermissionsManager(this);
//                            permissionsManager.requestLocationPermissions(this);

                        }
                    }
                });
            }



        });

    }
}
