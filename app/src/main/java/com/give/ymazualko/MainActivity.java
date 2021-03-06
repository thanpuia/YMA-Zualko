package com.give.ymazualko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView name;
    TextView funeral;
    TextView death;
    TextView branch;
    TextView family;
    TextView address;
    TableRow addressRow;
    TableRow familyRow1;
    TableRow deathRow;
    TableRow funeralRow;

    CircularImageView picture;
   // ImageView ymalogo;
    SharedPreferences sharedPreferences;
    SingleDateAndTimePickerDialog.Builder singleBuilder;

    SimpleDateFormat simpleDateFormat;
    private GoogleMap mMap;
    Menu menu;
    Bitmap bitmap;

    private Marker myMarker;
    private String myMarkerTitle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }else if(item.getItemId()==R.id.create){
            takeScreenshot();
            //ss();
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Drawable drawable = getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark);
        //bitmap = ((BitmapDrawable) drawable).getBitmap();

        myMarkerTitle = "Add Marker";
        sharedPreferences = getApplication().getSharedPreferences("com.example.root.sharedpreferences", Context.MODE_PRIVATE);
        this.simpleDateFormat = new SimpleDateFormat("d MMM yyyy \nh:mm a", Locale.getDefault());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        name = findViewById(R.id.hming);
        funeral = findViewById(R.id.vuiHun);
        death = findViewById(R.id.thihHun);
        branch = findViewById(R.id.branchHming);
        family = findViewById(R.id.chhungte);
        address = findViewById(R.id.veng);
        picture =findViewById(R.id.thlalak);
      //  ymalogo = findViewById(R.id.ymalogo);

        addressRow = findViewById(R.id.addressRow);
        familyRow1 = findViewById(R.id.familyrow1);
        deathRow = findViewById(R.id.deathRow);
        funeralRow = findViewById(R.id.funeralRow);

        branch.setText("YOUNG MIZO ASSOCIATION : "+getIntent().getStringExtra("branch"));
        name.setText(getIntent().getStringExtra("name")+", K- "+getIntent().getStringExtra("age"));

        family.setText(getIntent().getStringExtra("family"));
        address.setText(getIntent().getStringExtra("address"));

        funeral.setText(getIntent().getStringExtra("funeral"));
        death.setText(getIntent().getStringExtra("death"));

      /*  Glide.with(getApplicationContext())
                 .load(R.drawable.yma)
                .centerCrop()
                .transform(new BlurTransformation(10,2))
                .into(ymalogo);*/

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Thlalak thlanna"), 1);

            }
        });
