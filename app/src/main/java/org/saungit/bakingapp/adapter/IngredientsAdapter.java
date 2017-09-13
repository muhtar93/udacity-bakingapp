package org.saungit.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saungit.bakingapp.R;
import org.saungit.bakingapp.model.Baking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muhtar on 23/05/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Baking> bakingList;
    private Context context;

    public IngredientsAdapter(List<Baking> agendaList, Context context) {
        this.bakingList = agendaList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_ingredients, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Baking baking = bakingList.get(position);
        holder.quantity.setText(baking.getQuantity());
        holder.measure.setText(baking.getMeasure());
        holder.ingredient.setText(baking.getIngredient());
    }

    @Override
    public int getItemCount() {
        return bakingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textQty) TextView quantity;
        @BindView(R.id.textMeasure) TextView measure;
        @BindView(R.id.textIngredient) TextView ingredient;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
