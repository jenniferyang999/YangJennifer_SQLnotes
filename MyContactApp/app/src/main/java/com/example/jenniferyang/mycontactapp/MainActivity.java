package com.example.jenniferyang.mycontactapp;

import android.content.Intent;
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
    EditText editSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editText_Name);
        editNumber = (EditText) findViewById(R.id.editText_Number);
        editAddress = (EditText) findViewById(R.id.editText_Address);
        editSearch = (EditText) findViewById(R.id.editText_Search);

        myDb = new DatabaseHelper(this);
        Log.d("MYCONTACTAPP", "MainActivity: instantiated myDb");

    }

    public void addData(View view) {
        Log.d("MYCONTACTAPP", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editNumber.getText().toString(), editAddress.getText().toString());
        if (isInserted == true) {
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
        buffer.append("\n");
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

    public static final String EXTRA_MESSAGE = "com.example.jenniferyang.mycontactapp_p2.MESSAGE";
    public void searchRecord (View view) {
        Log.d("MYCONTACTAPP", "MainActivity: launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        boolean exist = false;
        Cursor res_v2 = myDb.getAllData();
        StringBuffer buffer_v2 = new StringBuffer();
        Log.d("MYCONTACTAPP", "MainActivity: searchRecord: received cursor");
        while (res_v2.moveToNext()) {
            buffer_v2.delete(0, buffer_v2.capacity());
            if (res_v2.getString(1).equals(editSearch.getText().toString())) {
                exist = true;
                buffer_v2.append("Table Name: " + res_v2.getString(0));
                buffer_v2.append("\nName: " + res_v2.getString(1));
                buffer_v2.append("\nPhone Number: " + res_v2.getString(2));
                buffer_v2.append("\nAddress: " + res_v2.getString(3));
                if (exist == true) {
                    intent.putExtra(EXTRA_MESSAGE, buffer_v2.toString());
                    Log.d("MYCONTACTAPP", "MainActivity: searchRecord: search was successful!");
                }
            }
            else {
                intent.putExtra(EXTRA_MESSAGE, "Error: contact doesn't exist");
            }
        }


        startActivity(intent);
    }
}