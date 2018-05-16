package ru.maximen.copybook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class FactoryDrawer {

    private Drawer drawer;
    private Toolbar toolbar;
    private Activity activity;

    public FactoryDrawer(Toolbar toolbar, Activity activity) {
        this.toolbar = toolbar;
        this.activity = activity;
    }

    public Drawer initDrawer() {
        this.drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(getItems())
                .build();
        return this.drawer;
    }

    @NonNull
    private IDrawerItem[] getItems() {
        return new IDrawerItem[]{
                getHomeButton(),
                new SectionDrawerItem().withName(R.string.drawer_item_settings),
                getConfigButton(),
                getHelpButton(),
                new DividerDrawerItem(),
                getAllRecords()
        };
    }

    private SecondaryDrawerItem getConfigButton() {
        return new SecondaryDrawerItem()
                .withName(R.string.drawer_item_config)
                .withIcon(FontAwesome.Icon.faw_cog)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (!(activity instanceof SettingsActivity)) {
                            Intent intent = new Intent(activity.getApplicationContext(),
                                    SettingsActivity.class);
                            activity.startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });
    }

    private SecondaryDrawerItem getHomeButton() {
        return new SecondaryDrawerItem()
                .withName(R.string.drawer_item_home)
                .withIcon(FontAwesome.Icon.faw_home)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (!(activity instanceof MainActivity)) {
                            Intent intent = new Intent(activity.getApplicationContext(),
                                    MainActivity.class);
                            activity.startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });
    }

    private SecondaryDrawerItem getHelpButton() {
        return new SecondaryDrawerItem()
                .withName(R.string.drawer_item_help)
                .withIcon(FontAwesome.Icon.faw_question)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://copybook.ru/help"));
                        activity.startActivity(browserIntent);
                        return false;
                    }
                });
    }

    private SecondaryDrawerItem getAllRecords() {
        return new SecondaryDrawerItem()
                .withName(R.string.drawer_item_all)
                .withIcon(FontAwesome.Icon.faw_list)
                .withBadge("73");
    }
}
