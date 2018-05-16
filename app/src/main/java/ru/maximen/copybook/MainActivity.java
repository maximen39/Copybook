package ru.maximen.copybook;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView mainListViwe;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            FactoryDrawer factoryDrawer = new FactoryDrawer(toolbar, this);
            drawer = factoryDrawer.initDrawer();
        }

        mainListViwe.setAdapter(new CustomListViewAdapter(this,
                new String[]{"Мои цели", "Ссылки на интересные статьи", "Цитаты", "Истории", "Закладки", "Названия книг"},
                new String[]{"Цели: 25", "Ссылок: 12", "Цитат: 5", "Историй: 10", "Закладок: 6", "Названия книг: 15"}));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewlistActivity.class);
                startActivityForResult(intent, 1);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void initViews() {
        this.fab = findViewById(R.id.fab);
        this.toolbar = findViewById(R.id.toolbar);
        this.mainListViwe = findViewById(R.id.mainListView);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1: {
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
