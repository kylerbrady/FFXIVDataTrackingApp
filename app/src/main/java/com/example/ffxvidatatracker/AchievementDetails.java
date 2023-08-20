package com.example.ffxvidatatracker;

import android.app.Dialog;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ffxvidatatracker.db.Mounts.Mounts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AchievementDetails extends DialogFragment {

    View view;
    String path, name, desc, owned, tooltip, bgm, descOne;

    private TextView txtName, txtDesc, txtOwned;
    Button playSong;
    private ImageView imgIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_achievement_details, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtOwned = view.findViewById(R.id.txtOwned);
        imgIcon = (ImageView) view.findViewById(R.id.imgIcon);


        Bundle bundle = this.getArguments();
        if(bundle != null){
            path = bundle.getString("achievement_icon");
            name = bundle.getString("achievement_name");
            desc = bundle.getString("achievement_desc");
            owned = bundle.getString("achievement_owned");
            Picasso.get().load(path).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgIcon, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("ImgSuccess", "Loaded Img Correctly");

                    txtName.setText(name);
                    txtDesc.setText(desc);
                    txtOwned.setText("Owned: " + owned);

                }
                @Override
                public void onError(Exception e) {
                    Log.e("Img_Error", e.toString());
                }
            });
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }
}