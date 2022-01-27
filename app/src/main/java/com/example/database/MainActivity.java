package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2, editText3;
    Button button, button1, button2, button3;
    database g;
    RadioButton radioButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);


        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);

         g = new database(this);


         /// this function is used to insert the data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);


                String name = editText1.getText().toString();
                String email = editText2.getText().toString();
                String password = editText3.getText().toString();
                if (name.equals("") || email.equals("") || password.equals("") || selectedId==-1){
                    Toast.makeText(MainActivity.this, "Please Enter All the fields", Toast.LENGTH_SHORT).show();

                } else {
                    boolean aa = g.insert_data(name, email, password);
                    if (aa==true) {
                        Toast.makeText(MainActivity.this, "Data has been inserted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Not successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        });


        //// This function is used to delete a particular user data by email
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText2.getText().toString();
                Boolean i = g.delete_data(email);
                if (i == true) {
                    Toast.makeText(MainActivity.this, "Data is Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data is not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //// This function is used to update data by email
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editText1.getText().toString();
                String email = editText2.getText().toString();
                String password = editText3.getText().toString();
                Boolean i = g.update_data(name, email, password);
                if (i == true) {
                    Toast.makeText(MainActivity.this, "Data is updated SuccessFully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data is not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //// this function is used to show the data in alert box
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor t=g.getinfo();
                if (t.getCount()==0){
                    Toast.makeText(MainActivity.this, "Data is not found", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer=new StringBuffer();
                while(t.moveToNext()){
                    buffer.append("Name : " + t.getString(1) + "\n");
                    buffer.append("Email : " + t.getString(2) + "\n");
                    buffer.append("Password : " + t.getString(3) + "\n\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Insert user data");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}