package com.example.joeco.ecotaxiphoneapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("ALL")
public class booking_Interface extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PopupMenu.OnMenuItemClickListener {
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    MarkerOptions markerOptionsDest, markerOptionsPickUp;
    FusedLocationProviderClient mFusedLocationClient;
    private SupportPlaceAutocompleteFragment autocompleteFragment;
    private GoogleMap mMap;
    private LatLng pickUp;
    private LatLng destUp;
    private String email = null;
    private TextView price;
    private String dist;
    private String ecoStat;
    private String rating = " ";
    ArrayList<LatLng> MarkerPoints;
    ArrayList<LatLng> points;
    private GoogleApiClient mGoogleApiClient;
    private String MY_API_KEY = "AIzaSyDCV_51ykRTUKcC1wvvKWJu8PQPPJY3M9w";
    private String carType = "5 Seater";
    private String userId;
    private DatabaseReference mDatabase;
    private String review = " ";
    PolylineOptions lineOptions = null;
    private Marker carMarker;
    private Handler handler;
    private int index;
    private int next;
    private LatLng startpos;
    private LatLng endpos;
    private float v;
    private double lng;
    private double lat;
    private String p;
    private LatLng startPosition;
    private LatLng endPosition;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = getIntent().getExtras().getString("email");
        setContentView(R.layout.activity_booking__interface);

