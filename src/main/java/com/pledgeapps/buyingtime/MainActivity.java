package com.pledgeapps.buyingtime;


import android.app.AlarmManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pledgeapps.buyingtime.data.Alarms;
import com.pledgeapps.buyingtime.data.Transactions;
import com.pledgeapps.buyingtime.utils.AlarmHelper;
import com.pledgeapps.buyingtime.utils.AlarmReceiver;

public class MainActivity extends ActionBarActivity {

    private static final int ACTIVITY_ALARMS=101;
    private static final int ACTIVITY_DONATE=102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new com.pledgeapps.buyingtime.MainFragment())
                    .commit();
            Alarms.load(getApplicationContext());
            Transactions.load(getApplicationContext());
           // registerReceiver(AlarmReceiver.getCurrent(), new IntentFilter(getString(R.string.namespace)));
        }
        checkAlarm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAlarm();
    }

    private void checkAlarm()
    {
        if (AlarmHelper.getCurrent().isSounding) AlarmHelper.getCurrent().showAlert(this, Alarms.getCurrent().getNextAlarm());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_alarms:
                i = new Intent(this, AlarmsActivity.class);
                startActivityForResult(i, ACTIVITY_ALARMS);
                break;
            case R.id.action_donate:
                i = new Intent(this, DonateActivity.class);
                startActivityForResult(i, ACTIVITY_DONATE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
