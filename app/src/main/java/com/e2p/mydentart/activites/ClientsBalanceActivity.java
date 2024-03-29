package com.e2p.mydentart.activites;

import static com.e2p.mydentart.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.mydentart.helpers.ConstantConfig.ALL_CLIENTS_BALANCES;
import static com.e2p.mydentart.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.e2p.mydentart.R;
import com.e2p.mydentart.adapters.ClientBalanceAdapter;
import com.e2p.mydentart.adapters.PrixPrestationsAdapter;
import com.e2p.mydentart.helpers.MySettings;
import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.PrixPrestation;
import com.e2p.mydentart.retrofit.RetrofitClient;
import com.e2p.mydentart.retrofit.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsBalanceActivity extends AppCompatActivity {

    private static final String TAG = "CLIENT_BAL_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_balance);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            rvList.setEnabled(true);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clients_balance_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadeClientsBalance();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);

            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

            rvList = (RecyclerView) findViewById(R.id.rv_prix);
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(new LinearLayoutManager(this));

            progressView = (LinearLayoutCompat) findViewById(R.id.loading_view);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    private void init() {
        try {
            //settings
            mySettings = new MySettings(getApplicationContext());

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(AB_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            btnEmptyViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loadeClientsBalance();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            loadeClientsBalance();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void loadeClientsBalance() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rvList.setVisibility(View.GONE);

        String URL = "Client/GetBalanceClient";
        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<BalanceClient>> apiCall = service.getClientsBalanceQuery(URL);

        apiCall.enqueue(new Callback<ArrayList<BalanceClient>>() {
            @Override
            public void onResponse(Call<ArrayList<BalanceClient>> call, Response<ArrayList<BalanceClient>> response) {
                progressView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {

                    ALL_CLIENTS_BALANCES = response.body();

                    ClientBalanceAdapter _Adapter = new ClientBalanceAdapter(ClientsBalanceActivity.this, ALL_CLIENTS_BALANCES);
                    rvList.setAdapter(_Adapter);
                    rvList.setVisibility(View.VISIBLE);

                } else {
                    emptyListView.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BalanceClient>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
            }
        });
    }

}