package com.example.signup_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private TextView tvAppName;
private EditText etName,etEmail,etPassword;
Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAppName=findViewById(R.id.tvAppName);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnSignup=findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=etName.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String pass=etPassword.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please provide details..",Toast.LENGTH_SHORT).show();
                }
                else{
                    database db=new database(getApplicationContext());

                    //checking if such account existdss
                    boolean ans=db.checkUser(email);
                    if(ans==true){
                        Toast.makeText(getApplicationContext(),"This Email ID is already registered",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        User user=new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(pass);

                        db.addUser(user);
                        Toast.makeText(getApplicationContext(),"New User Registered",Toast.LENGTH_SHORT).show();

                        //go to show the database
                        startActivity(new Intent(getApplicationContext(),showDataBase.class));

                    }



                }

            }
        });
    }
}
