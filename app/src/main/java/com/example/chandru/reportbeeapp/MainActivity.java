package com.example.chandru.reportbeeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Button verify=(Button)findViewById(R.id.button);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText e1=(EditText)findViewById(R.id.editText);
                        EditText e2=(EditText)findViewById(R.id.editText3);

                        if( e1.getText().toString().equals("7812012012") && e2.getText().toString().equals("3535")   ) {

                            Intent intent = new Intent(MainActivity.this, Attend.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Invalid Details", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    public  void webcom(View view)
    {
        TextView verify=(TextView)findViewById(R.id.textView3);
        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, web.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Press OK to exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        builder.setCancelable(false);
        builder.show();
    }
}
