package org.saungit.bakingapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.saungit.bakingapp.R;
import org.saungit.bakingapp.fragment.BakesFragment;

public class MainActivity extends AppCompatActivity {
    public static boolean isMultiPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if (savedInstanceState == null) {
            if (findViewById(R.id.bakesGrid) != null) {
                isMultiPane = true;
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakesFragment bakesFragment = new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesGrid, bakesFragment)
                        .commit();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakesFragment bakesFragment = new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesframe, bakesFragment)
                        .commit();
            }
        }
    }
}
