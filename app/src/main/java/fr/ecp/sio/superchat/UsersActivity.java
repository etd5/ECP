package fr.ecp.sio.superchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class UsersActivity extends ActionBarActivity {

    private static final int REQUEST_LOGIN_FOR_FOLLOW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.follow) {
            if (AccountManager.isConnected(UsersActivity.this)) {
                startActivity(new Intent(this, FollowActivity.class));
            } else {
                LoginFragment fragment = new LoginFragment();
                fragment.setTargetFragment(fragment, REQUEST_LOGIN_FOR_FOLLOW);
                fragment.show(getSupportFragmentManager(), "login_dialog");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loginSuccessfull() {
//        super.onActivityResult(requestCode, resultCode, data);
            startActivity(new Intent(this, FollowActivity.class));
    }

}
