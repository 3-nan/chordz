package com.example.chordz;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.chordz.ui.main.SectionsPagerAdapter;
import com.example.chordz.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

import piano.PianoView;

public class MainActivity extends AppCompatActivity {

    private static final Map<String, int[]> chordMap = new HashMap<String, int[]>();

    static {
        chordMap.put("C", new int[]{1, 3, 5});
        chordMap.put("D", new int[]{2, 17, 6});
        chordMap.put("E", new int[]{3, 18, 7});
        chordMap.put("F", new int[]{4, 6, 8});
        chordMap.put("G", new int[]{5, 7, 9});
        chordMap.put("A", new int[]{6, 20, 10});
        chordMap.put("B", new int[]{7, 21, 22});
        // min
        chordMap.put("cmin", new int[]{1, 16, 5});
        chordMap.put("dmin", new int[]{2,4,6});
        chordMap.put("emin", new int[]{3,5,7});
        chordMap.put("fmin", new int[]{4,18,8});
        chordMap.put("gmin", new int[]{5,19,9});
        chordMap.put("amin", new int[]{6,8,10});
        chordMap.put("bmin", new int[]{7,9,22});
        // dim
        chordMap.put("cdim", new int[]{1,16,17});
    }

private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void playChord(View view) {
        String chord = view.getTag().toString();
        int[] notes = chordMap.get(chord);

        PianoView pianoView = view.getRootView().findViewById(R.id.pianoView);
        if (pianoView != null) {
            pianoView.playChord(notes);
        }
    }
}