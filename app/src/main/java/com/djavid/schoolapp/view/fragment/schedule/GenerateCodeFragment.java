package com.djavid.schoolapp.view.fragment.schedule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BaseFragment;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.rest.RestDataRepository;

import java.util.Locale;

import butterknife.BindView;
import info.hoang8f.widget.FButton;


public class GenerateCodeFragment extends BaseFragment {

    @BindView(R.id.studentCode)
    RadioButton studentCode;
    @BindView(R.id.teacherCode)
    RadioButton teacherCode;
    @BindView(R.id.date_picker)
    DatePicker date_picker;
    @BindView(R.id.generate_btn)
    Button generate_btn;
    @BindView(R.id.tv_code)
    TextView tv_code;


    public GenerateCodeFragment() { }

    public static GenerateCodeFragment newInstance() {
        return new GenerateCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View setupView(View view) {

        generate_btn.setOnClickListener(v -> {

            RestDataRepository dataRepository = new RestDataRepository();

            String expiration_date = String.format(Locale.US, Api.DATE_STRING_FORMAT,
                    date_picker.getYear(), date_picker.getMonth() + 1,
                    date_picker.getDayOfMonth(), 12, 0, 0);
            int level = studentCode.isChecked() ? 1 : 2;

            dataRepository.createCode(expiration_date, level)
                    .subscribe(response -> {
                        tv_code.setText(response.get("code"));
                        tv_code.setVisibility(View.VISIBLE);
                    }, error -> {
                        Log.e(TAG, expiration_date);
                        showError(R.string.load_error);
                    });
        });

        return view;
    }

    @Override
    public void loadData() { }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_generate_code;
    }

    @Override
    public String getPresenterId() {
        return null;
    }

}
