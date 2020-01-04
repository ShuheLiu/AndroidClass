package com.example.myapplication;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.VH> {
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView nameView;
        public final TextView typeView;

        public VH(View v){
            super(v);
            nameView = (TextView)v.findViewById(R.id.text_view);
            typeView = (TextView)v.findViewById(R.id.type);
        }
    }

    private List<material> datas;
    public MediaAdapter(List<material> data){
        datas = data;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);          //点击事件
    }

    private MediaAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(MediaAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull final MediaAdapter.VH vh, final int position) {
        vh.nameView.setText(datas.get(position).getMaterialUrl());
        vh.typeView.setText(datas.get(position).getMaterialType());
        vh.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View v){    //点击事件
                if(onItemClickListener != null){
                    int pos = vh.getLayoutPosition();
                    onItemClickListener.onItemClick(vh.itemView, pos);
                }
            }
        });
    }

    public int getItemCount(){
        return datas.size();
    }

    @NonNull
    @Override
    public MediaAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false );
        return new MediaAdapter.VH(v);
    }
}
