package org.saungit.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.saungit.bakingapp.activity.DetailVideoActivity;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.listener.ItemClickListener;
import org.saungit.bakingapp.model.Baking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muhtar on 23/05/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private List<Baking> bakingList;
    private Context context;

    public StepsAdapter(List<Baking> agendaList, Context context) {
        this.bakingList = agendaList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_steps, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Baking baking = bakingList.get(position);
        holder.shortDesc.setText(baking.getShortDescription());
        Glide.with(context).load(baking.getThumbnailURL())
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.chef)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {
                Intent steps = new Intent(context, DetailVideoActivity.class);
                steps.putExtra("name", baking.getName());
                steps.putExtra("videoURL", baking.getVideoURL());
                steps.putExtra("description", baking.getDescription());
                context.startActivity(steps);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bakingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.textShortDesc) TextView shortDesc;
        @BindView(R.id.image) ImageView imageView;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view);
        }
    }
}
