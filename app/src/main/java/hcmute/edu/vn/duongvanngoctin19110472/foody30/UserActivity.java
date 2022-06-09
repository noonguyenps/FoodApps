package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserActivity extends AppCompatActivity {

    private EditText idCustomer, nameCustomer, emailCustomer, addressCustomer, birthCustomer, phoneCustomer;
    private Button btnUpImg, btnSave;
    ImageView imageView;
    private final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user);

        idCustomer = (EditText) findViewById (R.id.etIDCustomer);
        nameCustomer = (EditText) findViewById (R.id.etNameCustomer);
        emailCustomer = (EditText) findViewById (R.id.etEmailAddressCustomer);
        addressCustomer = (EditText) findViewById (R.id.etAddressCustomer);
        birthCustomer = (EditText) findViewById (R.id.etBirthCustomer);
        phoneCustomer = (EditText) findViewById (R.id.etPhoneCustomer);

        btnUpImg = (Button) findViewById (R.id.btnUpImgCustomer);
        btnSave = (Button) findViewById (R.id.btnSaveCustomer);

        imageView = (ImageView) findViewById (R.id.imgCustomer);
        String id = String.valueOf (MainActivity.statusLogin);
        idCustomer.setText (id);

        DBHelper DB = new DBHelper (this);
        DB.executeQuery ("create table if not exists infousers(" +
                "id INTEGER primary key, " +
                "name VARCHAR, " +
                "email VARCHAR, " +
                "address VARCHAR, " +
                "birth VARCHAR, " +
                "phone VARCHAR, " +
                "img BLOB)");


        btnUpImg.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions (
                        UserActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
        btnSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                DB.insertUser (
                        idCustomer.getText ().toString (),
                        nameCustomer.getText ().toString (),
                        emailCustomer.getText ().toString (),
                        addressCustomer.getText ().toString (),
                        birthCustomer.getText ().toString (),
                        phoneCustomer.getText ().toString (),
                        ImgView_To_Byte (imageView));
                Toast.makeText (getApplicationContext (),"Đã lưu",Toast.LENGTH_LONG).show ();
            }
        });
        bottomNavigation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult (intent,
                        REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText (this, "Không tìm thấy đường đẫn",Toast.LENGTH_LONG).show ();
            }
            return;
        }
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && requestCode == RESULT_OK && data != null){
            Uri uri = data.getData ();
            try {
                InputStream inputStream = getContentResolver ().openInputStream (uri);
                Bitmap bitmap = BitmapFactory.decodeStream (inputStream);
                imageView.setImageBitmap (bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace ();
            }
        }
        super.onActivityResult (requestCode, resultCode, data);
    }

    public byte[] ImgView_To_Byte(ImageView h){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) h.getDrawable ();
        Bitmap bitmap = bitmapDrawable.getBitmap ();
        ByteArrayOutputStream stream = new ByteArrayOutputStream ();
        bitmap.compress (Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray ();
        return byteArray;
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout settingBtn = findViewById (R.id.settingbtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (UserActivity.this, CartListActivity.class));
            }
        });
        settingBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(MainActivity.statusLogin != 1){
                    Toast.makeText (UserActivity.this,"Không có gì để xem", Toast.LENGTH_SHORT).show ();
                }
                else{
                    Intent intent = new Intent (UserActivity.this, AddStoreActivity.class);
                    startActivity (intent);
                }
            }
        });
        homeBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (UserActivity.this,MainActivity.class);
                startActivity (intent);
            }
        });
    }
}