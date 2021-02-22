package com.example.ymazualko;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MaterialEditText name;
    MaterialEditText age;
    MaterialEditText section;
    Button deathTime;
    Button funeralTime;
    MaterialEditText branch;
    SingleDateAndTimePickerDialog.Builder singleBuilder;
    final Calendar myCalendar = Calendar.getInstance();

    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleTimeFormat;
    SimpleDateFormat simpleDateOnlyFormat;
    SimpleDateFormat simpleDateLocaleFormat;

    Boolean bName = true;
    Boolean bAge = true;
    Boolean bDeathtime = false;
    Boolean bFuneralTime = false;
    Boolean bBranch = true;
    Boolean bSection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        section = findViewById(R.id.section);
        deathTime = findViewById(R.id.deathTime);
        funeralTime = findViewById(R.id.funeralTime);
        branch = findViewById(R.id.branch);

        this.simpleDateFormat = new SimpleDateFormat("d.MM.YYYY HH:mm", Locale.getDefault());
        this.simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        this.simpleDateOnlyFormat = new SimpleDateFormat("EEE d MMM", Locale.getDefault());
        this.simpleDateLocaleFormat = new SimpleDateFormat("EEE d MMM", Locale.ENGLISH);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng midLatLng = googleMap.getCameraPosition().target;
                //Toast.makeText(getApplicationContext(),"Location:"+midLatLng,Toast.LENGTH_SHORT).show();
                LatLng userLocationSet = new LatLng(midLatLng.latitude,midLatLng.longitude);

                Log.d("TAG","LatLng: "+userLocationSet);
            }
        });
    }


    public void deathTimeClick(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 50);

        final Date defaultDate = calendar.getTime();

        singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)
                //  .setTimeZone(TimeZone.getDefault())
                .bottomSheet()
                .curved()
                .defaultDate(defaultDate)

                //.titleTextColor(Color.GREEN)
                //.backgroundColor(Color.BLACK)
                //.mainColor(Color.GREEN)

                .displayMinutes(true)
                .displayHours(true)
                .displayDays(true)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {
                        Log.d("TAG", "Dialog displayed");
                    }
                })
                .title("Thih ni leh dar  zat")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        bDeathtime = true;
                        //     singleTimeText.setText(simpleTimeFormat.format(date));
                        deathTime.setText(simpleDateFormat.format(date)+simpleTimeFormat.format(date));
                    }
                });
        singleBuilder.display();

    }

    public void funeralClick(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 50);

        final Date defaultDate = calendar.getTime();

        singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)
                //  .setTimeZone(TimeZone.getDefault())
                .bottomSheet()
                .curved()
                .defaultDate(defaultDate)

                //.titleTextColor(Color.GREEN)
                //.backgroundColor(Color.BLACK)
                //.mainColor(Color.GREEN)

                .displayMinutes(true)
                .displayHours(true)
                .displayDays(true)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {
                        Log.d("TAG", "Dialog displayed");
                    }
                })
                .title("Thih ni leh dar  zat")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        //     singleTimeText.setText(simpleTimeFormat.format(date));
                        bFuneralTime = true;
                         funeralTime.setText(simpleDateFormat.format(date)+simpleTimeFormat.format(date));
                    }
                });
        singleBuilder.display();
    }

    public void nextClick(View view) {

        if(name.getText().toString().matches("")){
            name.setError("sfsdsdf");
            bName =false;
        }
        if(age.getText().toString().matches("")){
            age.setError("sfsdsdf");
            bAge = false;
        }
        if(section.getText().toString().matches("")){
            section.setError("sfsdsdf");
            bSection = false;
        }
        if(branch.getText().toString().matches("")){
            branch.setError("sfsdsdf");
            bBranch = false;
        }

        if(bName && bAge && bSection && bBranch && bDeathtime && bFuneralTime){
            Toasty.success(this,"YES",Toasty.LENGTH_SHORT).show();
            Log.d("TAG","SUCCESS");
        }


    }
}