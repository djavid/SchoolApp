package com.djavid.schoolapp.core;


public interface View {
    void showProgressbar();
    void hideProgressbar();
    void showError(int errorId);
    void dispose();
}
