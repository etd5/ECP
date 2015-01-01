package fr.ecp.sio.superchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class FollowActivity extends ActionBarActivity {

    private int FOLLOW_AVERTISSEUR_SORTIE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_follow_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*Cette fois, il n'y a que les préférences.
          Si on se déconnecte, il faut que l'activité se ferme.
         */
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), FOLLOW_AVERTISSEUR_SORTIE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == FOLLOW_AVERTISSEUR_SORTIE)
        {
            this.finish();
        }
    }
}
