package com.djavid.schoolapp.core;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.view.View;
import android.widget.Toast;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements com.djavid.schoolapp.core.View {

    @BindView(R.id.progressbar)
    View progressbar;

    private Unbinder unbinder;

    public abstract int getLayoutId();

    public abstract String getPresenterId();

    public abstract View setupView(View view);

    public abstract void loadData();

    public final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return setupView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public <CustomPresenter> CustomPresenter getPresenter(Class<CustomPresenter> c) {
        return c.cast(App.getAppInstance().getPresenterProvider().getPresenter(getPresenterId(),
                Presenter.class));
    }

    @Override
    public void showProgressbar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int errorId) {
        Toast.makeText(getContext(), getString(errorId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dispose() {
        App.getAppInstance().getPresenterProvider().removePresenter(getPresenterId());
    }
}
