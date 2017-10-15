package burakcanbulbul.com.hurriyetapiprojesi.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import burakcanbulbul.com.hurriyetapiprojesi.Adapters.NewsAdapter;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.HurriyetApiClientInstance;
import burakcanbulbul.com.hurriyetapiprojesi.Classes.NewsModel;
import burakcanbulbul.com.hurriyetapiprojesi.Interfaces.HurriyetApiService;
import burakcanbulbul.com.hurriyetapiprojesi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitNewsActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private List<NewsModel> newsModels;
    private NewsAdapter newsAdapter;
    private MaterialSearchView searchView;
    private Call<NewsModel []> retrieveData;
    private HurriyetApiService hurriyetApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_retrofit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.purpleBar)));

        initViews();
        hideFloatingActionButton();
        retrieveNewsFromHurriyet();
        doSomeSearch();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main_retrofit, menu);

        MenuItem item = menu.findItem(R.id.action_search_retrofit);
        searchView.setMenuItem(item);

        return true;
    }

    private void hideFloatingActionButton()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
            public boolean onQueryTextSubmit(final String query) {
                 hurriyetApiService = HurriyetApiClientInstance.newClientInstance().create(HurriyetApiService.class);

                if(query.equalsIgnoreCase("gündem") ^ query.equalsIgnoreCase("gundem"))
                {
                    retrieveData = hurriyetApiService.retrieveJournalNews();

                    Toast.makeText(RetrofitNewsActivity.this, query.toUpperCase()+" Haberleri Getiriliyor", Toast.LENGTH_SHORT).show();
                }

                else if(query.equalsIgnoreCase("ekonomi"))
                {
                    retrieveData = hurriyetApiService.retrieveEconomyNews();
                    Toast.makeText(RetrofitNewsActivity.this, query.toUpperCase()+" Haberleri Getiriliyor", Toast.LENGTH_SHORT).show();
                }

                else if(query.equalsIgnoreCase("spor"))
                {

                    retrieveData = hurriyetApiService.retrieveSportNews();
                    Toast.makeText(RetrofitNewsActivity.this, query.toUpperCase()+" Haberleri Getiriliyor", Toast.LENGTH_SHORT).show();

                }

                else
                {
                    retrieveData = hurriyetApiService.retrieveNoFilteringNews();

                }


                retrieveData.enqueue(new Callback<NewsModel[]>()
                {
                    @Override
                    public void onResponse(Call<NewsModel[]> call, Response<NewsModel[]> response)
                    {


                        newsModels =  Arrays.asList(response.body());
                        newsAdapter = new NewsAdapter(newsModels,RetrofitNewsActivity.this);
                        recyclerView.setAdapter(newsAdapter);
                    }

                    @Override
                    public void onFailure(Call<NewsModel[]> call, Throwable t)
                    {
                        Log.d("Error", t.getMessage());
                    }
                });
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
                //Do some magic
            }

            @Override
            public void onSearchViewClosed()
            {
                //Do some magic
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Toast.makeText(this, "Giriş Sayfasına Yönleniyorsunuz...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RetrofitNewsActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initViews()
    {
        searchView =  (MaterialSearchView) findViewById(R.id.search_view_retrofit);
        searchView.setVoiceSearch(true);
        searchView.setSuggestions(new String[]{"gündem","ekonomi","spor"});
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        newsModels = new ArrayList<>();

    }

    private void retrieveNewsFromHurriyet()
    {
        hurriyetApiService = HurriyetApiClientInstance.newClientInstance().create(HurriyetApiService.class);
        retrieveData = hurriyetApiService.retrieveNoFilteringNews();
        retrieveData.enqueue(new Callback<NewsModel[]>()
        {
            @Override
            public void onResponse(Call<NewsModel[]> call, Response<NewsModel[]> response)
            {


                newsModels =  Arrays.asList(response.body());
                Toast.makeText(RetrofitNewsActivity.this, "Haberler Getiriliyor...", Toast.LENGTH_LONG).show();
                newsAdapter = new NewsAdapter(newsModels,RetrofitNewsActivity.this);
                recyclerView.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<NewsModel[]> call, Throwable t)
            {
                Log.d("Error", t.getMessage());
            }
        });
    }



}
