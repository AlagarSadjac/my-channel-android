package com.nature.mychannel;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// Firebase இம்போர்ட்ஸ்
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private TextView myChannelName;
    private DatabaseReference mDatabase;
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //-------------

        // ஸ்டேட்டஸ் பாரை மறைத்து முழுத் திரையாக்க
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //----------------------------
        setContentView(R.layout.activity_main);

        myChannelName = findViewById(R.id.ch);
        wv = findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        //------
        wv.setWebChromeClient(new WebChromeClient());
        //---------

        wv.setWebViewClient(new WebViewClient());


        wv.loadUrl("https://youtube.com/@alagarsadjactamil3503");


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSy...") //  api_key in my google-services.json
                .setApplicationId("1:...") // mobilesdk_app_id in my google-services.json
                .setDatabaseUrl("https://mychannel-6f958-default-rtdb.asia-southeast1.firebasedatabase.app")
                .setProjectId("mychannel-6f958")
                .build();


       FirebaseApp.initializeApp(this, options);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mychannel-6f958-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String channel = dataSnapshot.child("MYChannel").getValue(String.class);
                String link = dataSnapshot.child("YouTube_Link").getValue(String.class);

                if (channel != null) {
                    myChannelName.setText(channel);
                }

                Log.d("FirebaseResult", "Channel Name: " + channel);
                Log.d("FirebaseResult", "YouTube Link: " + link);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FirebaseResult", "Failed to read value.", error.toException());
            }
        });


    }

public void onBackPressed()
{
    if (wv !=null && wv.canGoBack()) {
        wv.goBack();
    } else {
        super.onBackPressed();
    }
}


}



