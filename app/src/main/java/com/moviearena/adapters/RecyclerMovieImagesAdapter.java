package com.moviearena.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moviearena.R;
import com.bumptech.glide.Glide;
import com.moviearena.models.MovieImagesModel;

import java.util.ArrayList;
import com.moviearena.Utils.Consts;

public class RecyclerMovieImagesAdapter extends RecyclerView.Adapter<RecyclerMovieImagesAdapter.Holder> {


    private ArrayList<MovieImagesModel.Backdrops> list;
    public void setList(ArrayList<MovieImagesModel.Backdrops> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_image,parent,false);
        return new Holder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0:list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (list.get(position).getFile_path()!=null){
            String url = Consts.IMAGE_URL+list.get(position).getFile_path();
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .into(holder.Image);
        }

    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView Image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.MovieImage);

        }
    }


}
