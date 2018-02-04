package com.djavid.schoolapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GenerateCodeActivity extends AppCompatActivity implements View.OnClickListener {

    DatePicker _datePicker;
    TextView _codeTextView;
    Button _createCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        _datePicker = findViewById(R.id.datePicker);
        _codeTextView = findViewById(R.id.codeTextView);
        _createCodeButton = findViewById(R.id.generateButton);
        _createCodeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.generateButton:
                App.getAppInstance().getApi()
                        .createCode(String.format(Api.DATE_STRING_FORMAT, _datePicker.getYear(), _datePicker.getMonth() + 1, _datePicker.getDayOfMonth(), 12, 0, 0),
                                ((RadioButton) findViewById(R.id.studentCode)).isChecked() ? 1 : 2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(q -> _codeTextView.setText(q.get("code")),
                                q -> Log.e("", String.format("%04d-%02d-%02dT12:00:00", _datePicker.getYear(), _datePicker.getMonth(), _datePicker.getDayOfMonth())))
                ;
        }
    }
}
