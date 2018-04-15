package com.example.carol.kilimodigital2;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("VisibleForTests")
public class Sell extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;

    private EditText txtTitle, txtPrice, txtDescription, txtName, txtContact;

    Button postAd;
    String sTitle, sPrice, sDescription, sName, sContact, sCategory;
    int id;

    private ImageView camview;
    private static final int GALLERY_REQUEST = 2;
    private StorageReference mStorage;
    private DatabaseReference mReference;
    ProgressDialog pd;

    DatabaseReference usersRef;
    private int itemId = 0;
    String uid;
    private String userChoosenTask;
    private ImageView icClear, photo;
    private TextView addPhoto;
    String item_key = null;
    DatabaseReference dbRef;
    StorageReference filepath = null;
    Uri resultUri;
    Uri imageUri;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sell);



        pd = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mReference = FirebaseDatabase.getInstance().getReference().child("Items");

        mReference.keepSynced(true);
        usersRef = FirebaseDatabase.getInstance().getReference();
        usersRef.keepSynced(true);






        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtName = findViewById(R.id.txtName);
        txtContact = findViewById(R.id.txtMobile);
        postAd = findViewById(R.id.btnPostAd);
        icClear = findViewById(R.id.ic_clear);
        photo = findViewById(R.id.photo);
        addPhoto = findViewById(R.id.txtAddPhoto);

        icClear.setColorFilter(Color.BLACK);
        icClear.setVisibility(View.GONE);
        photo.setVisibility(View.GONE);


        camview = findViewById(R.id.imgCam);
        camview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(txtTitle.getText())) {
                    txtTitle.setError("This field is required");
                } else {
                        PostAd();

                        sendNotificationToUser(uid, "New Item Posted"+sTitle);
                    }
                }

        });


    }
    public static void sendNotificationToUser(String user, final String message) {
        DatabaseReference notifications=FirebaseDatabase.getInstance().getReference().child("notificationRequests");

        Map<String, String> notification = new HashMap<>();
        notification.put("username", user);
        notification.put("message", message);
        notifications.push().setValue(notification);
    }





    private void selectImage() {
        final CharSequence[] items = {"Camera", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Sell.this);
                if (items[item].equals("Camera")) {
                    userChoosenTask = "Camera";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from gallery")) {
                    userChoosenTask = "Choose from gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switch (userChoosenTask) {
                        case "Camera":
                            cameraIntent();
                            break;
                        case "Choose from gallery":
                            galleryIntent();
                            break;
                        default:

                            break;
                    }
                    break;
                }
        }
    }

    private int getItemId() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    itemId = (int) dataSnapshot.child("id").getValue();
                    Toast.makeText(Sell.this, itemId, Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    Log.d("getItemid", "no item yet");
                    itemId = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return itemId;
    }


    private void PostAd() {
        pd.setMessage("Posting Item");
        pd.show();
        pd.setCancelable(false);


        if (resultUri != null) {

            filepath = mStorage.child("Items_Images").child(resultUri.getLastPathSegment());
        }
        Long tsLong = 1 - System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        final DatabaseReference newItem = FirebaseDatabase.getInstance().getReference().child("Items").child(ts);

        sTitle = txtTitle.getText().toString().trim();
        sPrice = txtPrice.getText().toString().trim();
        sDescription = txtDescription.getText().toString().trim();
        sName = txtName.getText().toString().trim();
        sContact = txtContact.getText().toString().trim();

        newItem.child("id").setValue(getItemId() + 1);
        newItem.child("title").setValue(sTitle).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                alert();

            }
        });
        newItem.child("price").setValue(sPrice);
        newItem.child("description").setValue(sDescription);
        newItem.child("category").setValue(sCategory);
        newItem.child("contact").setValue(sContact);
        newItem.child("name").setValue(sName);
        newItem.child("uid").setValue(uid);
        newItem.child("views").setValue(0);
      //  sendNotification("New Item Posted for sale",sTitle);
        if (filepath != null) {

            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    newItem.child("image").setValue(downloadUrl.toString());
                    pd.dismiss();
                    alert();

                }
            });
        }


    }

    private void alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Sell.this);
        alert.setTitle("Ad Posted Succesfully!");
        alert.setMessage("Congratulations! \n Your Ad will be live on Kilimo Market in a while." +
                "\n Do you want to post another item?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                txtTitle.setText(null);
                txtPrice.setText(null);

                txtDescription.setText(null);

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(Sell.this, MarketActivity.class));
            }
        });
        alert.show();
    }
    /*private void sendNotification(final String paramString1 ,final String paramString2) {

        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJSON = new JSONObject();
                    dataJSON.put("body", paramString2);
                    dataJSON.put("title", paramString1);
                    json.put("notification", dataJSON);
                    json.put("to", "/topics/posts");
                    RequestBody body = RequestBody.create(Sell.JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=AIzaSyAOTJBHFIIRbpdeyApCZ5SybYehwEIgxMI")
                            .url("https://fcm.googleapis.com/fcm/send").post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                } catch (Exception paramAnonymousVarArgs) {

                }
                return null;
            }
        }.execute();
    }*/

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
           // startActivity(new Intent(Sell.this, MainActivity.class));
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if (requestCode == GALLERY_REQUEST) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                resultUri = result.getUri();

                photo.setImageURI(resultUri);

            }

        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(destination);
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);

        setImages();
    }

    private void onSelectFromGalleryResult(Intent data) {

      /*  Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }*/


        imageUri = data.getData();

        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);

        setImages();


    }


    private void setImages() {
        icClear.setVisibility(View.VISIBLE);
        camview.setVisibility(View.GONE);
        addPhoto.setVisibility(View.GONE);
        photo.setVisibility(View.VISIBLE);
        icClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icClear.setVisibility(View.GONE);
                camview.setVisibility(View.VISIBLE);
                addPhoto.setVisibility(View.VISIBLE);
                photo.setVisibility(View.GONE);
            }
        });
    }
}