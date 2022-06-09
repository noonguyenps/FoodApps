package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.duongvanngoctin19110472.foody30.MainActivity;

public class IntroActivity extends AppCompatActivity {
    private final int WAIT_TIME = 4000;
    private Handler uiHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHandler = new Handler();
        setContentView(R.layout.activity_intro);
        DBHelper DB = new DBHelper (this);
        //Tao cac Table
        DB.executeQuery ("create table if not exists stores(id INTEGER primary key AUTOINCREMENT, name VARCHAR,address VARCHAR, img BLOB)");
        DB.executeQuery ("create table if not exists food(id INTEGER primary key AUTOINCREMENT, name VARCHAR,description VARCHAR,price DOUBLE,store VARCHAR, img BLOB)");
        DB.executeQuery ("create table if not exists cart(id INTEGER primary key AUTOINCREMENT, iduser INTEGER, idfood INTEGER, amount INTEGER)");
        DB.executeQuery ("create table if not exists bills(id INTEGER primary key AUTOINCREMENT, iduser INTEGER, idfood INTEGER, amount INTEGER, total DOUBLE)");
        Runnable onUi = new Runnable() {
            @Override
            public void run() {
                // Lệnh này sẽ chạy trên chủ đề giao diện người dùng chính
                Intent mainIntent = new Intent(IntroActivity.this, MainActivity.class);
                IntroActivity.this.startActivity(mainIntent);
                IntroActivity.this.finish();
            }
        };

        Runnable background = new Runnable() {
            @Override
            public void run() {
                // Dòng lệnh delay nền
                try {
                    Thread.sleep( WAIT_TIME );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Lệnh này sẽ chạy trên một nền
                uiHandler.post( onUi );
            }
        };

        new Thread( background ).start();

    }
}
