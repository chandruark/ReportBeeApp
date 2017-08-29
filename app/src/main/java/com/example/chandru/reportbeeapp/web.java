package com.example.chandru.reportbeeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

public class web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityweb);
        WebView webView=(WebView)findViewById(R.id.webView2);
        webView.loadUrl("https://www.reportbee.com");
        Button agree=(Button)findViewById(R.id.button3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        agree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(web.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
