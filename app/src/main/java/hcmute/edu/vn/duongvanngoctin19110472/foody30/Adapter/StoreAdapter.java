package hcmute.edu.vn.duongvanngoctin19110472.foody30.Adapter;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity.Store;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.InfoStoreActivity;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.OderActivity;
import hcmute.edu.vn.duongvanngoctin19110472.foody30.R;

public class StoreAdapter extends ArrayAdapter<Store>{
    public StoreAdapter(@NonNull Context context, int resource) {
        super (context, resource);
    }

    public StoreAdapter(@NonNull Context context, int resource, @NonNull List<Store> objects) {
        super (context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if(v==null){
            LayoutInflater vi;
            vi = LayoutInflater.from (getContext ());
            v = vi.inflate (R.layout.viewholder_store,null);

            ViewHolder viewHolder = new ViewHolder ();
            viewHolder.btnShowStore = (LinearLayout) v.findViewById (R.id.mainLayout);
            viewHolder.storeName = (TextView) v.findViewById (R.id.rowStoreName);
            viewHolder.storeAddress = (TextView) v.findViewById (R.id.rowStoreAddress);
            viewHolder.imageView = (ImageView) v.findViewById (R.id.storePic);

            viewHolder.btnShowStore.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    String storeName = viewHolder.storeName.getText ().toString ().trim ();
                    String storeAddress =viewHolder.storeAddress.getText ().toString ().trim ();
                    byte[] imgFood = ImgView_To_Byte (viewHolder.imageView);

                    Intent intent = new Intent (view.getContext (), InfoStoreActivity.class);
                    intent.putExtra ("name_store",storeName);
                    intent.putExtra ("address_store",storeAddress);
                    intent.putExtra ("img_store",imgFood);
                    intent.addFlags (intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext ().startActivity (intent);
                }
            });

        }
        Store s = getItem (position);
        if(s!=null){
            TextView t1 = (TextView) v.findViewById (R.id.rowStoreName);
            t1.setText (s.storeName);

            TextView t2 = (TextView) v.findViewById (R.id.rowStoreAddress);
            t2.setText (s.address);

            ImageView imgv = (ImageView) v.findViewById (R.id.storePic);
            Bitmap bitmap = BitmapFactory.decodeByteArray (s.imgStore,0,s.imgStore.length);
            imgv.setImageBitmap (bitmap);
        }
        return v;
    }
    public class ViewHolder{
        LinearLayout btnShowStore;
        ImageView imageView;
        TextView storeName, storeAddress;
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