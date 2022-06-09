package hcmute.edu.vn.duongvanngoctin19110472.foody30;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin, btnRegister;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        username = (EditText) findViewById (R.id.etUsername);
        password = (EditText) findViewById (R.id.etPassword);

        DB = new DBHelper (this);

        btnLogin = (Button) findViewById (R.id.btnLogin);
        btnRegister = (Button) findViewById (R.id.btnGoRegister);

        btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String user = username.getText ().toString ();
                String pass = password.getText ().toString ();
                if(user.equals ("")||pass.equals ("")){
                    Toast.makeText (LoginActivity.this,"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show ();
                }else if(!DB.login (user,pass)){
                    Toast.makeText (LoginActivity.this,"Tài khoản hoặc mật khẩu không chính xác",Toast.LENGTH_LONG).show ();
                }else{
                    Toast.makeText (LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show ();
                    int id = DB.getIdUser (user);
                    MainActivity.statusLogin = id;
                    MainActivity.nameUser = user;
                    Intent intent = new Intent (getApplicationContext (), MainActivity.class);
                    startActivity (intent);
                }
            }
        });

        btnRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (), RegisterActivity.class);
                startActivity (intent);
            }
        });
    }
}
