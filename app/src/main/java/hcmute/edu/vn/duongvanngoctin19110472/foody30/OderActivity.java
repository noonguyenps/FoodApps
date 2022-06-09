package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Food;

public class OderActivity extends AppCompatActivity {
    private int numberOrder = 1;
    private ImageView btnPlus, btnMinus, picFood;
    private TextView btnAddToCart, nameFood, priceFood, descriptionFood, numberOfFoodInOder;
    private Food food;
    public static DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_oder);

        DBHelper DB = new DBHelper (this);

        btnPlus = (ImageView) findViewById (R.id.plusBtnOder);
        btnMinus = (ImageView) findViewById (R.id.minusBtnOder);
        picFood = (ImageView) findViewById (R.id.picfoodOder);

        btnAddToCart = (TextView) findViewById (R.id.addOderFoodToCartBtn);
        nameFood = (TextView) findViewById (R.id.nameFoodOder);
        priceFood = (TextView) findViewById (R.id.priceFoodOder);
        descriptionFood =(TextView) findViewById (R.id.descriptionFoodOder);
        numberOfFoodInOder = (TextView) findViewById (R.id.numberFoodOrder);

        nameFood.setText (getIntent ().getStringExtra ("name_food"));
        descriptionFood.setText (getIntent ().getStringExtra ("description_food"));
        priceFood.setText (getIntent ().getStringExtra ("price_food") + " VND");
        byte[] imgFood = getIntent ().getByteArrayExtra ("img_food");
        Bitmap bitmap = BitmapFactory.decodeByteArray (imgFood,0,imgFood.length);
        picFood.setImageBitmap (bitmap);

        btnPlus.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOfFoodInOder.setText(String.valueOf(numberOrder));
            }
        });
        btnMinus.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(numberOrder == 1){
                    Toast.makeText (OderActivity.this,"Không thể xóa thêm",Toast.LENGTH_SHORT).show ();
                }
                else{
                    numberOrder = numberOrder - 1;
                    numberOfFoodInOder.setText(String.valueOf(numberOrder));
                }
            }
        });

        btnAddToCart.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(MainActivity.statusLogin == -1){
                    Toast.makeText (OderActivity.this,"Hãy đăng nhập để tiếp tục",Toast.LENGTH_SHORT).show();
                }
                else{
                    int idFood = DB.getIdFood (getIntent ().getStringExtra ("name_food"), getIntent ().getStringExtra ("store_food"));
                    DB.insertCart (MainActivity.statusLogin,idFood,numberOrder);
                    Toast.makeText (OderActivity.this,"Đã lưu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}