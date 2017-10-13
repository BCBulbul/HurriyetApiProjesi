package burakcanbulbul.com.hurriyetapiprojesi;

/**
 * Created by burty on 12.10.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView displayImage;
    public TextView textTitle;
    public TextView textDescription;
    public TextView textUrl;
    public RecyclerViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        textTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        displayImage = (ImageView) itemView.findViewById(R.id.articleImageView);
        textDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        textUrl = (TextView) itemView.findViewById(R.id.tvUrl);

    }
    @Override
    public void onClick(View view) {
    }
}
