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

import com.example.fabia.doppelkopfnew.GameActivity;
import com.example.fabia.doppelkopfnew.R;

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


    }
}
