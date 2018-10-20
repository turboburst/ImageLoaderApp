package com.example.admin.imagegalleryapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.imagegalleryapp.R;

public class CustomAlertDialog extends Dialog {
    public CustomAlertDialogListener mListener = null;
    public Button okButton;
    public TextView errorMessageTextView;
    public CustomAlertDialog(@NonNull Context context, CustomAlertDialogListener listener, String message, final boolean cancelable) {
        super(context);
        this.setCancelable(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.alert_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.mListener = listener;

        okButton = findViewById(R.id.button_message_ok);
        errorMessageTextView = findViewById(R.id.label_errormessage);
        if(!TextUtils.isEmpty(message)){
            errorMessageTextView.setText(message);
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelable){
                    dismiss();
                }
                else if(mListener != null){
                    mListener.onOKButtonClicked();
                }
            }
        });
    }
}
