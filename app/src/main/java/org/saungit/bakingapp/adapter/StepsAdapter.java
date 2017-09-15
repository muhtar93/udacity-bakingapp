package org.saungit.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.saungit.bakingapp.R;
import org.saungit.bakingapp.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ArticleViewHolder> {

    final private ListItemClickListener mOnClickListener;
    final private ArrayList<Step> steps;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public StepsAdapter(ListItemClickListener listener, ArrayList<Step> steps) {
        mOnClickListener = listener;
        this.steps = steps;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {

        ImageView icon;
        TextView name;
        TextView servings;


        public ArticleViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            servings = (TextView) itemView.findViewById(R.id.servings);
            itemView.setOnClickListener(this);
        }

        void onBind(int position) {
            if (!steps.isEmpty()) {
                if(!steps.get(position).getThumbnailURL().isEmpty()) {
                    Picasso.with(itemView.getContext()).load(steps.get(position).getThumbnailURL()).error(R.drawable.chef).into(icon);
                }else{
                    icon.setImageResource(R.drawable.chef);
                }
                name.setText(steps.get(position).getShortDescription());
                servings.setText(itemView.getContext().getString(R.string.step)+" "+steps.get(position).getId());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
