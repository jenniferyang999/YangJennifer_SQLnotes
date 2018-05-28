package com.example.jenniferyang.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editNumber;
    EditText editAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editText_Name);
        editNumber = (EditText) findViewById(R.id.editText_Number);
        editAddress = (EditText) findViewById(R.id.editText_Address);

        myDb = new DatabaseHelper(this);
        Log.d("MYCONTACTAPP", "MainActivity: instantiated myDb");

    }

    public void addData(View view) {
        Log.d("MYCONTACTAPP", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editNumber.getText().toString(), editAddress.getText().toString());
        if (isInserted == true)
        {
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Failure - contact not inserted", Toast.LENGTH_LONG).show();

        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        Log.d("MYCONTACTAPP", "MainActivity: viewData: received cursor");

        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
        //append res column 0, 1, 2, 3 to buffer --> see Stringbuffer and cursor apis
        //Delimit reach of the "appends" with line feed "\n"
        buffer.append("Table Name: " + res.getString(0));
        buffer.append("\nName: " + res.getString(1));
        buffer.append("\nPhone Number: " + res.getString(2));
        buffer.append("\nAddress: " + res.getString(3));


        }

        showMessage("Data", buffer.toString());


    }

    private void showMessage(String title, String message) {
        Log.d("MYCONTACTAPP", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
