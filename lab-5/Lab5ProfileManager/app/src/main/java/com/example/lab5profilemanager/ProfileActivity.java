package com.example.lab5profilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    // Declare views
    ImageView flag_ca, flag_eg, flag_fr, flag_jp, flag_kr, flag_sp, flag_tr, flag_uk, flag_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        flag_ca = findViewById(R.id.flag_ca);
        flag_eg = findViewById(R.id.flag_eg);
        flag_fr = findViewById(R.id.flag_fr);
        flag_jp = findViewById(R.id.flag_jp);
        flag_kr = findViewById(R.id.flag_kr);
        flag_sp = findViewById(R.id.flag_sp);
        flag_tr = findViewById(R.id.flag_tr);
        flag_uk = findViewById(R.id.flag_uk);
        flag_us = findViewById(R.id.flag_us);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void SetTeamIcon(View view) {
        Intent returnIntent = new Intent();
        ImageView selectedImage = (ImageView) view;
        returnIntent.putExtra("imageID", selectedImage.getId());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}