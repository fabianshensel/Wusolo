package com.example.fabia.doppelkopfnew.myFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fabia.doppelkopfnew.AddGameActivity;
import com.example.fabia.doppelkopfnew.GameController;
import com.example.fabia.doppelkopfnew.R;

public class LastGameFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lastgame,container,false);

        return v;
    }
}
