package com.example.ffxvidatatracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ffxvidatatracker.db.Mounts.Mounts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MountDetails extends DialogFragment {
    View view;
    String path, name, desc, owned, tooltip, bgm, descOne;
    Mounts mounts;
    Toolbar toolbar;
    MediaPlayer mediaPlayer;
    private TextView txtName, txtDesc, txtOwned, txtToolTip, txtDescOne;
    Button playSong;
    private ImageView imgIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_mounts, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtOwned = view.findViewById(R.id.txtOwned);
        txtToolTip = view.findViewById(R.id.txtToolTip);
        txtDescOne = view.findViewById(R.id.txtDescOne);
        imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        mediaPlayer = new MediaPlayer();
        playSong = (Button) view.findViewById(R.id.btnPlaySong);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            path = bundle.getString("mount_icon");
            name = bundle.getString("mount_name");
            desc = bundle.getString("mount_journal");
            owned = bundle.getString("mount_owned");
            descOne = bundle.getString("mount_desc");
            tooltip = bundle.getString("mount_tooltip");
            bgm = bundle.getString("mount_bgm");
            Picasso.get().load(path).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgIcon, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("ImgSuccess", "Loaded Img Correctly");

                    txtName.setText(name);
                    txtDesc.setText(desc);
                    txtOwned.setText("Owned: " + owned);
                    txtDescOne.setText(descOne);
                    txtToolTip.setText(tooltip);
                    mediaPlayer.stop();
                    mediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    );
                    try { //File May not exist
                        if(bgm != null) {
                            mediaPlayer.setDataSource(bgm);
                            mediaPlayer.prepare();
                            playSong.setVisibility(View.VISIBLE);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                @Override
                public void onError(Exception e) {
                    Log.e("Img_Error", e.toString());
                }
            });
            playSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
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

