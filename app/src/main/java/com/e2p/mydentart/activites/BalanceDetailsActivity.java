package com.e2p.mydentart.activites;

import static com.e2p.mydentart.helpers.ConstantConfig.CLIENT_BALANCE_DETAILS;
import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_CLIENT_BALANCE;
import static com.e2p.mydentart.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

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

    private NestedScrollView nsvMain;
    private TableLayout tlCharges;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;

    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

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

            nsvMain = (NestedScrollView) findViewById(R.id.nsv_main_container);
            tlCharges = (TableLayout) findViewById(R.id.tl_balance_details);
            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);
            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

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

            formatter = NumberFormat.getCurrencyInstance(Locale.US);
            decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
            formatter.setMinimumFractionDigits(3);

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
        nsvMain.setVisibility(View.GONE);

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
                    nsvMain.setVisibility(View.VISIBLE);
                    CLIENT_BALANCE_DETAILS = response.body();

                    for (BalanceClientDetail balance : CLIENT_BALANCE_DETAILS) {
                        addRow(balance, tlCharges);
                    }

                } else {
                    emptyListView.setVisibility(View.VISIBLE);
                    nsvMain.setVisibility(View.GONE);
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BalanceClientDetail>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                nsvMain.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addRow(BalanceClientDetail obj, TableLayout tab) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.item_table_balance_detail_row, null);

        AppCompatTextView tvDate = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_date);
        AppCompatTextView tvBilling = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_billing);
        AppCompatTextView tvSettlement = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_settlement);
        AppCompatTextView tvReference = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_reference);
        AppCompatTextView tvBalance = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_balance);

        tvDate.setText(obj.getDate());
        tvBilling.setText(formatter.format(obj.getFacturation()));
        tvSettlement.setText(formatter.format(obj.getReglement()));
        tvReference.setText(obj.getReference());
        tvBalance.setText(formatter.format(obj.getSolde()));

        TableRow tr = new TableRow(getApplicationContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        tr.addView(mView);

        tab.addView(tr);
    }

}