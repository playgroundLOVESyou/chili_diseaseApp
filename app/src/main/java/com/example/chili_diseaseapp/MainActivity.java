package com.example.chili_diseaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText email,password,confirm_pass;

    Button register,signin;

    DBhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        email = (EditText) findViewById(R.id.edittextemail);
        password = (EditText) findViewById(R.id.edittextpassword);
        confirm_pass=(EditText)findViewById(R.id.edittextconfirmpassword);
        signin = (Button) findViewById(R.id.btnsignIn);
        register = (Button)findViewById(R.id.btnREGISTER);


        dbhelper = new DBhelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Confirm_pass=confirm_pass.getText().toString();

                if( Email.equals("") || Password.equals("") || Confirm_pass.equals("")) {
                    Toast.makeText(MainActivity.this, "All fields are Required", Toast.LENGTH_SHORT).show();
                }else{
                    if(Password.equals(Confirm_pass)){
                        Boolean emailcheckresult = dbhelper.checkEmail(Email);

                        if(emailcheckresult == false){
                            Boolean registrationResult = dbhelper.insertData(Email,Password);

                            if (registrationResult == true){
                                Toast.makeText(MainActivity.this, "Registration Successfully ! ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Email already exist. \n Please Sign_n", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });


    }
}