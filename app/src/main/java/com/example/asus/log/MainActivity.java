package com.example.asus.log;

        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.view.Menu;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

public class MainActivity extends Activity {

    EditText et_id;
    EditText et_psw;
    ImageView psw_image;
    DBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        psw_image =(ImageView)findViewById(R.id.psw_image_login) ;
        et_id =(EditText)findViewById(R.id.et_user_id);
        et_psw =(EditText)findViewById(R.id.et_user_psw);
        Button btnlogin=(Button)findViewById(R.id.btn_login);
        database = new DBHelper(MainActivity.this,"LoginInfo",null,1);//这段代码放到Activity类中才用this
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getUser();
            }
        });
        Button btnreg=(Button)findViewById(R.id.btn_reg);
        btnreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, UserRegister.class);
                startActivity(intent);
            }
        });

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
    }
    public void getUser()
    {
        String sql="select * from user where userid=?";
        Cursor cursor=database.getWritableDatabase().rawQuery(sql, new String[]{et_id.getText().toString()});
        if(cursor.moveToFirst())
        {

            if(et_psw.getText().toString().equals(cursor.getString(cursor.getColumnIndex("userpwd"))))
            {
                Toast.makeText(this, "登录成功",Toast.LENGTH_SHORT ).show();
            }
            else
            {
                Toast.makeText(this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
            }
        }
        database.close();
    }

}

