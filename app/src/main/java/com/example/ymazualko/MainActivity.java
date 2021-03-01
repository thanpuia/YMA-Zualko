package com.example.ymazualko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.glide.transformations.BlurTransformation;

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
    ImageView ymalogo;
    SharedPreferences sharedPreferences;
    SingleDateAndTimePickerDialog.Builder singleBuilder;

    SimpleDateFormat simpleDateFormat;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ymalogo = findViewById(R.id.ymalogo);

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

        Glide.with(getApplicationContext())
                 .load(R.drawable.yma)
                .centerCrop()
                .transform(new BlurTransformation(10,2))
                .into(ymalogo);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                //alert.setMessage("Branc");
                alert.setTitle("Branch ziah");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        branch.setText("YOUNG MIZO ASSOCIATION : "+YouEditTextValuea);
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
                edittext.setHint("Pu Xyz, K-99");
                //alert.setMessage("Branc");
                alert.setTitle("Hming leh kum ziah");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        name.setText(YouEditTextValuea);
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
                //alert.setMessage("Branc");
                alert.setTitle("Veng");
                alert.setView(edittext);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValuea = edittext.getText().toString();
                        address.setText(YouEditTextValuea);
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

    private void myFamily() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText edittext = new EditText(MainActivity.this);
        edittext.setHint("A nupui: Pi XYZ");
        //alert.setMessage("Branc");
        alert.setTitle("A Chhungte");
        alert.setView(edittext);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValuea = edittext.getText().toString();
                family.setText(YouEditTextValuea);
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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng elctricVeng = new LatLng( 23.736561, 92.718193);
      //  mMap.addMarker(new MarkerOptions().position(elctricVeng).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(elctricVeng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.736561,92.718193), 16.0f));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}