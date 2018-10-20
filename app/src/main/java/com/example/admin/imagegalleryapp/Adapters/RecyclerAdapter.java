package com.example.admin.imagegalleryapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.imagegalleryapp.Activities.BaseActivity;
import com.example.admin.imagegalleryapp.Activities.ImagesActivity;
import com.example.admin.imagegalleryapp.Dialogs.CustomImageDialogListener;
import com.example.admin.imagegalleryapp.R;
import com.example.admin.imagegalleryapp.models.ModelImage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.admin.imagegalleryapp.Activities.ImagesActivity.getBytesFromInputStream;

public class RecyclerAdapter  extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<ModelImage> dataList;

    public RecyclerAdapter(Context context, ArrayList<ModelImage> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView currentImageview = ((MyViewHolder)holder).getImageview();
        final ModelImage currentImage = this.dataList.get(position);
        Picasso.with(context)
                .load(currentImage.getImageURL())
                .into(currentImageview);

        currentImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    URL imageURL;
                    imageURL = new URL(currentImage.getImageURL());
                    HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    byte[] bytesFromInputStream = getBytesFromInputStream(inputStream);
                    Bitmap bitMap = BitmapFactory.decodeByteArray(bytesFromInputStream, 0, bytesFromInputStream.length);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte[] ba = bao.toByteArray();
                    int size = ba.length;
                    currentImage.setImageSizeKB(size / 1024.0);
                    currentImage.setImageWidth(bitMap.getWidth());
                    currentImage.setImageHeight(bitMap.getHeight());
                    String currentImageInfo = "width:" + currentImage.getImageWidth() +
                            " height:" + currentImage.getImageHeight() + " size:" + size / 1024.0 + "KB";
                    if(context instanceof BaseActivity){
                        ((BaseActivity) context).showImageDialog(context, (CustomImageDialogListener) context,
                                currentImageInfo, currentImage.getImageURL());
                    }
                }
                catch (MalformedURLException e){
                    if(context instanceof BaseActivity){
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageview;
        public MyViewHolder(View itemView){
            super(itemView);
            imageview = itemView.findViewById(R.id.imageviewid);

        }
        public ImageView getImageview(){
            return imageview;
        }
    }
}
