package com.konka.kkstar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konka.kkstar.R;
import com.konka.kkstar.bean.AlbumBean;
import com.konka.kkstar.util.HttpHelper;
import com.konka.kkstar.util.LogUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by HP on 2018-4-20.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterView> {
    private final String TAG = "MovieAdapter";
    private List<AlbumBean>  movies = null;
    private Context mContext;
    private int ITEM_COUNT = 6; //默认6个
    private boolean isFirst = true;

    private OnItemSelectedListener itemSelectedListener;
    private OnItemClickListener onItemClickListener;
    public void setItemSelectedListener(OnItemSelectedListener listener) {
        this.itemSelectedListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MovieAdapter(Context context, List<AlbumBean> movies) {
        this.mContext = context;
        this.movies = movies;
    }

    @Override
    public AdapterView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.float_movie_item, parent, false);
        return new AdapterView(view);
    }

    @Override
    public void onBindViewHolder(final AdapterView holder, final int position) {
        LogUtils.d(TAG, "---------- onBindViewHolder ----------");
        holder.movie_seq_num.setText(""+(position%ITEM_COUNT+1));
        holder.movie_name.setText(movies.get(position).getName());
        List<AlbumBean.PosterListBean> posterList = movies.get(position).getPosterList();
        HttpHelper.downloadPicture(mContext, posterList.get(posterList.size()-1).getUrl(), R.drawable.img_default,
                R.drawable.img_default, holder.movie_pic);

        if (position == 0 && isFirst){
            holder.itemView.requestFocus();
            isFirst = !isFirst;
        }

        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                holder.movie_name.setSelected(hasFocus);
                if (hasFocus) {
                    System.out.println("--- OnFocusChangeListener position="+position);
                    itemSelectedListener.OnItemSelected(position);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--- click position = "+position);
                onItemClickListener.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class AdapterView extends RecyclerView.ViewHolder {
        RoundedImageView movie_pic;
        TextView movie_name;
        TextView movie_seq_num;

        public AdapterView(View itemView) {
            super(itemView);
            movie_pic = (RoundedImageView) itemView.findViewById(R.id.movie_pic);
            movie_name = (TextView) itemView.findViewById(R.id.movie_name);
            movie_seq_num = (TextView) itemView.findViewById(R.id.movie_seq_num);
        }
    }

    public interface OnItemSelectedListener {
        void OnItemSelected(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setITEM_COUNT(int ITEM_COUNT) {
        this.ITEM_COUNT = ITEM_COUNT;
    }

    public int getITEM_COUNT() {
        return ITEM_COUNT;
    }
}
