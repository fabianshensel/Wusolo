package com.example.fabia.doppelkopfnew;

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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                break;

            case R.id.nav_profiles:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new PlayersFragment()).commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_lastgame:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new LastGameFragment()).commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_games_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new GameListFragment()).commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new SettingsFragment()).commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.myFrameLayout,new AboutUsFragment()).commit();
                mDrawerLayout.closeDrawers();
                break;
            default:
                return true;
        }
        return true;
    }
}
