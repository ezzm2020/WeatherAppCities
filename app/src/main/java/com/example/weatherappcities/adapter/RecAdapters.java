package com.example.weatherappcities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappcities.DetailsActivity;
import com.example.weatherappcities.R;

import java.util.ArrayList;
import java.util.List;

public class RecAdapters extends RecyclerView.Adapter<RecAdapters.Viewholders> {

    List<String> namecity;
    ItemCliickLisnter itemCliickLisnter;
    Context context;
    public RecAdapters(List<String> namecity) {
        this.namecity = namecity;
    }

    public RecAdapters(List<String> namecity, Context context) {
        this.namecity = namecity;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new Viewholders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholders holder, int position) {

        holder.textView.setText(namecity.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("pogif", position);

                view.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return namecity.size();
    }


    public class Viewholders extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public Viewholders(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.namerow);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            {
                itemCliickLisnter.onclick(v, getAdapterPosition(), false);
            }
        }

    }
    public void updateList(List<String> newname) {
        namecity = new ArrayList<>();
        namecity.addAll(newname);
        notifyDataSetChanged();
    }
}