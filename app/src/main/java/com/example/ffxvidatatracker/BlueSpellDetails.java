package com.example.ffxvidatatracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffxvidatatracker.db.AppDatabase;
import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class BlueSpellDetails extends DialogFragment {
    int blue_pk;
    String path;
    BlueSpells blueSpells;
    View view;
    Toolbar toolbar;

    private TextView txtBlueName, txtBlueDesc, txtBlueLocation;
    private ImageView imgIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);

        txtBlueName = view.findViewById(R.id.txtName);
        txtBlueDesc = view.findViewById(R.id.txtDesc);
        txtBlueLocation = view.findViewById(R.id.txtLocation);
        imgIcon = (ImageView) view.findViewById(R.id.imgIcon);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            blue_pk = bundle.getInt("blue_pk");
            path = bundle.getString("blue_icon");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    blueSpells = AppDatabase.getInstance(getContext())
                            .BlueSpellsDAO()
                            .getById(blue_pk);
                    txtBlueName.setText("#"+blueSpells.getOrder() +": "+ blueSpells.getName());
                    txtBlueDesc.setText(blueSpells.getDescription());
                    Log.e("", blueSpells.getDescription().toString());
                    String locations = "";
                    for (int i = 0; i < blueSpells.getSources().size(); i++) {
                        locations = locations + blueSpells.getSources().get(i).getText() + "\n";
                    }
                    txtBlueLocation.setText(locations);
                }
            }).start();
            Picasso.get().load(path).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgIcon, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("ImgSuccess", "Loaded Img Correctly");
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
