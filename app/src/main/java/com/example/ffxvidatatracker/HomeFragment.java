package com.example.ffxvidatatracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffxvidatatracker.db.CharacterStuff.Character;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {
    private CharacterLiveData viewModel;

    FindCharacter findCharacter;
    Button button;
    ImageView imageView;
    View view;
    String path, name, server, datacenter;
    EditText txtCharId;
    TextView textName, textDataCenter, textServer;

    private TextWatcher textWatcher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterLiveData.class);
        //Setting stuff to layout
        button = (Button) view.findViewById(R.id.btnSubmitCharId);
        imageView = (ImageView) view.findViewById(R.id.imgChar);
        textName = view.findViewById(R.id.txtCharName);
        textDataCenter = view.findViewById(R.id.txtCharDataCenter);
        textServer = view.findViewById(R.id.txtCharServer);
        txtCharId = (EditText) view.findViewById(R.id.txtCharId);

        //if id is valid length then submit
        txtCharId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtCharId.getText().toString().length() >= 7){
                    FindCharacter();
                    button.setEnabled(true);
                }
                else{
                    button.setEnabled(false);
                }
            }
        });

        //IF you had a previous character Id entered resubmit it
        if(((MainActivity) getContext()).getCharacterId()!= ""){
            txtCharId.setText(((MainActivity) getContext()).getCharacterId());
            findCharacter = new FindCharacter();
            try {

                findCharacter.setCharacterId(txtCharId.getText().toString());
                findCharacter.setOnCharacterListListener(new FindCharacter.OnCharacterListImport() {
                    @Override
                    public void completedCharacter(Character characterResults) {
                        Character results;
                        results = characterResults;
                        name = results.getName();
                        path = results.getPortrait();
                        datacenter = results.getData_center();
                        server = results.getServer();

                        if(path != null){
                            Picasso.get().load(path).memoryPolicy(MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_STORE).into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("CharImg_Success", "Loaded Img Correctly");
                                    textName.setText("Character Name: " + name);
                                    textDataCenter.setText("Datacenter: " + datacenter);
                                    textServer.setText("Server: " + server);
                                    ((MainActivity) getContext()).setCharacterId(txtCharId.getText().toString());
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("CharImg_Error", e.toString());
                                }
                            });
                        }
                        else{//Display Error message in toast not a valid id
                            Toast toast = Toast.makeText(getActivity(), "Not a Valid CharacterID", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }

                });
                findCharacter.execute("");
            }
            catch (Error e) {
                Log.e("FindCharacter_error", e.toString());
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(path != null){
                    Picasso.get().load(path).memoryPolicy(MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_STORE).into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("CharImg_Success", "Loaded Img Correctly");
                            textName.setText("Character Name: " + name);
                            textDataCenter.setText("Datacenter: " + datacenter);
                            textServer.setText("Server: " + server);
                            ((MainActivity) getContext()).setCharacterId(txtCharId.getText().toString());
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("CharImg_Error", e.toString());
                        }
                    });
                }
                else{//Display Error message in toast not a valid id
                    Toast toast = Toast.makeText(getActivity(), "Not a Valid CharacterID", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });
        return view;

    }


    private boolean FindCharacter(){
        findCharacter = new FindCharacter();
        try {

            findCharacter.setCharacterId(txtCharId.getText().toString());
            findCharacter.setOnCharacterListListener(new FindCharacter.OnCharacterListImport() {
                @Override
                public void completedCharacter(Character characterResults) {
                    Character results;
                    results = characterResults;
                    name = results.getName();
                    path = results.getPortrait();
                    datacenter = results.getData_center();
                    server = results.getServer();
                }

            });
            findCharacter.execute("");


            return true;
        }
        catch (Error e) {
            Log.e("FindCharacter_error", e.toString());
        }

        return false;
    }

}