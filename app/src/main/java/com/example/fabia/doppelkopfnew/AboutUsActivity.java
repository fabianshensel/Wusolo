package com.example.fabia.doppelkopfnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class AboutUsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
    /*
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myDrawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
    }

    public boolean getMeOut(){
        System.out.println("Okay");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public boolean onMenuItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.nav_about_us:
                AboutUsActivity aboutUsActivity = new AboutUsActivity();
                startActivity(new Intent(AboutUsActivity.this,AboutUsActivity.class));
                aboutUsActivity.getMeOut();
                return true;
            case R.id.nav_profiles:


                return true;
            default:
                return true;
        }
    }
}
