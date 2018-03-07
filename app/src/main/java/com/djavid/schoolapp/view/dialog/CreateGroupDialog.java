package com.djavid.schoolapp.view.dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BaseDialogFragment;
import com.djavid.schoolapp.rest.RestDataRepository;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import butterknife.BindView;


public class CreateGroupDialog extends BaseDialogFragment {

    @BindView(R.id.et_group_name)
    EditText et_group_name;
    @BindView(R.id.rl_create_dialog)
    RelativeLayout rl_create_dialog;
    @BindView(R.id.tv_cancel_btn)
    TextView tv_cancel_btn;
    @BindView(R.id.tv_create_btn)
    TextView tv_create_btn;

    private ToolTipsManager mToolTipsManager;


    public CreateGroupDialog() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolTipsManager = new ToolTipsManager();
    }

    @Override
    public View setupView(View view) {

        tv_cancel_btn.setOnClickListener(v -> {
            this.dismiss();
        });

        tv_create_btn.setOnClickListener(v -> {
            try {
                RestDataRepository dataRepository = new RestDataRepository();
                String title = et_group_name.getText().toString().trim();

                if (!isValidValue(title)) return;

                dataRepository.createGroup(title)
                        .subscribe(response -> {
                            Log.d("CreateGroupDialog", "Succesfully sent " + response.toString());
                            dismiss();

                        }, error -> {
                            error.printStackTrace();
                            showError(R.string.error_try_again);
                        });

            } catch (Exception e) {
                e.printStackTrace();
                dismiss();
            }
        });

        et_group_name.addTextChangedListener(textWatcher);

        return view;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            mToolTipsManager.dismissAll();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.dialog_create_group;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private boolean isValidValue(String value) {

        if (value.isEmpty()) {
            showErrorHint(R.string.error_empty_string);
            return false;
        }

        return true;
    }

    private void showErrorHint(int res) {

        String hint = getString(res);

        ToolTip.Builder builder = new ToolTip.Builder(getContext(), et_group_name,
                rl_create_dialog, hint, ToolTip.POSITION_BELOW);
        builder.setBackgroundColor(getResources().getColor(R.color.colorTooltipBg));

        mToolTipsManager.dismissAll();
        mToolTipsManager.show(builder.build());
    }
}
