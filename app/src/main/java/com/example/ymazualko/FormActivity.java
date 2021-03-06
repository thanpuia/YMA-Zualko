package com.example.ymazualko;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class FormActivity extends AppCompatActivity {
    MaterialEditText name;
    MaterialEditText age;
    MaterialEditText address;
    MaterialEditText family;

    Button deathTime;
    Button funeralTime;
    MaterialEditText branch;
    TextView deathValue;
    TextView funeralValue;
    Spinner preFixed;
    Spinner familySpinner;

    String fullName;
    String familyMember;

 //   CircularImageView circularImageView;
    SingleDateAndTimePickerDialog.Builder singleBuilder;
    final Calendar myCalendar = Calendar.getInstance();

    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleTimeFormat;

    String funeralStr;
    String deathStr;
    Boolean bName = true;
    Boolean bAge = true;
    Boolean bDeathtime = false;
    Boolean bFuneralTime = false;
    Boolean bBranch = true;
    Boolean bAddress = true;
    Boolean bFamily = true;

    SharedPreferences sharedPreferences;

    String[] prefixes ;
    String[] familyName;

    ArrayAdapter arrayAdapterPrixes;
    ArrayAdapter arrayAdapterFamilyName;

    UpdateManager mUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
        mUpdateManager.start();
        mUpdateManager.addUpdateInfoListener(new UpdateManager.UpdateInfoListener() {
            @Override
            public void onReceiveVersionCode(final int code) {
                Log.d("TAG","VERCODE: "+code);
            }
            @Override
            public void onReceiveStalenessDays(final int days) {
                // Number of days passed since the user was notified of an update through the Google Play// If the user hasn't notified this will return -1 as days// You can decide the type of update you want to call
            }
        });

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(FormActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE
                        ).check();

        prefixes = new String[]{"Pu","Pi","Tv.","Nl.","Np"," "};
        familyName = new String[]{"A Nupui","A Pasal","A Pi","A Pu","A Ni","A Nau","A Pa","A Nu"};

        sharedPreferences = getApplication().getSharedPreferences("com.example.root.sharedpreferences", Context.MODE_PRIVATE);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        family = findViewById(R.id.family);
        deathTime = findViewById(R.id.deathTime);
        funeralTime = findViewById(R.id.funeralTime);
        branch = findViewById(R.id.branch);
        //circularImageView = findViewById(R.id.picture);

        deathValue =findViewById(R.id.deathTimeValue);
        funeralValue = findViewById(R.id.funeralTimeValue);

        preFixed = findViewById(R.id.preFixed);
        familySpinner = findViewById(R.id.familySpinner);

        arrayAdapterPrixes = new ArrayAdapter(this, android.R.layout.simple_spinner_item,prefixes);
        arrayAdapterPrixes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preFixed.setAdapter(arrayAdapterPrixes);

        arrayAdapterFamilyName = new ArrayAdapter(this, android.R.layout.simple_spinner_item,familyName);
        arrayAdapterFamilyName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familySpinner.setAdapter(arrayAdapterFamilyName);

//        this.simpleDateFormat = new SimpleDateFormat("d.MM.YYYY h:m a", Locale.getDefault());
        this.simpleDateFormat = new SimpleDateFormat("d MMM yyyy \nh:mm a", Locale.getDefault());

        this.simpleTimeFormat = new SimpleDateFormat("h:m a", Locale.getDefault());
        funeralStr="";
        deathStr="";
        prepopulate();
/*
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions( Manifest.permission.READ_CONTACTS,
                         Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();*/

    }


    public void imageSelectClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
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
                //circularImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }



    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }*/

    public void nextClick(View view) {

        //TESTING
       // startActivity(new Intent(this,MainActivity.class));
        if(name.getText().toString().matches("")){
            name.setError("Hming ziah tur");
            bName =false;
        }else bName = true;
        if(age.getText().toString().matches("")){
            age.setError("Kum ziah tur");
            bAge = false;
        }else bAge = true;
        if(address.getText().toString().matches("")){
            address.setError("Veng leh khua ziah tur");
            bAddress = false;
        }else bAddress = true;
        if(branch.getText().toString().matches("")){
            branch.setError("Branch hming ziah tur");
            bBranch = false;
        }else bBranch = true;
        if(family.getText().toString().matches("")){
            family.setError("A chhungte tarlan tur");
            bFamily = false;
        }else bFamily = true;

        if(bName && bAge && bAddress && bBranch && bDeathtime && bFuneralTime && bFamily){
            //Toasty.success(this,"A dik vek e!",Toasty.LENGTH_SHORT).show();
            Log.d("TAG","SUCCESS");

            String pre = preFixed.getSelectedItem().toString();
            fullName = pre+" "+name.getText().toString();

            String prefly = familySpinner.getSelectedItem().toString();
            familyMember = prefly+" "+family.getText().toString();

            //pre = pre+" "+name.getText().toString();
            sharedPreferences.edit().putString("family",familyMember).apply();
            sharedPreferences.edit().putString("name",fullName).apply();
            sharedPreferences.edit().putString("age",age.getText().toString()).apply();
            sharedPreferences.edit().putString("address",address.getText().toString()).apply();
            sharedPreferences.edit().putString("branch",branch.getText().toString()).apply();
            sharedPreferences.edit().putString("funeral",sharedPreferences.getString("funeral","")).apply();
            sharedPreferences.edit().putString("death",sharedPreferences.getString("death","")).apply();

//            Intent intent = new Intent(this,MapsActivity.class);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("family",familyMember);

            intent.putExtra("name",fullName);
            intent.putExtra("age",age.getText().toString());
            intent.putExtra("address",address.getText().toString());
            intent.putExtra("branch",branch.getText().toString());
            intent.putExtra("funeral",funeralStr);
            intent.putExtra("death",deathStr);

            startActivity(intent);

        }

    }

    private void prepopulate() {
        if(!sharedPreferences.getString("name","").matches("")){
            bName = true;
            name.setText(sharedPreferences.getString("name",""));
        }else bName = false;

        if(!sharedPreferences.getString("age","").matches("")){
            bAge = true;
            age.setText(sharedPreferences.getString("age",""));
        }else bAge = false;

        if(!sharedPreferences.getString("address","").matches("")){
            bAddress = true;
            address.setText(sharedPreferences.getString("address",""));
        }else bAddress = false;


        if(!sharedPreferences.getString("branch","").matches("")){
            bBranch = true;
            branch.setText(sharedPreferences.getString("branch",""));
        }else bBranch = false;
        if(!sharedPreferences.getString("family","").matches("")){
            bFamily = true;
            family.setText(sharedPreferences.getString("family",""));
        }else bFamily = false;

        if(!sharedPreferences.getString("funeral","").matches("")){
            bFuneralTime = true;
            funeralTime.setText(sharedPreferences.getString("funeral",""));
            funeralStr = sharedPreferences.getString("funeral","");
        }else bFuneralTime = false;

        if(!sharedPreferences.getString("death","").matches("")){
            bDeathtime = true;
            deathTime.setText(sharedPreferences.getString("death",""));
            deathStr = sharedPreferences.getString("death","");
        }else bDeathtime = false;
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
                        //deathTime.setText(simpleDateFormat.format(date));
                        deathValue.setText(simpleDateFormat.format(date));
                        deathStr = simpleDateFormat.format(date);
                        sharedPreferences.edit().putString("death",deathStr).apply();
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
                        //funeralTime.setText(simpleDateFormat.format(date));
                        funeralValue.setText(simpleDateFormat.format(date));
                        funeralStr = simpleDateFormat.format(date);
                        sharedPreferences.edit().putString("funeral",funeralStr).apply();
                    }
                });
        singleBuilder.display();
    }
}