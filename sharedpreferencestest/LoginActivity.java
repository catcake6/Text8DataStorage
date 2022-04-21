package com.example.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;
    private CheckBox autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remenber_pass);
        login = (Button) findViewById(R.id.login);
        autologin = (CheckBox) findViewById(R.id.auto_login);
        boolean isRemember = pref.getBoolean("remember_password",false);
        boolean isAuto = pref.getBoolean("auto_login",false);
        if (isRemember){
            //将账号和密码都设置到文本框中
            String account =pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
            if (isAuto){//设置自动登录
                autologin.setChecked(true);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号是admin且密码是123456，就登录成功
                if (account.equals("admin")&&password.equals("123456")){
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {//检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                        if (autologin.isChecked()){
                            editor.putBoolean("auto_login",true);
                        }
                    }else {
                        editor.clear();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或者密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}