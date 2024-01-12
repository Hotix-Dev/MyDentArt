package com.e2p.mydentart.activites;

import static com.e2p.mydentart.helpers.ConstantConfig.CURENT_CLIENT;
import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_CLIENT;
import static com.e2p.mydentart.helpers.Utils.showSnackbar;
import static com.e2p.mydentart.helpers.ConstantConfig.AB_TITLE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e2p.mydentart.R;
import com.e2p.mydentart.adapters.DashbordGridAdapter;
import com.e2p.mydentart.helpers.MySettings;
import com.e2p.mydentart.models.Client;
import com.e2p.mydentart.models.DashItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DASHBOAD_ACTIVITTY";

    private Toolbar toolbar;
    private GridView gvDashbord;
    private MySettings mySettings;

    private DashbordGridAdapter mGridAdapter;
    private ArrayList<DashItem> dashItems = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            gvDashbord.setEnabled(true);
            getSupportActionBar().setTitle((SELECTED_CLIENT != null)?SELECTED_CLIENT.getName():"");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        try {
            startExitDialog();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            startLogoutDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            gvDashbord = (GridView) findViewById(R.id.gv_dashbord);

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
            //getSupportActionBar().setTitle(getString(R.string.app_name));
            getSupportActionBar().setTitle((SELECTED_CLIENT != null)?SELECTED_CLIENT.getName():"");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

            dashItems = new ArrayList<DashItem>();
            dashItems.add(new DashItem(1, getString(R.string.menu_prix_prestations), "price_list"));
            dashItems.add(new DashItem(2, getString(R.string.menu_balance_clients), "invoice"));

            mGridAdapter = new DashbordGridAdapter(getApplicationContext(), dashItems);
            gvDashbord.setAdapter(mGridAdapter);

            gvDashbord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        gvDashbord.setEnabled(false);

                        DashItem _DashItem = dashItems.get(position);
                        AB_TITLE = _DashItem.getName();
                        Intent i = null;
                        switch (_DashItem.getId()) {

                            case 1: {
                                i = new Intent(getApplicationContext(), PrixPrestationsActivity.class);
                                startActivity(i);
                                break;
                            }

                            case 2: {
                                i = new Intent(getApplicationContext(), ClientsBalanceActivity.class);
                                startActivity(i);
                                break;
                            }
                        }

                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                        gvDashbord.setEnabled(true);
                    } finally {
                    }

                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    //This method show exit dialog.
    private void startExitDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        AppCompatButton btnYes = (AppCompatButton) mView.findViewById(R.id.btn_dialog_exit_yes);
        AppCompatButton btnCancel = (AppCompatButton) mView.findViewById(R.id.btn_dialog_exit_cancel);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //This method show logout dialog.
    private void startLogoutDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        AppCompatButton btnYes = (AppCompatButton) mView.findViewById(R.id.btn_dialog_logout_yes);
        AppCompatButton btnCancel = (AppCompatButton) mView.findViewById(R.id.btn_dialog_logout_cancel);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the LoginActivity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    /**********************************************************************************************/

}