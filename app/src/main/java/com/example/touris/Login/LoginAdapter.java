package com.example.touris.Login;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.touris.SignUp.SignUpFragment;

public class LoginAdapter extends FragmentStateAdapter {
    int totalTab;

    public LoginAdapter(FragmentActivity fm, int totalTab) {
        super(fm);
        this.totalTab = totalTab;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();

                return loginFragment;
            case 1:
                SignUpFragment signUp_fragment = new SignUpFragment();
                return signUp_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTab;
    }
}


