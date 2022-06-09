package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter.FoodAdapter;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Food;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Store;

public class InfoStoreActivity extends AppCompatActivity {

    ListView listViewFood;
    TextView nameStore, addressStore;
    ImageView imageView;
    ArrayList<Food> listFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_info_store);
        listViewFood = (ListView) findViewById (R.id.lwFoodInStore);

        nameStore = (TextView) findViewById (R.id.storeNameInInfo);
        addressStore = (TextView) findViewById (R.id.storeAddressInInfo);

        nameStore.setText (getIntent ().getStringExtra ("name_store"));
        addressStore.setText (getIntent ().getStringExtra ("address_store"));

        imageView = (ImageView) findViewById (R.id.storeImgInInfo);
        DBHelper DB = new DBHelper (this);
        listFood = new ArrayList<Food> ();
        Cursor foods = DB.getFoodData ("Select * from food where store ='"+nameStore.getText ().toString ()+"'");
        while (foods.moveToNext ()){
            listFood.add (new Food (
                    foods.getString (1),
                    foods.getString (2),
                    foods.getDouble (3),
                    foods.getString (4),
                    foods.getBlob (5)));
        }
        FoodAdapter foodAdapter = new FoodAdapter (getApplicationContext (), R.layout.viewholder_food,listFood);
        listViewFood.setAdapter (foodAdapter);
        byte[] imgStore = getIntent ().getByteArrayExtra ("img_store");
        Bitmap bitmap = BitmapFactory.decodeByteArray (imgStore,0,imgStore.length);
        imageView.setImageBitmap (bitmap);
    }
}