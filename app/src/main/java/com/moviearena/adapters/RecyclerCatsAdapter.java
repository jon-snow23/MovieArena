package com.moviearena.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.moviearena.R;
import com.moviearena.models.MovieModel;

import java.util.ArrayList;
import com.moviearena.Utils.Consts;

public class RecyclerCatsAdapter extends RecyclerView.Adapter<RecyclerCatsAdapter.Holder> {


    private ArrayList<MovieModel.ResultsDTO> list;
    private OnItemClick onItemClick ;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setList(ArrayList<MovieModel.ResultsDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_moviebig,parent,false);
        return new Holder(view);
    }

    public int getItemCount(Context context) {
        return list == null ? 0:list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (list.get(position).getPosterPath() != null) {
            String url = Consts.IMAGE_URL + list.get(position).getPosterPath();
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.noimage) // Added placeholder
                    .error(com.denzcoskun.imageslider.R.drawable.error) // Added error image
                    .into(holder.MovieImage);
        }

        holder.MovieName.setText(list.get(position).getTitle());

        // Added null check for mainlayout
        if (holder.mainlayout != null) {
            if (position == list.size() - 2 || position == list.size() - 1) {
                holder.mainlayout.setVisibility(View.VISIBLE);
            } else {
                holder.mainlayout.setVisibility(View.GONE);
            }
        } else {
            Log.e("RecyclerCatsAdapter", "mainlayout is null for position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView MovieImage;
        TextView MovieName;
        ConstraintLayout mainlayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            MovieImage = itemView.findViewById(R.id.image_movie);
            MovieName = itemView.findViewById(R.id.movie_title);
            mainlayout = itemView.findViewById(R.id.mainLayout); // Ensure mainLayout exists in XML

            itemView.setOnClickListener(view -> {
                if (onItemClick != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClick.OnClick(list.get(getAdapterPosition()).getId());
                }
            });
        }
    }


    public interface OnItemClick{

        void OnClick(int MovieId);

    }

}
