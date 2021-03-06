package hcmute.edu.vn.duongvanngoctin19110472.foody30;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddStoreActivity extends AppCompatActivity {
    private EditText nameStore, addressStore;
    private Button btnSelectImg, btnSaveStore, btnGoBackHome;
    private final int REQUEST_CODE_GALLERY = 999;

    public static DBHelper DB;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_store);

        nameStore = (EditText) findViewById (R.id.etNameStore);
        addressStore = (EditText) findViewById (R.id.etAddressStore);

        btnSelectImg = (Button) findViewById (R.id.btnSelectImgStore);
        btnSaveStore = (Button) findViewById (R.id.btnSaveStore);
        btnGoBackHome = (Button) findViewById (R.id.btnBackToHome);

        imageView= (ImageView) findViewById (R.id.imageViewFood);
        DB = new DBHelper (this);
        btnSelectImg.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions (
                        AddStoreActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
        btnGoBackHome.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),MainActivity.class);
                startActivity (intent);
            }
        });
        btnSaveStore.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                DB.insertStore (nameStore.getText ().toString (),addressStore.getText ().toString (),ImgView_To_Byte (imageView));
                Toast.makeText (getApplicationContext (),"???? l??u",Toast.LENGTH_LONG).show ();
            }
        });
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
                Toast.makeText (this, "Kh??ng t??m th???y ???????ng ?????n",Toast.LENGTH_LONG).show ();
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
}
