package com.e2p.mydentart.activites;

import static com.e2p.mydentart.helpers.ConstantConfig.ALL_CLIENTS_BALANCES;
import static com.e2p.mydentart.helpers.ConstantConfig.CLIENT_BALANCE_DETAILS;
import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_CLIENT_BALANCE;
import static com.e2p.mydentart.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.e2p.mydentart.R;
import com.e2p.mydentart.adapters.ClientBalanceAdapter;
import com.e2p.mydentart.adapters.DetailBalanceAdapter;
import com.e2p.mydentart.helpers.MySettings;
import com.e2p.mydentart.models.BalanceClientDetail;
import com.e2p.mydentart.retrofit.RetrofitClient;
import com.e2p.mydentart.retrofit.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceDetailsActivity extends AppCompatActivity {

    private static final String TAG = "B_DETAILS_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_details);

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
        getMenuInflater().inflate(R.menu.balance_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadeClientBalace();
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

            rvList = (RecyclerView) findViewById(R.id.rv_detail);
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

            String _Title = (SELECTED_CLIENT_BALANCE != null)
                    ? SELECTED_CLIENT_BALANCE.getName()
                    : "";

            setSupportActionBar(toolbar);
            //getSupportActionBar().setTitle(AB_TITLE);
            getSupportActionBar().setTitle(_Title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            btnEmptyViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loadeClientBalace();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            loadeClientBalace();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void loadeClientBalace() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rvList.setVisibility(View.GONE);

        Integer _ClientID = (SELECTED_CLIENT_BALANCE != null)
                ? SELECTED_CLIENT_BALANCE.getId()
                : -1;

        String URL = "Client/GetDetailBalanceClient?";
        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<BalanceClientDetail>> apiCall = service.getClientBalanceDetailsQuery(URL, _ClientID);

        apiCall.enqueue(new Callback<ArrayList<BalanceClientDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<BalanceClientDetail>> call, Response<ArrayList<BalanceClientDetail>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {

                    CLIENT_BALANCE_DETAILS = response.body();

                    DetailBalanceAdapter _Adapter = new DetailBalanceAdapter(BalanceDetailsActivity.this, CLIENT_BALANCE_DETAILS);
                    rvList.setAdapter(_Adapter);
                    rvList.setVisibility(View.VISIBLE);

                } else {
                    emptyListView.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BalanceClientDetail>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
            }
        });
    }


}