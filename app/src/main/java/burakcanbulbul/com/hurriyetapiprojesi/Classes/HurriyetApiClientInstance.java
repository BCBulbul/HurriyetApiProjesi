package burakcanbulbul.com.hurriyetapiprojesi.Classes;

import burakcanbulbul.com.hurriyetapiprojesi.Constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Burak Can BÜLBÜL on 14.10.2017.
 */

public class HurriyetApiClientInstance
{
    private static Retrofit retrofit = null;


    public static Retrofit newClientInstance()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_URL_RETROFIT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
