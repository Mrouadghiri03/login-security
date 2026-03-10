package com.example.dummysecurity.ui.details;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dummysecurity.R;
import com.example.dummysecurity.ui.login.LoginActivity;
import com.example.dummysecurity.ui.login.LoginContract;
import com.example.dummysecurity.ui.login.LoginPresenter;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    TextView tvRawToken;
    TextView tvDecodedPayload;
    private DetailsContract.Presenter presenter;
    ProgressBar loader;
    Button btnLogout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialisation des vues
        tvRawToken = findViewById(R.id.tvRawToken);
        tvDecodedPayload = findViewById(R.id.tvDecodedPayload);
        loader = findViewById(R.id.loader);
        btnLogout=findViewById(R.id.btnlogout);



        btnLogout.setOnClickListener(v -> onLogoutClicked());

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
        presenter = new DetailsPresenter(this);
        presenter.bind(this);
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
    public void onLogoutClicked() {

            presenter.doLogout(this);

    }


    @Override
    public void showLoading() {
       loader.setVisibility(View.VISIBLE);
      btnLogout.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
        btnLogout.setEnabled(true);
    }

    @Override
    public void onLogOutSuccess() {
        Intent intent = new Intent(this, LoginActivity.class);

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    @Override
    public void onLogOutError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind(); // Éviter les fuites de mémoire
    }
}