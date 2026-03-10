package com.example.dummysecurity.ui.details;

import android.content.Context;

import com.example.dummysecurity.ui.login.LoginContract;

public interface DetailsContract {

    interface View {
        void showLoading();
        void hideLoading();
        void onLogOutSuccess();
        void onLogOutError(String message);
    }
    interface Presenter {
        // La méthode bind permet de lier la vue au presenter
        void bind(DetailsContract.View view);

        // La méthode de déconnexion pour éviter les fuites de mémoire
        void unbind();



        void doLogout(Context context);
    }
}
