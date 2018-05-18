package ru.maximen.copybook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import ru.maximen.copybook.drawer.DrawerFactory;
import ru.maximen.copybook.fragments.MainFragment;
import ru.maximen.copybook.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment mainFragment;
    private Fragment settingsFragment;

    private Toolbar toolbar;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViews();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            DrawerFactory drawerFactory = new DrawerFactory(this);
            drawer = drawerFactory.initDrawer();
        }
    }

    private void initFragments() {
        this.settingsFragment = new SettingsFragment();
        this.mainFragment = new MainFragment().setMainActivity(this);
        startMainFragment(mainFragment);
    }

    private void startMainFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left, R.animator.slide_in_right
                )
                .add(R.id.fragmentContent, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left, R.animator.slide_in_right,
                        R.animator.slide_in_left_reverse, R.animator.slide_in_right_reverse
                ).replace(R.id.fragmentContent, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void initViews() {
        this.toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if (getMainFragment().isVisible()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public Fragment getMainFragment() {
        return mainFragment;
    }

    public Fragment getSettingsFragment() {
        return settingsFragment;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
