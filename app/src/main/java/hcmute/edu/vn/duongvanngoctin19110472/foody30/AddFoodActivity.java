package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class AddFoodActivity extends AppCompatActivity {
    Spinner storeList;
    Button btnSelectImg, btnSaveFood, btnBackToHome;
    EditText etNameFood, etDescription, etPrice;
    ImageView imageView;
    private final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> listStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_food);

        storeList = (Spinner) findViewById (R.id.spinner);

        listStore = new ArrayList<String> ( );
        DBHelper DB = new DBHelper (this);
        Cursor stores = DB.getStoreData ("Select * from stores");
        while (stores.moveToNext ()){
            listStore.add (stores.getString (1));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item,listStore);
        storeList.setAdapter (arrayAdapter);

        btnSelectImg = (Button) findViewById (R.id.btnSelectImgFood);
        btnSaveFood = (Button) findViewById (R.id.btnSaveFood);
        btnBackToHome = (Button) findViewById (R.id.btnBackFromFoodToHome);

        etNameFood = (EditText) findViewById (R.id.etNameFood);
        etDescription = (EditText) findViewById (R.id.etDescriptionFood);
        etPrice = (EditText) findViewById (R.id.etPriceFood);

        imageView = (ImageView)findViewById (R.id.imageViewFood);

        btnSelectImg.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions (
                        AddFoodActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
        btnSaveFood.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                DB.insertFood (etNameFood.getText ().toString (),
                        etDescription.getText ().toString (),
                        Double.parseDouble (etPrice.getText ().toString ()),
                        storeList.getSelectedItem ().toString (),
                        ImgView_To_Byte (imageView));
                Toast.makeText (getApplicationContext (),"Đã lưu",Toast.LENGTH_LONG).show ();
            }
        });
        btnBackToHome.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),MainActivity.class);
                startActivity (intent);
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
}
