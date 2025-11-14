package com.example.lab5profilemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

// Import UI components
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Declare views
    Button buttonGoogleMaps;
    EditText editTeamName, editTeamPostalAddress;
    TextView textHeading, textTeamName, textTeamPostalAddress;
    ImageView imageTeamLogo;

    // Map of flags to string names
    Map<Integer, String> drawableToName;

    // Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Finds the views in the XML layout with the IDs provided
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

        // Create map of drawables to names
        drawableToName = new HashMap<>();
        drawableToName.put(R.id.flag_ca, "flag_ca");
        drawableToName.put(R.id.flag_eg, "flag_eg");
        drawableToName.put(R.id.flag_fr, "flag_fr");
        drawableToName.put(R.id.flag_jp, "flag_jp");
        drawableToName.put(R.id.flag_kr, "flag_kr");
        drawableToName.put(R.id.flag_sp, "flag_sp");
        drawableToName.put(R.id.flag_tr, "flag_tr");
        drawableToName.put(R.id.flag_uk, "flag_uk");
        drawableToName.put(R.id.flag_us, "flag_us");
    }

    public void onOpenInGoogleMaps(View view) {
        String postalAddress = editTeamPostalAddress.getText().toString().trim();

        // Create Uri from strong
        // Use result to create intent
        Uri gmmIntentUri = Uri.parse("https://maps.google.co.in/maps?q=" + Uri.encode(postalAddress));

        // Create intent from gmmIntentUri
        // Set action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start the activity that can handle the Intent
        startActivity(mapIntent);
    }

    private ActivityResultLauncher<Intent> profileActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            ImageView avatarImage = (ImageView) findViewById(R.id.imageTeamLogo);

            String drawableName = drawableToName.get(data.getIntExtra("imageID", R.id.flag_ca));
            if (drawableName == null) drawableName = "flag_ca";

            int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            avatarImage.setImageResource(resID);

        }
    });

    public void OnSetAvatarButton(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileActivityResultLauncher.launch(intent);
    }


}