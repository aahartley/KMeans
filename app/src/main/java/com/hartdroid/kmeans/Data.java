
package com.hartdroid.kmeans;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class Data extends AppCompatActivity {
    TextView tv9=null;
    List<String> lines = new ArrayList<>();
    Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = super.findViewById(R.id.button);
        tv9 = super.findViewById(R.id.tv9);
        tv9.setMovementMethod(new ScrollingMovementMethod());


        lines= SecondActivity.allLines;
        for(String s: lines) {
            System.out.println(s);
            tv9.append(s+"\n");
        }

        button.setOnClickListener(v -> {
            goBack();
        });
    }
    protected void goBack(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}
