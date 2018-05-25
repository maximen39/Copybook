package ru.maximen.copybook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.drawer.DrawerFactory;
import ru.maximen.copybook.fragments.BaseCopybookFragment;
import ru.maximen.copybook.fragments.ListFragment;
import ru.maximen.copybook.service.CopybookService;
import ru.maximen.copybook.service.DatabaseCopybookService;

public class MainActivity extends AppCompatActivity {

    private BaseCopybookFragment currentFragment;
    private BaseCopybookFragment mainFragment;
    private CopybookService localService;

    private Toolbar toolbar;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.localService = new DatabaseCopybookService(this);
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
        Section section = new Section();
        section.id(-1).title("CopyBook");
        section.addAllItems(Utils.getMainSectionList(getLocalService()));

        ListFragment fragment = new ListFragment();
        fragment.section(section).mainActivity(this);

        setCurrentFragment(fragment);
        this.mainFragment = fragment;
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left, R.animator.slide_in_right,
                        R.animator.slide_in_left_reverse, R.animator.slide_in_right_reverse
                ).replace(R.id.fragmentContent, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    private void initViews() {
        this.toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        } else if (getCurrentFragment() == getMainFragment()) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public BaseCopybookFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseCopybookFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public BaseCopybookFragment getMainFragment() {
        return mainFragment;
    }

    public CopybookService getLocalService() {
        return localService;
    }
}
