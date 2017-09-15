package org.saungit.bakingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saungit.bakingapp.R;
import org.saungit.bakingapp.activity.MainActivity;
import org.saungit.bakingapp.activity.StepsDetailActivity;
import org.saungit.bakingapp.adapter.IngredientsAdapter;
import org.saungit.bakingapp.adapter.StepsAdapter;
import org.saungit.bakingapp.model.Step;

import java.util.ArrayList;

import static org.saungit.bakingapp.fragment.BakesFragment.bakes;

public class StepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private RecyclerView stepsRecyclerView;
    private RecyclerView ingredientRecyclerView;
    private StepsAdapter stepsAdapter;
    private IngredientsAdapter ingredientsAdapter;

    private View rootView;
    private int index = 0;
    public static ArrayList<Step> steps = new ArrayList<>();

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.steps_fragment, container, false);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.stepslist);
        ingredientRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientslist);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        index = getActivity().getIntent().getExtras().getInt(getString(R.string.extra));
        steps = bakes.get(index).getSteps();
        stepsAdapter = new StepsAdapter(this, steps);
        ingredientsAdapter = new IngredientsAdapter(bakes.get(index).getIngredients());
        stepsRecyclerView.setAdapter(stepsAdapter);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (!MainActivity.isMultiPane) {
            Intent intent = new Intent(getActivity(), StepsDetailActivity.class);
            intent.putExtra("item", clickedItemIndex);
            startActivity(intent);
        } else {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            StepsDetailsFragment stepsDetailsFragment = new StepsDetailsFragment();
            stepsDetailsFragment.index = clickedItemIndex;
            fragmentManager.beginTransaction()
                    .replace(R.id.stepsdetailsframe, stepsDetailsFragment)
                    .commit();
        }
    }
}

