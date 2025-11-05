package com.example.lab5profilemanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button buttonGoogleMaps;
    EditText editTeamName, editTeamPostalAddress;
    TextView textHeading, textTeamName, textTeamPostalAddress;
    ImageView imageTeamLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonGoogleMaps = findViewById(R.id.buttonGoogleMaps);
        editTeamName = findViewById(R.id.editTeamName);
        editTeamPostalAddress = findViewById(R.id.editTeamPostalAddress);
        textTeamName = findViewById(R.id.textTeamName);
        textTeamPostalAddress = findViewById(R.id.textTeamPostalAddress);
        textHeading = findViewById(R.id.textHeading);
        imageTeamLogo = findViewById(R.id.imageTeamLogo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}