        // Initializing
        MarkerPoints = new ArrayList<>();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place destination) {
                mMap.clear();
                markerOptionsDest = new MarkerOptions();
                mMap.addMarker(markerOptionsDest.position(destination.getLatLng()).title(destination.getName().toString()));
                markerOptionsDest.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(destination.getLatLng()));
                mMap.addMarker(markerOptionsDest);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination.getLatLng(), 12.0f));

                destUp = markerOptionsDest.getPosition();
                pickUp = markerOptionsPickUp.getPosition();
                MarkerPoints.clear();
                MarkerPoints.add(pickUp);
                MarkerPoints.add(destUp);
                getDistance(pickUp,destUp);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin,dest);
                    Log.d("onLocSelected", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);

                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }

            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    private float getDistance(LatLng pickUp, LatLng destUp) {
        Location crntLocation=new Location("crntlocation");
        crntLocation.setLatitude(pickUp.latitude);
        crntLocation.setLongitude(pickUp.longitude);

        Location newLocation=new Location("newlocation");
        newLocation.setLatitude(destUp.latitude);
        newLocation.setLongitude(destUp.longitude);

        float distance =crntLocation.distanceTo(newLocation) / 1000; // in km
        DecimalFormat dc = new DecimalFormat(".##");
        String d = dc.format(distance);
        toastMessage(String.valueOf(distance));
        price = (TextView) findViewById(R.id.priceView);
        p = d;
        price.setText("Estimated Price: €" + d);
        ecoStat = String.valueOf(Double.valueOf(d)*80)+"g";
        dist = String.valueOf(d+"km");
        return distance;
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //PRIORITY_BALANCED_POWER_ACCURACY


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
            buildGoogleApiClient();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + MY_API_KEY;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //find out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.option1:
                carType = "5 seater";
                return true;
            case R.id.option2:
                carType = "8 seater";
                return true;
            case R.id.bad:
                rating = "bad";

                sendRating(rating, review);
                return true;
            case R.id.good:
                rating = "good";
                sendRating(rating, review);
                return true;
            case R.id.mrBurns:
                rating = "excellent";
                sendRating(rating, review);
                return true;
            default:
                return false;
        }
    }
    private void sendRating(String rating, String review) {
        String s = "Ratings";
        mDatabase = FirebaseDatabase.getInstance().getReference(s);

        Ratings ratings = new Ratings(email, rating, review);
        ratings.setEmail(email);
        ratings.setRating(rating);
        ratings.setReview(review);
        userId = mDatabase.push().getKey();
        mDatabase.child("Ratings").setValue(ratings);
        toastMessage("Ratings sent.");
    }


    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            Log.d("Background Task", "e.toString()");
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("booking_Interface", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng pickUp = new LatLng(location.getLatitude(), location.getLongitude());
                markerOptionsPickUp = new MarkerOptions().draggable(true);
                markerOptionsPickUp.position(pickUp);
                markerOptionsPickUp.title("Current Position");
                markerOptionsPickUp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mMap.addMarker(markerOptionsPickUp);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUp, 18));

            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(booking_Interface.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                /*
                PopupMenu popup = new PopupMenu(booking_Interface.this, v);
                popup.setOnMenuItemClickListener(booking_Interface.this);
                popup.inflate(R.menu.menu);
                popup.show();
                toastMessage("car type");
                **/
                //showPopup();
                if(p != null){
                    doCardDeets();
                }else {
                    toastMessage("Please pick a place to go");
                }
                break;

            case R.id.button2:
                //Validate ID onCreate
                String s  = "Trip" ;
                mDatabase = FirebaseDatabase.getInstance().getReference(s);

                if (destUp == null){
                    toastMessage("Please Pick a place to go..");

                }else{
                    Trip trip = new Trip(email,carType,points);
                    trip.setMarkerPoints(points);
                    trip.setEmail(email);
                    trip.setCarType(carType);

                    userId = mDatabase.push().getKey();
                    mDatabase.child("Trip").setValue(trip);
                    toastMessage("Request Send.");
                }
                break;

            case R.id.button4:
                if (destUp != null) {
                    Animate();
                }else{
                    toastMessage("Pick a place to go!");
                }

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void doCardDeets() {
        Intent intent = new Intent (booking_Interface.this, Registration_CardDetails.class);
        intent.putExtra("email",email);
        intent.putExtra("price", p);
        startActivity(intent);
    }


    private void showPopup() {
        //if you call this method correctly then you do not need to wrap
        // this method by try-catch block which affects performance

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View layout = inflater.inflate(R.layout.invoice, (ViewGroup) findViewById(R.id.popup_element), false);

        final PopupWindow pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        //get txt view from "layout" which will be added into popup window
        //before it you tried to find view in activity container
        TextView username = (TextView) layout.findViewById(R.id.username);
        username.setText("Username: " + email);
        TextView pricev = (TextView) layout.findViewById(R.id.price);
        pricev.setText(price.getText());
        TextView distv = (TextView) layout.findViewById(R.id.distance);
        distv.setText("Trip Distance:"+ dist);
        TextView eco = (TextView) layout.findViewById(R.id.ecostat);
        eco.setText("C02 Savings:" + ecoStat);

        //init your button
        Button btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
        btnClosePopup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pwindo.dismiss();
            }
        });

        final Button ratings = (Button) layout.findViewById(R.id.button3);
        ratings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText r= (EditText) layout.findViewById(R.id.review);
                review = r.getText().toString();
                PopupMenu pop = new PopupMenu(booking_Interface.this, v);
                pop.setOnMenuItemClickListener(booking_Interface.this);
                pop.inflate(R.menu.ratings);
                pop.show();
            }
        });
        //show popup window after you have done initialization of views
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    private void Animate() {

        carMarker = mMap.addMarker(new MarkerOptions().position(pickUp).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.the_car)));

        handler = new Handler();
        index = -1;
        next = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < points.size() - 1) {
                    index++;
                    next = index + 1;
                }
                if (index < points.size() - 1) {
                    startPosition = points.get(index);
                    endPosition = points.get(next);
                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        v = valueAnimator.getAnimatedFraction();
                        lng = v * endPosition.longitude + (1 - v)
                                * startPosition.longitude;
                        lat = v * endPosition.latitude + (1 - v)
                                * startPosition.latitude;
                        LatLng newPos = new LatLng(lat,lng);
                        carMarker.setPosition(newPos);
                        carMarker.setAnchor(.5f, .5f);
                        carMarker.setRotation(getBearing(startPosition, newPos));
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition
                                (new CameraPosition.Builder().target(newPos)
                                        .zoom(14f).build()));

                    }
                });
                valueAnimator.start();
                if (index != points.size() - 1) {
                    handler.postDelayed(this, 1000);
                }else {
                    showPopup();
                }
            }
        }, 1000);
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


}
