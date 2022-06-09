package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, retype_password;
    private Button btnLogin, btnRegister;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);

        username = (EditText) findViewById (R.id.registerUsername);
        password = (EditText) findViewById (R.id.registerPassword);
        retype_password = (EditText) findViewById (R.id.registerRetypePassword);

        btnLogin = (Button) findViewById (R.id.btnGoLogin);
        btnRegister = (Button) findViewById (R.id.btnRegister);

        DB = new DBHelper (this);

        btnRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String user = username.getText ().toString ();
                String pass = password.getText ().toString ();
                String rtpass = retype_password.getText ().toString ();

                if(user.equals ("")||pass.equals ("")||retype_password.equals (""))
                    Toast.makeText (RegisterActivity.this,"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show ();
                else if(!pass.equals (rtpass)){
                    Toast.makeText (RegisterActivity.this,"Nhập lại mật khẩu không khớp",Toast.LENGTH_LONG).show ();
                }else if(DB.checkUserName (user)){
                    Toast.makeText (RegisterActivity.this,"Tên đăng nhập đã được sử dụng",Toast.LENGTH_LONG).show ();
                }else if(DB.insertData (user,pass)){
                    Toast.makeText (RegisterActivity.this,"Đã tạo tài khoản.",Toast.LENGTH_LONG).show ();
                }
            }
        });

        btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),LoginActivity.class);
                startActivity (intent);
            }
        });
    }
}
