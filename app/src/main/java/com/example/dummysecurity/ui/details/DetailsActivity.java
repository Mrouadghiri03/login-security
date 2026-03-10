package com.example.dummysecurity.ui.details;

import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dummysecurity.R;

public class DetailsActivity extends AppCompatActivity {

    TextView tvRawToken;
    TextView tvDecodedPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialisation des vues
        tvRawToken = findViewById(R.id.tvRawToken);
        tvDecodedPayload = findViewById(R.id.tvDecodedPayload);

        findViewById(R.id.btnBack).setOnClickListener(v -> onBackClicked());

        // 1. Récupérer le token passé par LoginActivity
        String token = getIntent().getStringExtra("ACCESS_TOKEN");

        if (token != null) {
            tvRawToken.setText(token);

            // 2. Décoder et afficher le contenu
            try {
                String decodedInfo = decodeJWT(token);
                tvDecodedPayload.setText(decodedInfo);
            } catch (Exception e) {
                tvDecodedPayload.setText("Erreur lors du décodage : " + e.getMessage());
            }
        }
    }

    /**
     * Un JWT est composé de : HEADER.PAYLOAD.SIGNATURE
     * On décode la partie centrale (index 1) qui est en Base64.
     */
    private String decodeJWT(String jwt) throws Exception {
        String[] parts = jwt.split("\\.");
        if (parts.length < 2) return "Format de token invalide";

        String payload = parts[1];
        byte[] data = Base64.decode(payload, Base64.DEFAULT);
        return new String(data, "UTF-8");
    }

    public void onBackClicked() {
        finish(); // Retourne à l'écran précédent
    }
}