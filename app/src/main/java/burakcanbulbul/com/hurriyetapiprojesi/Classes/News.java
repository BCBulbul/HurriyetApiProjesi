package burakcanbulbul.com.hurriyetapiprojesi.Classes;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by burty on 8.10.2017.
 */

public class News
{
    private String url;
    private String description;
    private String title;
    private String path;
    private String newsImage;
    private Context context;


    public News()
    {

    }


    public News(String url, String description, String title, String path, String newsImage)
    {

        this.url = url;
        this.description = description;
        this.title = title;
        this.path = path;
        this.newsImage = newsImage;
    }

    public News(Context context)
    {
        this.context = context;
    }


    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getNewsImage()
    {
        return newsImage;
    }

    public void setNewsImage(String newsImage)
    {
        this.newsImage = newsImage;
    }
}
