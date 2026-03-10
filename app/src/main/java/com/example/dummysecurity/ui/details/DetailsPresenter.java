package com.example.dummysecurity.ui.details;


import android.content.Context;
import android.content.SharedPreferences;


public class DetailsPresenter implements DetailsContract.Presenter{

    // Constructeur obligatoire

    private DetailsContract.View view;

    public DetailsPresenter(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void bind(DetailsContract.View view) {
        this.view = view;

    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void doLogout(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        pref.edit().clear().apply();

        if (view != null) {
            view.onLogOutSuccess();
        }
    }


}
