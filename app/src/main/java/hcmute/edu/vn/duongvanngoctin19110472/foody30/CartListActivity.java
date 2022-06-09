package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter.CartAdapter;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter.StoreAdapter;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Cart;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Store;

public class CartListActivity extends AppCompatActivity {

    ArrayList<Cart> listCart;
    ListView listViewCart;
    private TextView totalTxt, btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_cart_list);
        Double totalPrice = 0.0;

        //Danh sach gio hang
        DBHelper DB = new DBHelper (this);
        Cursor cart = DB.getCartData ("Select * from cart where iduser = " + MainActivity.statusLogin);
        listViewCart = (ListView) findViewById (R.id.listViewFoodOfCart);
        listCart = new ArrayList<Cart> ( );
        while (cart.moveToNext ()){
            Cursor foods = DB.getFoodData ("Select * from food where id ="+ cart.getInt (2));
            while (foods.moveToNext ()){
                listCart.add (new Cart (foods.getString (1),
                        foods.getString (4),
                        cart.getInt (3),
                        foods.getDouble (3),
                        foods.getBlob (5)));
                totalPrice += foods.getDouble (3)* Double.valueOf (cart.getInt (3));
            }
        }
        totalTxt = (TextView) findViewById (R.id.totalTxt);
        totalTxt.setText (String.valueOf (totalPrice) + " VND");
        CartAdapter adapter = new CartAdapter (getApplicationContext (),R.layout.viewholder_cart,listCart);
        listViewCart.setAdapter (adapter);
        bottomNavigation();
        btnCheckOut = (TextView) findViewById (R.id.btnCheckOut);
        btnCheckOut.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                for(Cart cart : listCart){
                    int idFood = DB.getIdFood (cart.foodName,cart.storeFood);
                    double total = Double.valueOf (cart.amount)*cart.price;
                    DB.insertBill (MainActivity.statusLogin,idFood,cart.amount,total);
                    DB.deleteCart (MainActivity.statusLogin);
                }
                Toast.makeText (CartListActivity.this, "Đã Đặt hàng", Toast.LENGTH_SHORT).show ();
            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById (R.id.cartBtn);
        LinearLayout homeBtn = findViewById (R.id.homeBtn);
        LinearLayout settingBtn = findViewById (R.id.settingbtn);
        LinearLayout profileBtn = findViewById (R.id.profileBtn);

        settingBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (MainActivity.statusLogin != 1) {
                    Toast.makeText (CartListActivity.this, "Không có gì để xem", Toast.LENGTH_SHORT).show ();
                } else {
                    Intent intent = new Intent (CartListActivity.this, AddStoreActivity.class);
                    startActivity (intent);
                }
            }
        });
        homeBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (CartListActivity.this, MainActivity.class);
                startActivity (intent);
            }
        });
        profileBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(MainActivity.statusLogin == -1){
                    Toast.makeText (CartListActivity.this,"Bạn cần đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent (CartListActivity.this, UserActivity.class);
                    startActivity (intent);
                }
            }
        });
    }
}