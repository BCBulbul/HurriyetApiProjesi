package burakcanbulbul.com.hurriyetapiprojesi.Adapters;

/**
 * Created by burty on 12.10.2017.
 */


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import burakcanbulbul.com.hurriyetapiprojesi.Classes.News;
import burakcanbulbul.com.hurriyetapiprojesi.ProgressViewHolder;
import burakcanbulbul.com.hurriyetapiprojesi.R;
import burakcanbulbul.com.hurriyetapiprojesi.RecyclerViewHolders;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>
{
    private List<News> itemList;
    protected Context context;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public RecyclerViewAdapter(final Context context, List<News> itemList, RecyclerView recyclerView)
    {
        this.itemList = itemList;
        this.context = context;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
        {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                    {
                        if(onLoadMoreListener != null)
                        {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        return itemList.get(position) != null ? 1 : 0;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerViewHolders viewHolder = null;
        if(viewType == 1)
        {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new RecyclerViewHolders(layoutView);
        }
        else
        {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(layoutView);
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position)
    {
        if(holder instanceof RecyclerViewHolders)
        {
            News news = itemList.get(position);


            System.out.println(news.getNewsImage());
            ((RecyclerViewHolders)holder).textTitle.setText(news.getTitle());
            ((RecyclerViewHolders)holder).textDescription.setText(news.getDescription());
            ((RecyclerViewHolders)holder).textUrl.setText(news.getUrl());

            if(news.getNewsImage() != null)
            {
                Picasso.with(context).load(news.getNewsImage()).into(holder.displayImage);
            }

            else
            {
                Picasso.with(context).load("http://www.trfinansemlak.com/UPLOADS/anaresimyok.jpg").into(holder.displayImage);
            }


        }
        else
        {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }
    public void setLoad()
    {
        loading = false;
    }
    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
    {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }
    public void setLoaded()
    {
        loading = false;
    }
}
