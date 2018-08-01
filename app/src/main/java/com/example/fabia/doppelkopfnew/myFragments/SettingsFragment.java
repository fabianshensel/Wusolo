package com.example.fabia.doppelkopfnew.myFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fabia.doppelkopfnew.FirebaseHelper;
import com.example.fabia.doppelkopfnew.GameActivity;
import com.example.fabia.doppelkopfnew.Player;
import com.example.fabia.doppelkopfnew.PlayerController;
import com.example.fabia.doppelkopfnew.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings,container,false);

        fragmentView = v;


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        final EditText maxPointsEditText = fragmentView.findViewById(R.id.settingsMaxPointsEditText);
        maxPointsEditText.setHint(String.valueOf(GameActivity.MAX_VALUE_NUMBERPICKER));

        Button tmpBtn = fragmentView.findViewById(R.id.tmpApplyChangesbutton);
        tmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = maxPointsEditText.getText().toString();
                int nr = Integer.parseInt(input);

                GameActivity.MAX_VALUE_NUMBERPICKER = nr;

            }
        });


        Button uploadbtn = fragmentView.findViewById(R.id.uploadToFirebaseButton);
        Button loadbtn = fragmentView.findViewById(R.id.downloadFromFirebaseButton);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlayerController controller = new PlayerController(new ArrayList<Player>());
                controller.readFromJSON(fragmentView.getContext());

                FirebaseHelper.storePlayers(controller.getPlayerList(),fragmentView.getContext());
            }
        });


        loadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseHelper.isNetworkAvailable(fragmentView.getContext())){
                    FirebaseHelper.getPlayers(fragmentView.getContext());
                }else{
                    Toast.makeText(fragmentView.getContext(), "Kein Internet Zugriff", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
