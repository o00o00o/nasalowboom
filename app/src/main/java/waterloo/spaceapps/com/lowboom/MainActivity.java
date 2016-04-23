package waterloo.spaceapps.com.lowboom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LatLng mLatLng;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mButton = (Button) findViewById(R.id.sonic_boom);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBoomLocation();
            }
        });

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        centerMapOnMyLocation();
    }

    private void drawBoomLocation(){
        if(mMap != null) {
            int d = 500; // diameter
            Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bm);
            Paint p = new Paint();
            p.setColor(getResources().getColor(R.color.colorPrimary));
            c.drawCircle(d / 2, d / 2, d / 2, p);

            Bitmap bm2 = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
            Canvas c2 = new Canvas(bm2);
            Paint p2 = new Paint();
            p.setColor(getResources().getColor(R.color.colorAccent));
            c2.drawCircle(d / 2, d / 2, d / 2, p2);
            int radiusM = 1000;

            // generate BitmapDescriptor from circle Bitmap
            BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);
            BitmapDescriptor bmD2 = BitmapDescriptorFactory.fromBitmap(bm2);

            // mapView is the GoogleMap
            mMap.addGroundOverlay(new GroundOverlayOptions().
                    image(bmD).
                    position(mLatLng, radiusM * 2, radiusM * 2).
                    transparency(0.4f));

            mMap.addGroundOverlay(new GroundOverlayOptions().
                    image(bmD2).
                    position(mLatLng, (radiusM + 500) * 2, (radiusM + 500) * 2).
                    transparency(0.4f));
        }
    }


    private void centerMapOnMyLocation() {

        mMap.setMyLocationEnabled(true);

        mLatLng = new LatLng(43.451096, -80.498792);
        mMap.addMarker(new MarkerOptions().position(mLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 14));

    }
}
