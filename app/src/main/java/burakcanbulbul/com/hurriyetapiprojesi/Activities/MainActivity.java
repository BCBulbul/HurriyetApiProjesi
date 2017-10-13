package burakcanbulbul.com.hurriyetapiprojesi.Activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import burakcanbulbul.com.hurriyetapiprojesi.Adapters.RecyclerViewAdapter;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.HttpHandler;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.News;
import burakcanbulbul.com.hurriyetapiprojesi.R;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;
    private LinearLayoutManager linearLayoutManager;
    private  List<News> adapterData;
    private Handler handler;
    private static String apiKey = "53e78bfa1b7b4349a3df3c25cd7fc201";
    public static final String apiUrl = "https://api.hurriyet.com.tr/v1/";
    private List<News> news = new ArrayList<>();
    private HttpHandler httpHandler;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private News article;
    private int counter = 10;
    private MaterialSearchView searchView;
    private int filteredCounter = 10;
    private List<News> articles;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    private void init()
    {
        searchView =  (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);


    }

    private void hideFloatingActionButton()
    {
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.GONE);
    }

    private void doSomeSearch()
    {

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                //Do some magic
                try
                {
                    Toast.makeText(MainActivity.this, query.toUpperCase()+" Haberleri Getiriliyor...", Toast.LENGTH_LONG).show();
                    loadNewsByFilter(query);
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener()
        {
            @Override
            public void onSearchViewShown()
            {

            }

            @Override
            public void onSearchViewClosed()
            {

            }
        });
    }

    public void fillRecyclerViewFirstRandomArticles()
    {
        handler = new Handler();
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        try
        {
                adapterData = getFirstDataNoFilter();
                counter+=10;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, adapterData, recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore() {
                if(counter < 70)
                {
                    adapterData.add(null);


                    Handler handlerA = new Handler();

                    final Runnable r = new Runnable()
                    {
                        public void run() {
                            recyclerViewAdapter.notifyItemInserted(adapterData.size() - 1);
                            adapterData.remove(adapterData.size() - 1);
                            recyclerViewAdapter.notifyItemRemoved(adapterData.size());;
                            counter+=10;
                            Toast.makeText(MainActivity.this, "Haberler Getiriliyor...", Toast.LENGTH_SHORT).show();
                            if(counter == 60)
                            {
                                Toast.makeText(MainActivity.this, "Maksimum Haber Sayısına Ulaştınız!", Toast.LENGTH_SHORT).show();

                                return;
                            }
                            recyclerViewAdapter.notifyItemInserted(adapterData.size());
                            NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
                            Log.d("Counter", String.valueOf(counter));
                            try {
                                List<News> data = newsAsyncTask.execute(new String[]{"noFilter",String.valueOf(counter)}).get();
                                for (News article : data)
                                {
                                    Log.d("Article Descriotion",article.getDescription());
                                    adapterData.add(article);
                                    recyclerViewAdapter.notifyItemInserted(adapterData.size());

                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            recyclerViewAdapter.setLoaded();
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    };

                    handlerA.post(r);

                    System.out.println("load");


                }
                swipeRefresh();
            }

        });
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.purpleBar)));

        init();
        hideFloatingActionButton();
        doSomeSearch();
        fillRecyclerViewFirstRandomArticles();

    }

    public void swipeRefresh()
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    public void loadNewsByFilter(final String query) throws ExecutionException, InterruptedException
    {



        recyclerView.setLayoutManager(linearLayoutManager);



        articles = getFirstData(query);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, articles, recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(filteredCounter < 70)
                {
                    articles.add(null);


                    Handler handlerA = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            recyclerViewAdapter.notifyItemInserted(articles.size() - 1);
                            articles.remove(articles.size() - 1);
                            recyclerViewAdapter.notifyItemRemoved(articles.size());;
                            filteredCounter +=10;
                            Toast.makeText(MainActivity.this, "Haberler Getiriliyor...", Toast.LENGTH_LONG).show();
                            if(filteredCounter == 60)
                            {
                                Toast.makeText(MainActivity.this, "Maksimum Haber Sayısına Ulaştınız!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            recyclerViewAdapter.notifyItemInserted(articles.size());
                            NewsAsyncTask newsAsyncTask = new NewsAsyncTask();

                            try {

                                List<News> data = newsAsyncTask.execute(new String[]{"last",query,String.valueOf(filteredCounter)}).get();
                                for (News article : data)
                                {
                                    Log.d("Article Descriotion",article.getDescription());
                                    articles.add(article);
                                    recyclerViewAdapter.notifyItemInserted(articles.size());

                                }

                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            recyclerViewAdapter.setLoaded();
                        }

                    };

                    handlerA.post(r);

                    System.out.println("load");


                }
                swipeRefresh();
            }

        });
    }


    public class NewsAsyncTask extends AsyncTask<String,List<News>,List<News>>
    {


        private List<News> getArticleByNoFilter(int count)
        {

            String noFilterUrl = apiUrl + "articles?apikey=" + apiKey + "&$top=" +count;

            httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(noFilterUrl);
            news = new ArrayList<>();

            if(!response.isEmpty())
            {
                try
                {
                    jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonFiles = jsonObject.getJSONArray("Files");
                        article = new News();
                        article.setDescription(jsonObject.getString("Description"));
                        article.setTitle(jsonObject.getString("Title"));
                        article.setUrl(jsonObject.getString("Url"));

                        if(jsonFiles.length() != 0)
                        {
                            JSONObject files = jsonFiles.getJSONObject(0);

                            article.setNewsImage(files.getString("FileUrl"));
                        }

                        news.add(article);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return news;

        }

        private List<News> getMoreArticle(String filter,String counter)
        {
            news = new ArrayList<>();

            httpHandler = new HttpHandler();
            String moreFilteringUrl = apiUrl +"articles?apikey=" + apiKey + "&$top="+counter+"&$filter=Path eq '/" + filter + "/'";;
            String query = httpHandler.makeServiceCall(moreFilteringUrl);



            if(!query.isEmpty())
            {
                try
                {
                    jsonArray = new JSONArray(query);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonFiles = jsonObject.getJSONArray("Files");
                        article = new News();
                        article.setDescription(jsonObject.getString("Description"));
                        article.setTitle(jsonObject.getString("Title"));
                        article.setUrl(jsonObject.getString("Url"));
                        JSONObject files = jsonFiles.getJSONObject(0);
                        if(files.length() != 0)
                        {
                            article.setNewsImage(files.getString("FileUrl"));
                        }

                        news.add(article);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }


            return news;

        }

        private List<News> getFilteredList(String filter)
        {

            news = new ArrayList<>();
            String filteringUrl = apiUrl +"articles?apikey=" + apiKey + "&$top=10&$filter=Path eq '/" + filter + "/'";

            httpHandler = new HttpHandler() ;
            String makeCallStringQuery = httpHandler.makeServiceCall(filteringUrl);
            if(!makeCallStringQuery.isEmpty())
            {
                try
                {
                    jsonArray = new JSONArray(makeCallStringQuery);
                    for(int i=0;i<jsonArray.length();i++)
                    {

                        jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonFiles = jsonObject.getJSONArray("Files");
                        article = new News();
                        article.setDescription(jsonObject.getString("Description"));
                        article.setTitle(jsonObject.getString("Title"));
                        article.setUrl(jsonObject.getString("Url"));

                        if(jsonFiles.length() != 0)
                        {
                            JSONObject files = jsonFiles.getJSONObject(0);
                            article.setNewsImage(files.getString("FileUrl"));
                        }

                        news.add(article);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            return news;
        }

        @Override
        protected List<News> doInBackground(String... filters)
        {



            if(!filters[0].isEmpty() && filters[0].equals("first"))
            {
                return getFilteredList(filters[1]);
            }
            else if(filters[0].equals("last"))
            {



                return getMoreArticle(filters[1],filters[2]);
            }

            else if(filters[0].equals("noFilter"))
            {

                return getArticleByNoFilter(Integer.parseInt(filters[1]));

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<News> news)
        {
            super.onPostExecute(news);


        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


        }
    }

    private List<News> getFirstDataNoFilter() throws ExecutionException, InterruptedException
    {
        List<News> data = new NewsAsyncTask().execute(new String[]{"noFilter","10"}).get();
        return data;

    }
    private List<News> getFirstData(String query) throws ExecutionException, InterruptedException
    {

        List<News> news =new NewsAsyncTask().execute(new String[]{"first",query}).get();


        return news;
    }
}

