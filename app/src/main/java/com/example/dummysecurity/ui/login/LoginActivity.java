package com.example.dummysecurity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dummysecurity.R;
import com.example.dummysecurity.ui.details.DetailsActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    EditText etUser;
    EditText etPass;
    Button btnSignIn;
    ProgressBar loader;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialisation des vues
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        loader = findViewById(R.id.loader);

        btnSignIn.setOnClickListener(v -> onLoginClicked());

        // Initialisation du Presenter
        presenter = new LoginPresenter(this);
        presenter.bind(this);
    }

    public void onLoginClicked() {
        String user = etUser.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            onLoginError("Veuillez remplir tous les champs");
        } else {
            presenter.doLogin(user, pass);
        }
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
        btnSignIn.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
        btnSignIn.setEnabled(true);
    }

    @Override
    public void onLoginSuccess(String token) {
        // Redirection vers la deuxième page avec le token
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("ACCESS_TOKEN", token);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind(); // Éviter les fuites de mémoire
    }
}