/*  sharedPreferences.edit().putString("family",family.getText().toString()).apply();
            sharedPreferences.edit().putString("name",name.getText().toString()).apply();
            sharedPreferences.edit().putString("age",age.getText().toString()).apply();
            sharedPreferences.edit().putString("address",address.getText().toString()).apply();
            sharedPreferences.edit().putString("branch",branch.getText().toString()).apply();
            sharedPreferences.edit().putString("funeral",sharedPreferences.getString("funeral","")).apply();
            sharedPreferences.edit().putString("death",sharedPreferences.getString("death","")).apply();
*/
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                edittext.setText(sharedPreferences.getString("branch",""));
                alert.setTitle("Branch ziah");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        branch.setText("YOUNG MIZO ASSOCIATION : "+YouEditTextValuea);
                        sharedPreferences.edit().putString("branch",YouEditTextValuea).apply();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                edittext.setText(sharedPreferences.getString("name",""));

                edittext.setHint("Pu Xyz, K-99");
                //alert.setMessage("Branc");
                alert.setTitle("Hming leh kum ziah");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        name.setText(YouEditTextValuea);
                        sharedPreferences.edit().putString("name",YouEditTextValuea).apply();

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
        addressRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                edittext.setHint("Electric Veng, Aizawl Mizoram");
                edittext.setText(sharedPreferences.getString("address",""));
                alert.setTitle("Veng");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        address.setText(YouEditTextValuea);
                        sharedPreferences.edit().putString("address",YouEditTextValuea).apply();

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });

        familyRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFamily();
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFamily();
            }
        });

        deathRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","CLICK");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 21);
                calendar.set(Calendar.MINUTE, 50);

                final Date defaultDate = calendar.getTime();

                singleBuilder = new SingleDateAndTimePickerDialog.Builder(MainActivity.this)
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
                              //  bDeathtime = true;
                                //     singleTimeText.setText(simpleTimeFormat.format(date));
                                //deathTime.setText(simpleDateFormat.format(date));
                                death.setText(simpleDateFormat.format(date));
                                String deathStr = simpleDateFormat.format(date);
                                sharedPreferences.edit().putString("death",deathStr).apply();
                            }
                        });
                singleBuilder.display();
            }
        });
        funeralRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","CLICK");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 21);
                calendar.set(Calendar.MINUTE, 50);

                final Date defaultDate = calendar.getTime();

                singleBuilder = new SingleDateAndTimePickerDialog.Builder(MainActivity.this)
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
                        .title("Vui ni leh dar  zat")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                //  bDeathtime = true;
                                //     singleTimeText.setText(simpleTimeFormat.format(date));
                                //deathTime.setText(simpleDateFormat.format(date));
                                funeral.setText(simpleDateFormat.format(date));
                                String deathStr = simpleDateFormat.format(date);
                                sharedPreferences.edit().putString("funeral",deathStr).apply();
                            }
                        });
                singleBuilder.display();
            }
        });
    }

    public void ss(){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "UniqueFileName" + ".jpg");
        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);


        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(this,"Roor +"+e,Toast.LENGTH_SHORT).show();


            e.printStackTrace();
            Log.d("TAG","ER"+e);
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
    private void myFamily() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText edittext = new EditText(MainActivity.this);
        edittext.setHint("A nupui: Pi XYZ");
        edittext.setText(sharedPreferences.getString("family",""));
        alert.setTitle("A Chhungte");
        alert.setView(edittext);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValuea = edittext.getText().toString();
                family.setText(YouEditTextValuea);
                sharedPreferences.edit().putString("family",YouEditTextValuea).apply();

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (requestCode == 1) {
                Uri uri = data.getData();
                UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), "iamge.jpg")))

                        .withMaxResultSize(500, 500)
                        .start(this);
            }

            if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    picture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
            }
        }catch (Exception e){
            Log.d("TAG","Image Select Exception: "+e);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //TODO : hei a user lastknownlocation atang a lak tur
        LatLng elctricVeng = new LatLng( 23.736561, 92.718193);
      //  mMap.addMarker(new MarkerOptions().position(elctricVeng).title("Marker in Sydney"));

        mMap.animateCamera(
                CameraUpdateFactory.
                        newLatLngZoom(elctricVeng, 16.0f));

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //MITTHI TE IN BUL
        //Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.add_more);
        myMarker = mMap.addMarker(
                new MarkerOptions()
                    .title(myMarkerTitle)
                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.add_marker)
                        )
                    .position(new LatLng(23.736571,92.718193))
                    .draggable(true)

        );


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDragStart(Marker marker)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onMarkerDragEnd(Marker marker)
            {
                // TODO Auto-generated method stub
                //   lat     = marker.getPosition().latitude;
                //  lng     = marker.getPosition().longitude;
            }

            @Override
            public void onMarkerDrag(Marker marker)
            {
                // TODO Auto-generated method stub
            }
        });

//        mMap.addMarker(
//                new MarkerOptions()
//                        .title("Dynamic")
//                        .position(new LatLng(23.736571,92.718193))
//                        .draggable(true)
//        ).showInfoWindow();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.equals(myMarker)){
                   // Toast.makeText(getApplicationContext(),"CLCICKCKC",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    final EditText edittext = new EditText(MainActivity.this);
                    edittext.setHint("Landmark (tu te in bul");
                    edittext.setText(sharedPreferences.getString("landmark",""));
                    alert.setTitle("Landmark");
                    alert.setView(edittext);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String YouEditTextValuea = edittext.getText().toString();
                            //address.setText(YouEditTextValuea);
                            myMarkerTitle = YouEditTextValuea;

                            mMap.addMarker(
                                    new MarkerOptions()
                                            .title(myMarkerTitle)
                                            .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.landmark)
                                            )
                                            .position(new LatLng(23.736571,92.718193))
                                            .draggable(true)
                            ).showInfoWindow();


                            sharedPreferences.edit().putString("landmark",YouEditTextValuea).apply();

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.show();
                }
                return false;
            }
        });


    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}