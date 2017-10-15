package burakcanbulbul.com.hurriyetapiprojesi.Interfaces;

import burakcanbulbul.com.hurriyetapiprojesi.Classes.HurriyetApiClientInstance;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.News;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.NewsModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by burty on 14.10.2017.
 */

public interface HurriyetApiService
{


    @GET("/v1/articles?apikey=53e78bfa1b7b4349a3df3c25cd7fc201")
    Call<NewsModel []> retrieveNoFilteringNews();

    @GET("/v1/articles?apikey=53e78bfa1b7b4349a3df3c25cd7fc201&&$filter=Path eq '/spor/'")
    Call<NewsModel [] > retrieveSportNews();

    @GET("/v1/articles?apikey=53e78bfa1b7b4349a3df3c25cd7fc201&&$filter=Path eq '/gundem/'")
    Call<NewsModel []> retrieveJournalNews();

    @GET("/v1/articles?apikey=53e78bfa1b7b4349a3df3c25cd7fc201&&$filter=Path eq '/ekonomi/'")
    Call<NewsModel []> retrieveEconomyNews();



}
