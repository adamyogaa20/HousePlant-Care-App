package com.bloverteam.plantscare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bloverteam.plantscare.R;
import com.bloverteam.plantscare.db.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText TxUsername, TxPassword, TxConPassword;
    Button BtnRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        TxUsername = findViewById(R.id.txUsernameReg);
        TxPassword = findViewById(R.id.txPasswordReg);
        TxConPassword = findViewById(R.id.txConPassword);
        BtnRegister = findViewById(R.id.btnRegister);

        TextView tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setText(fromHtml("Back to " +
                "</font><font color='#3b5998'>Login</font>"));

        tvRegister.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        BtnRegister.setOnClickListener(v -> {
            String username = TxUsername.getText().toString().trim();
            String password = TxPassword.getText().toString().trim();
            String conPassword = TxConPassword.getText().toString().trim();

            ContentValues values = new ContentValues();


            if (!password.equals(conPassword)){
                Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
            }else if (password.equals("") || username.equals("")){
                Toast.makeText(RegisterActivity.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
            }else {
                values.put(DBHelper.row_username, username);
                values.put(DBHelper.row_password, password);
                dbHelper.insertData(values);

                Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}