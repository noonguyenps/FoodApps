package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public  static final String DBNAME = "MyDB.db";

    public DBHelper(@Nullable Context context) {
        super (context, "MyDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL ("create table users(userid INTEGER primary key AUTOINCREMENT, username TEXT UNIQUE, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put("username",username);
        contentValues.put("password",password);;
        long result = MyDB.insert ("users",null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public Boolean checkUserName(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        Cursor cursor = MyDB.rawQuery ("Select * from users where username = ?",new String[] {username});
        if(cursor.getCount ()>0){
            return true;
        }
        else return false;
    }

    public Boolean login(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        Cursor cursor = MyDB.rawQuery ("Select * from users where username = ? and password = ?",new String[] {username,password});
        if(cursor.getCount ()>0){
            return true;
        }
        else return false;
    }

    public void insertStore(String name, String address, byte[] img){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        String sql = "Insert into stores values (NULL, ?, ?, ?)";
        SQLiteStatement statement = MyDB.compileStatement (sql);
        statement.clearBindings ();
        statement.bindString (1,name);
        statement.bindString (2,address);
        statement.bindBlob (3,img);
        statement.executeInsert ();
    }

    public Cursor getStoreData(String sql){
        SQLiteDatabase MyDB = getReadableDatabase ();
        return MyDB.rawQuery (sql,null);
    }

    public void executeQuery(String sql){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        MyDB.execSQL (sql);
    }

    public void insertFood(String name, String description, double price, String store, byte[] img){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        String sql = "Insert into food values (NULL, ?, ?, ?,?,?)";
        SQLiteStatement statement = MyDB.compileStatement (sql);
        statement.clearBindings ();
        statement.bindString (1,name);
        statement.bindString (2,description);
        statement.bindDouble (3,price);
        statement.bindString (4,store);
        statement.bindBlob (5,img);
        statement.executeInsert ();
    }

    public void insertUser(String id,String name, String email, String address, String birth,String phone, byte[] img){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        String sql = "Insert into infousers values (?, ?, ?, ?,?,?,?)";
        SQLiteStatement statement = MyDB.compileStatement (sql);
        statement.clearBindings ();
        statement.bindString (1,id);
        statement.bindString (2,name);
        statement.bindString (3,email);
        statement.bindString (4,address);
        statement.bindString (5,birth);
        statement.bindString (6,phone);
        statement.bindBlob (7,img);
        statement.executeInsert ();
    }

    public Cursor getFoodData(String sql){
        SQLiteDatabase MyDB = getReadableDatabase ();
        return MyDB.rawQuery (sql,null);
    }

    public Cursor getCartData(String sql){
        SQLiteDatabase MyDB = getReadableDatabase ();
        return MyDB.rawQuery (sql,null);
    }

    public Cursor getUserData(String sql){
        SQLiteDatabase MyDB = getReadableDatabase ();
        return MyDB.rawQuery (sql,null);
    }

    public int getIdUser(String username){
        SQLiteDatabase MyDB = getReadableDatabase ();
        Cursor cur= MyDB.rawQuery("select * from users where username = ?",new String[] {username});
        if (cur.moveToFirst() == true)
        {
            return cur.getInt(1);
        }
        else
            return -1;
    }
    public int getIdFood(String foodName, String storeName){
        SQLiteDatabase MyDB = getReadableDatabase ();
        Cursor cur= MyDB.rawQuery("select * from food where name = ? and store = ?",new String[] {foodName,storeName});
        if (cur.moveToNext () == true)
        {
            return cur.getInt(0);
        }
        else
            return 0;
    }

    public void insertCart(int idUser, int idFood, int amount){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        String sql = "Insert into cart values (null,?,?,?)";
        SQLiteStatement statement = MyDB.compileStatement (sql);
        statement.clearBindings ();
        statement.bindLong (1,idUser);
        statement.bindLong (2,idFood);
        statement.bindLong (3,amount);
        statement.executeInsert ();
    }

    public void insertBill(int idUser, int idFood, int amount, double total){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        String sql = "Insert into bills values (NULL, ?, ?, ?, ?)";
        SQLiteStatement statement = MyDB.compileStatement (sql);
        statement.clearBindings ();
        statement.bindLong (1,idUser);
        statement.bindLong (2,idFood);
        statement.bindLong (3,amount);
        statement.bindDouble (4,total);
        statement.executeInsert ();
    }

    public void deleteCart(int iduser){
        SQLiteDatabase MyDB = this.getWritableDatabase ();
        MyDB.execSQL ("Delete from cart where iduser = "+ iduser);
    }


}
