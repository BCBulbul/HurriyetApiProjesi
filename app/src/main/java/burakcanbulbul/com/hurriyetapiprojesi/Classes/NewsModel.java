package burakcanbulbul.com.hurriyetapiprojesi.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import burakcanbulbul.com.hurriyetapiprojesi.Interfaces.HurriyetApiService;

/**
 * Created by Burak Can BÜLBÜL on 14.10.2017.
 */

public class NewsModel
{
    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("ContentType")
    @Expose
    public String contentType;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Files")
    @Expose
    public List<NewsImage> files = null;
    @SerializedName("ModifiedDate")
    @Expose
    public String modifiedDate;
    @SerializedName("Path")
    @Expose
    public String path;
    @SerializedName("StartDate")
    @Expose
    public String startDate;
    @SerializedName("Tags")
    @Expose
    public List<String> tags = null;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Url")
    @Expose
    public String url;

}
