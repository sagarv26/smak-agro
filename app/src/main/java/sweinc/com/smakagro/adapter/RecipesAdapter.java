package sweinc.com.smakagro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.classes.Show_Details;
import sweinc.com.smakagro.model.RecyclerItem;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.viewHolder>{

        private List<RecyclerItem> listItem;
        private Context mContext;

        public class viewHolder extends RecyclerView.ViewHolder {
            public ImageView index_image;
            public TextView index_name;

            viewHolder(View itemView) {
                super(itemView);
                index_name= itemView.findViewById(R.id.index_name);
                index_image= itemView.findViewById(R.id.index_image);

            }
        }

        public RecipesAdapter(List<RecyclerItem> listItem, Context mContext) {
            this.listItem = listItem;
            this.mContext = mContext;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.index_view,parent,false);
            return new viewHolder(v);
        }

        public void onBindViewHolder(viewHolder holder, final int position) {
            final RecyclerItem item = this.listItem.get(position);
            holder.index_name.setText(item.getIndex_name());

            Glide.with(mContext).load(item.getIndex_thumbnail()).into(holder.index_image);
            holder.index_image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    RecipesAdapter.this.ChangePage(RecipesAdapter.this.listItem.get(position));
                }
            });

        }

        private void ChangePage(RecyclerItem item) {
            Intent intent = new Intent(this.mContext, Show_Details.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            intent.putExtra("keyTitle", item.getIndex_name());
            intent.putExtra("materialDes", item.getIndex_material());
            intent.putExtra("procedure", item.getIndex_procedure());
            bundle.putInt("keyImage", item.getIndex_thumbnail());
            intent.putExtras(bundle);
            this.mContext.startActivity(intent);
        }

        public int getItemCount() {
            return this.listItem.size();
        }

    }

