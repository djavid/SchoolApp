package com.djavid.schoolapp.view.fragment.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BaseFragment;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.rest.RestDataRepository;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;


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
        Calendar date = Calendar.getInstance();
        date.set(date_picker.getYear(), date_picker.getMonth(), date_picker.getDayOfMonth());
        date.add(Calendar.DAY_OF_MONTH, 1);
        date_picker.updateDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

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
