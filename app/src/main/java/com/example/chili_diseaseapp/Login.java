package com.example.chili_diseaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
EditText email,password;
Button login, signUp;

DBhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edittextemail);
        password = findViewById(R.id.edittextpassword);
        login = findViewById(R.id.btnREGISTER);
        signUp = findViewById(R.id.signup);

        dbhelper = new DBhelper(this);

signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
});

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();


                if(Email.equals("") || Password.equals("")) {
                    Toast.makeText(Login.this, "All Fields are Required!", Toast.LENGTH_SHORT).show();

                }else{
                    Boolean checkEmailPass = dbhelper.checkEmailPassword(Email, Password);
                    if(checkEmailPass == true){
                        Intent intent = new Intent(getApplicationContext(),predict_then_save.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Sign Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}