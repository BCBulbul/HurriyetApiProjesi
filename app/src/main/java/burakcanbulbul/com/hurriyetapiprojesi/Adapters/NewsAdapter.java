package burakcanbulbul.com.hurriyetapiprojesi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import burakcanbulbul.com.hurriyetapiprojesi.Classes.NewsImage;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.NewsModel;
import burakcanbulbul.com.hurriyetapiprojesi.R;

/**
 * Created by Burak Can BÜLBÜL on 15.10.2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>
{
    private List<NewsModel> newsModelList;
    private Context context;
    private int newsCounter;

    public NewsAdapter(List<NewsModel> android, Context context)
    {
        this.newsModelList = android;
        this.context = context;
        Log.d("Android Size",String.valueOf(android.size()));
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int i)
    {

        NewsModel model = newsModelList.get(i);
        newsCounter++;

        for(NewsImage image : model.files)
        {

            if(image.fileUrl.isEmpty())
            {
                Picasso.with(context).load("http://www.trfinansemlak.com/UPLOADS/anaresimyok.jpg").into(viewHolder.imageView);
            }

            Picasso.with(context).load(image.fileUrl).into(viewHolder.imageView);



        }

        if(newsCounter == newsModelList.size())
        {
            Toast.makeText(context, "Maksimum Haber Sayısına Ulaşıldı", Toast.LENGTH_LONG).show();
        }



        viewHolder.tvTitle.setText(newsModelList.get(i).title);
        viewHolder.tvDescription.setText(newsModelList.get(i).description);
        viewHolder.tvUrl.setText(newsModelList.get(i).url);





    }

    @Override
    public int getItemCount()
    {
        return newsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvUrl;
        private ImageView imageView;

        public ViewHolder(View view)
        {
            super(view);

            tvTitle = (TextView)view.findViewById(R.id.tvTitleRetrofitView);
            tvDescription = (TextView)view.findViewById(R.id.tvDescriptionRetrofitView);
            tvUrl = (TextView)view.findViewById(R.id.tvUrlRetrofitView);
            imageView = (ImageView) view.findViewById(R.id.imageViewRetrofit);


        }
    }

}