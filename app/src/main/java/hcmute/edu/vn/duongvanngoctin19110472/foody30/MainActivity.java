package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter.FoodAdapter;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter.StoreAdapter;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Food;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Store;

public class MainActivity extends AppCompatActivity {

    ListView listViewStore, listViewFood;
    ArrayList<Store> listStore;
    ArrayList<Food> listFood;
    TextView textViewLogin,textViewHi;
    public static int statusLogin = -1;
    public static String nameUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        DBHelper DB = new DBHelper (this);
        textViewLogin = (TextView) findViewById (R.id.textView5);
        textViewHi = (TextView) findViewById (R.id.textView4);

        if(statusLogin == -1){
            textViewLogin.setText ("Đăng nhập");
        }
        else{
            textViewLogin.setText ("Đăng xuất");
            if(!nameUser.equals ("")){
                textViewHi.setText ("Hi "+ nameUser);
            }
        }

        //Danh sach cac cua hang
        Cursor stores = DB.getStoreData ("Select * from stores");
        listViewStore = (ListView) findViewById (R.id.listViewStore);
        listStore = new ArrayList<Store> ( );
        while (stores.moveToNext ()){
            listStore.add (new Store (
                    stores.getString (1),
                    stores.getString (2),
                    stores.getBlob (3)));
        }
        StoreAdapter adapter = new StoreAdapter (getApplicationContext (), R.layout.viewholder_store,listStore);
        listViewStore.setAdapter (adapter);

        //Danh sach cac mon an
        listViewFood = (ListView) findViewById (R.id.listViewFood);

        listFood = new ArrayList<Food> ();
        Cursor foods = DB.getFoodData ("Select * from food");
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

        textViewLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity (intent);
            }
        });
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout billBtn = findViewById(R.id.billBtn);
        LinearLayout settingBtn = findViewById (R.id.settingbtn);
        LinearLayout profileBtn = findViewById (R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this, CartListActivity.class));
            }
        });
        settingBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(statusLogin != 1){
                    Toast.makeText (MainActivity.this,"Không có gì để xem", Toast.LENGTH_SHORT).show ();
                }
                else{
                    Intent intent = new Intent (MainActivity.this, SettingActivity.class);
                    startActivity (intent);
                }
            }
        });
        profileBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(statusLogin == -1){
                    Toast.makeText (MainActivity.this,"Bạn cần đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent (MainActivity.this, UserActivity.class);
                    startActivity (intent);
                }
            }
        });
        billBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(statusLogin == -1){
                    Toast.makeText (MainActivity.this,"Bạn cần đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent (MainActivity.this, BillActivity.class);
                    startActivity (intent);
                }
            }
        });
    }

}