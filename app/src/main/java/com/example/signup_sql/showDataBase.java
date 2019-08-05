package com.example.signup_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class showDataBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_base);

        TextView tvShowData=findViewById(R.id.tvShowData);

        database db=new database(this);
        List<User> users=db.getAllUser();

        for(int i=0;i<users.size();i++){
            String user=users.get(i).getId() +" " + users.get(i).getName()+" "+ users.get(i).getEmail()+ " "+users.get(i).getPassword();
            tvShowData.append(user+ "\n");
        }
    }
}
