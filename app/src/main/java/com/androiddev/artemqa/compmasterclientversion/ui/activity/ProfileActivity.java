package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private final int PICK_IMAGE = 100;
    CardView cvSave;
    TextInputEditText etName, etAddress, etPhone, etEmail;
    CircularImageView ivClientPhoto;
    Realm realm;
    Client currentClient;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initForm();
        setFieldsData();
    }


    private void initForm() {
        realm = Realm.getDefaultInstance();
        etName = findViewById(R.id.et_name_profile_a);
        etPhone = findViewById(R.id.et_phobe_profile_a);
        etEmail = findViewById(R.id.et_email_profile_a);
        etAddress = findViewById(R.id.et_address_profile_a);
        cvSave = findViewById(R.id.cv_save_profile_a);
        cvSave.setOnClickListener(this);
        ivClientPhoto = findViewById(R.id.iv_client_photo_profile_a);
        ivClientPhoto.setOnClickListener(this);
    }

    private void setFieldsData() {
        currentClient = MainActivity.currentClient;

        if (currentClient.getName() != null) {
            etName.setText(currentClient.getName());
        }

        if (currentClient.getPhoneNumber() != null) {
            etPhone.setText(currentClient.getPhoneNumber());
        }

        if (currentClient.getAddress() != null) {
            etAddress.setText(currentClient.getAddress());
        }

        etEmail.setText(currentClient.getLogin());
        if (currentClient.getPhoto() != null) {
            byte[] byreArrayPhoto = currentClient.getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(byreArrayPhoto, 0, byreArrayPhoto.length);
            ivClientPhoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                    200, false));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_save_profile_a:
                if (validateForm()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentClient.setPhoneNumber(etPhone.getText().toString());
                            currentClient.setAddress(etAddress.getText().toString());
                            currentClient.setName(etName.getText().toString());
                            realm.copyToRealmOrUpdate(currentClient);
                        }
                    });
                    finish();
                }
                break;

            case R.id.iv_client_photo_profile_a:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Вы не выбрали фото", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] byteArray = stream.toByteArray();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        currentClient.setPhoto(byteArray);
                        realm.copyToRealmOrUpdate(currentClient);
                    }

                });

                ivClientPhoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap, ivClientPhoto.getWidth(),
                        ivClientPhoto.getHeight(), false));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateForm() {
        if (!(etPhone.getText().toString().equals("")
                && etAddress.getText().toString().equals("")
                && etName.getText().toString().equals(""))) {

            if ((etName.getText().toString().matches("^[\\w\\s]{2,35}$")
                    && (etAddress.getText().toString().matches("^[\\w\\s\\.\\,]{5,49}$"))
                    && (etPhone.getText().toString().matches("^\\+?[0-9]{5,16}$")))) {
                return true;

            } else {
                Toast.makeText(this, R.string.error_validation_new_order_a, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.error_fill_fields_new_order_a, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
