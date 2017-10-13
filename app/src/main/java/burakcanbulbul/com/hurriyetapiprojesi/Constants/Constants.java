package burakcanbulbul.com.hurriyetapiprojesi.Constants;

/**
 * Created by burty on 8.10.2017.
 */

public final class Constants
{
    public static final String API_URL = "https://api.hurriyet.com.tr/v1/";
    public static final String API_KEY = "53e78bfa1b7b4349a3df3c25cd7fc201";
    public static String NO_FILTER_URL = API_URL + "articles?apikey=" + API_KEY + "&$top=";
    private static String FILTER_URL = API_URL +"articles?apikey=" + API_KEY;

    public static final String getLinkByFilter(String  counter,String filter)
    {
        FILTER_URL +="&$top="+Integer.parseInt(counter)+"&$filter=Path eq '/" + filter + "/'";
        return FILTER_URL;
    }

}
