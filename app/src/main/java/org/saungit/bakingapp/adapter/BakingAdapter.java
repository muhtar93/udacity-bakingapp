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

/**
 * Created by Muhtar on 23/05/2017.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.ViewHolder> {
    private List<Baking> bakingList;
    private Context context;

    public BakingAdapter(List<Baking> agendaList, Context context) {
        this.bakingList = agendaList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_baking, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Baking baking = bakingList.get(position);
        holder.name.setText(baking.getName());
        holder.servings.setText(baking.getServings());
    }

    @Override
    public int getItemCount() {
        return bakingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,servings;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.textNameBaking);
            servings = (TextView)itemView.findViewById(R.id.textServings);
        }
    }
}
