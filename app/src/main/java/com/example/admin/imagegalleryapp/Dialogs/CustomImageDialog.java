package com.example.admin.imagegalleryapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.imagegalleryapp.R;
import com.squareup.picasso.Picasso;

public class CustomImageDialog extends Dialog {
    public CustomImageDialogListener mListener = null;
    public ImageView imageView;
    public TextView imageInfo;
    public Button imageOkButton;
    public CustomImageDialog(@NonNull Context context, CustomImageDialogListener listener,
                             String message, String imageURL, final boolean cancelable) {
        super(context);
        this.setCancelable(true);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.image_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.mListener = listener;

        imageView = findViewById(R.id.image_viewid);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight() * 3/4 ;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        imageInfo = findViewById(R.id.image_info);
        imageOkButton = findViewById(R.id.button_image_ok);

        if(!TextUtils.isEmpty(message)){
            imageInfo.setText(message);
        }
        if(!TextUtils.isEmpty(imageURL)){
            Picasso.with(context)
                    .load(imageURL)
                    .into(imageView);
        }
        imageOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelable){
                    dismiss();
                }
                else if(mListener != null){
                    mListener.onImageOKButtonClicked();
                }
            }
        });
    }
}
