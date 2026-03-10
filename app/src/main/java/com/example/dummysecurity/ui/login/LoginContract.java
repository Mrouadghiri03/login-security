package com.example.dummysecurity.ui.login;


public interface LoginContract {

    interface View {
        void showLoading();
        void hideLoading();
        void onLoginSuccess(String token);
        void onLoginError(String message);
    }

    interface Presenter {
        // La méthode bind permet de lier la vue au presenter
        void bind(LoginContract.View view);

        // La méthode de déconnexion pour éviter les fuites de mémoire
        void unbind();

        // L'action principale de login
        void doLogin(String username, String password);
    }
}