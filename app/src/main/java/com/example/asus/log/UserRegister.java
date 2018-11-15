package com.example.asus.log;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegister extends AppCompatActivity {

    EditText et_id;
    EditText et_psw;
    EditText et_psw2;
    TextView tv_suffix;
    ImageView psw_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Button btnsub=(Button)findViewById(R.id.btn_sub);
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();
            }
        });
        Button btncal=(Button)findViewById(R.id.btn_cancel);
        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        psw_image =(ImageView)findViewById(R.id.psw_image_reg) ;
        et_id=(EditText)findViewById(R.id.et_user_id_reg);
        et_psw=(EditText)findViewById(R.id.et_user_psw);
        et_psw.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    psw_image.setImageDrawable(getResources().getDrawable(R.drawable.pswclose));
                }
                else{
                    psw_image.setImageDrawable(getResources().getDrawable(R.drawable.pswopen));
                }
            }
            });
        et_psw2=(EditText)findViewById(R.id.et_user_psw2);
        et_psw2.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    psw_image.setImageDrawable(getResources().getDrawable(R.drawable.pswclose));
                }
                else{
                    psw_image.setImageDrawable(getResources().getDrawable(R.drawable.pswopen));
                }
            }
        });
        tv_suffix=(TextView) findViewById(R.id.tv_suffix);
    }

    private void setUser()
    {
        DBHelper database=new DBHelper(UserRegister.this,"LoginInfo",null,1);


        if(et_id.getText().toString().length()<=0||et_psw.getText().toString().length()<=0||et_psw2.getText().toString().length()<=0)
        {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_id.getText().toString().length()>0)
        {
            String sql="select * from user where userid=?";
            Cursor cursor=database.getWritableDatabase().rawQuery(sql, new String[]{et_id.getText().toString()});
            if(cursor.moveToFirst())
            {
                Toast.makeText(this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(!et_psw.getText().toString().equals(et_psw2.getText().toString()))
        {
            Toast.makeText(this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return;
        }
        if(database.AddUser(et_id.getText().toString(), et_psw.getText().toString()))
        {
            Toast.makeText(this, "用户注册成功", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "用户注册失败", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }




}
