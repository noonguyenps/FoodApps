package hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.DBHelper;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Cart;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Food;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Store;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.R;

public class CartAdapter extends ArrayAdapter<Cart> {

    public CartAdapter(@NonNull Context context, int resource) {
        super (context, resource);
    }

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<Cart> objects) {
        super (context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if(v==null){
            LayoutInflater vi;
            vi = LayoutInflater.from (getContext ());
            v = vi.inflate (R.layout.viewholder_cart,null);
        }
        Cart c = getItem (position);
        if(c != null){
            TextView t1 = (TextView) v.findViewById (R.id.foodNameInCart);
            t1.setText (c.foodName);

            TextView t2 = (TextView) v.findViewById (R.id.StoreInCart);
            t2.setText (c.storeFood);

            TextView t3 = (TextView) v.findViewById (R.id.PriceinCart);
            t3.setText (String.valueOf (c.price));

            TextView t4 = (TextView) v.findViewById (R.id.rowFoodAmountInCart);
            t4.setText ("Số lượng: " + String.valueOf (c.amount));

            TextView t5 = (TextView) v.findViewById (R.id.totalPriceInCart);
            Double total = Double.valueOf (c.amount) * c.price;
            t5.setText (String.valueOf (total));

            ImageView imgv = (ImageView) v.findViewById (R.id.foodPicInCart);
            Bitmap bitmap = BitmapFactory.decodeByteArray (c.imgFood,0,c.imgFood.length);
            imgv.setImageBitmap (bitmap);
        }
        return v;
    }
}