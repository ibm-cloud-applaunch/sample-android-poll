package com.ibm.applaunch.sample_android_poll;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ibm.mobile.applaunch.android.api.AppLaunch;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnTakePoll = findViewById(R.id.btn_take_poll);
        btnTakePoll.setVisibility(View.GONE);

        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3d70b2));

        try {
            if (AppLaunch.getInstance().isFeatureEnabled("_bgx03ul00")) {
                String popupTitle = AppLaunch.getInstance().getPropertyOfFeature("_bgx03ul00", "_2mp0k7mwb");
                String positiveAction = AppLaunch.getInstance().getPropertyOfFeature("_bgx03ul00", "_bo94pfygy");
                String negativeAction = AppLaunch.getInstance().getPropertyOfFeature("_bgx03ul00", "_85j65j9sf");

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(popupTitle);

                builder.setPositiveButton(positiveAction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMetrics("_qfg7s66e0");
                    }
                });
                builder.setNegativeButton(negativeAction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMetrics("_faeqzmlgi");
                    }
                });
                alertDialog = builder.create();

                btnTakePoll.setVisibility(View.VISIBLE);
                btnTakePoll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.show();
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void sendMetrics(String metricCode) {
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add(metricCode);
        AppLaunch.getInstance().sendMetrics(metrics);
        alertDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        logOutUser();
        return true;
    }

    public void logOutUser() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
