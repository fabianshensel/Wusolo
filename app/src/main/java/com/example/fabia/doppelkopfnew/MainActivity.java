package com.example.fabia.doppelkopfnew;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myDrawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void startAktivityMain(View v) {
        startActivity(new Intent(MainActivity.this,MenuActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void onClickButton(MenuItem item){
        System.out.println("Okay");
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
            case R.id.nav_about_us:
                AboutUsActivity aboutUsActivity = new AboutUsActivity();
                finish();
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                finish();
                aboutUsActivity.getMeOut();
                return true;

            case R.id.nav_profiles:

                Intent myIntent = new Intent(MainActivity.this,PlayerProfilActivity.class);
                startActivity(myIntent);

                return true;
            default:
                return true;
        }
    }
}
