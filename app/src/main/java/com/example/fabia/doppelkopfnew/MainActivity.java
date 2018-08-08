package com.example.fabia.doppelkopfnew;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.fabia.doppelkopfnew.myFragments.AboutUsFragment;
import com.example.fabia.doppelkopfnew.myFragments.GameListFragment;
import com.example.fabia.doppelkopfnew.myFragments.LastGameFragment;
import com.example.fabia.doppelkopfnew.myFragments.PlayersFragment;
import com.example.fabia.doppelkopfnew.myFragments.SettingsFragment;
import com.example.fabia.doppelkopfnew.myFragments.TopsAndFlopsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private PlayerController playerController;
/*
    private LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureMenu();

        //Player Controller erstellen und wenn vorhanden Daten aus Datei einlesen
        //Wenn keine Datei vorhanden wird ein bsp Datensatz erstellt
        playerController = new PlayerController(new ArrayList<Player>());
        playerController.readFromJSON(this);

        //Deletes the Pictures of Players who are nolonger in PlayerList
        PictureStorageHelper.cleanUpStorage(playerController.getPlayerList());




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerController.readFromJSON(this);
        //Deletes the Pictures of Players who are nolonger in PlayerList
        PictureStorageHelper.cleanUpStorage(playerController.getPlayerList());
    }

    private void configureMenu(){
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myNewMainDrawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    //Man muss noch fuer einzelne MenuItems das OnClick event in der Ressource festlegen
    public boolean onMenuItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.nav_tops_flops:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new TopsAndFlopsFragment()).commit();
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Tops und Flops");
                break;

            case R.id.nav_profiles:
                //Fragment init und per Manager laden
                PlayersFragment pFrag = new PlayersFragment();
                pFrag.setContext(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,pFrag).commit();
                //Damit es schön aussieht den MenuDrawer schließen
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Profile");
                break;

            case R.id.nav_lastgame:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new LastGameFragment()).commit();
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Letztes Spiel");
                break;

            case R.id.nav_games_history:
                GameListFragment gameFrag = new GameListFragment();
                gameFrag.setContext(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,gameFrag).commit();
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Spiele");
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new SettingsFragment()).commit();
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Einstellungen");
                break;

            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new AboutUsFragment()).commit();
                mDrawerLayout.closeDrawers();
                //Titel in ActionBar ändern
                setTitle("Über uns");
                break;
        }
        return true;
    }
}
