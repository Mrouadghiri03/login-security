package com.example.dummysecurity.ui.login;

import com.example.dummysecurity.model.LoginRequest;
import com.example.dummysecurity.model.LoginResponse;
import com.example.dummysecurity.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    // Constructeur obligatoire
    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }
    @Override
    public void bind(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void doLogin(String username, String password) {
        if (view != null) view.showLoading();

        LoginRequest request = new LoginRequest(username, password);

        ApiClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (view == null) return;

                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    // Ici on récupère le token de DummyJSON
                    view.onLoginSuccess(response.body().getAccessToken());
                } else {
                    view.onLoginError("Identifiants invalides (Essaye emilys / emilyspass)");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (view != null) {
                    view.hideLoading();
                    view.onLoginError("Erreur réseau : " + t.getMessage());
                }
            }
        });
    }
}