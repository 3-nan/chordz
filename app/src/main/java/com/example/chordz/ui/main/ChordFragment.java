package com.example.chordz.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chordz.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChordFragment extends Fragment {

    View rootView;
    private ChordViewModel mViewModel;
    private static final Map<String, int[]> chordMap = new HashMap<String, int[]>();

    static {
        chordMap.put("C", new int[]{1, 3, 5});
        chordMap.put("D", new int[]{2, 17, 6});
    }

    public static ChordFragment newInstance() {
        return new ChordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chord, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChordViewModel.class);
        // TODO: Use the ViewModel
    }

    public void playChord(View view) {
        String chord = view.getTag().toString();
        int[] notes = chordMap.get(chord);
        System.out.println("I want to play the chord " + chord);

    }
}