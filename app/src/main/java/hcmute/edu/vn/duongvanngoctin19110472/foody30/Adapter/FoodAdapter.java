package hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.AddStoreActivity;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Food;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.IntroActivity;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.OderActivity;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.R;

public class FoodAdapter extends ArrayAdapter<Food> {
    private Context context;

    public FoodAdapter(@NonNull Context context, int resource) {
        super (context, resource);
        this.context = context;
    }

    public FoodAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super (context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi;
            vi = LayoutInflater.from (getContext ());
            v = vi.inflate (R.layout.viewholder_food,null);
            ViewHolder viewHolder = new ViewHolder ();
            viewHolder.button = (Button) v.findViewById (R.id.btnAddToCart);
            viewHolder.foodName = (TextView) v.findViewById (R.id.rowFoodName);
            viewHolder.foodDescription = (TextView) v.findViewById (R.id.rowFoodDescription);
            viewHolder.foodPrice = (TextView) v.findViewById (R.id.rowPrice);
            viewHolder.foodStore = (TextView) v.findViewById (R.id.rowStore);
            viewHolder.imageView = (ImageView) v.findViewById (R.id.foodPic);
            viewHolder.button.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    String foodName = viewHolder.foodName.getText ().toString ().trim ();
                    String foodDescription =viewHolder.foodDescription.getText ().toString ().trim ();
                    String foodPrice =viewHolder.foodPrice.getText ().toString ().trim ();
                    String foodStore =viewHolder.foodStore.getText ().toString ().trim ();
                    byte[] imgFood = ImgView_To_Byte (viewHolder.imageView);

                    Intent intent = new Intent (view.getContext (), OderActivity.class);
                    intent.putExtra ("name_food",foodName);
                    intent.putExtra ("description_food",foodDescription);
                    intent.putExtra ("price_food",foodPrice);
                    intent.putExtra ("store_food",foodStore);
                    intent.putExtra ("img_food",imgFood);
                    intent.addFlags (intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext ().startActivity (intent);

                }
            });

        }
        Food f = getItem (position);
        if(f!=null){
            TextView t1 = (TextView) v.findViewById (R.id.rowFoodName);
            t1.setText (f.nameFood);

            TextView t2 = (TextView) v.findViewById (R.id.rowFoodDescription);
            t2.setText (f.descriptionFood);

            TextView t3 = (TextView) v.findViewById (R.id.rowStore);
            t3.setText (f.storeName);

            TextView t4 = (TextView) v.findViewById (R.id.rowPrice);
            t4.setText (f.priceFood.toString ());

            ImageView imgv = (ImageView) v.findViewById (R.id.foodPic);
            Bitmap bitmap = BitmapFactory.decodeByteArray (f.picFood,0,f.picFood.length);
            imgv.setImageBitmap (bitmap);
        }
        return v;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView foodName, foodDescription, foodStore, foodPrice;
        Button button;
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