package com.example.realestatemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RealtyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realty);

        // Initialisation des TextViews
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);


        // Initialisation des ImageViews
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);


        // Initialisation du bouton et mise en place d'un OnClickListener
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        textView1.setText("Nouveau texte pour TextView1");
        //imageView1.setImageResource(R.drawable.autre_image);

    }
}
