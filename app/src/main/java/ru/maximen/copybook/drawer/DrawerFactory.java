package ru.maximen.copybook.drawer;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;

public class DrawerFactory {

    private MainActivity mainActivity;
    private Drawer drawer;

    public DrawerFactory(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Drawer initDrawer() {
        this.drawer = new DrawerBuilder()
                .withActivity(mainActivity)
                .withToolbar(mainActivity.getToolbar())
                .withActionBarDrawerToggle(true)
                .addDrawerItems(getItems())
                .buildForFragment();
        return this.drawer;
    }

    private IDrawerItem[] getItems() {
        return new IDrawerItem[]{
                getHomeButton(),
                new SectionDrawerItem().withName(R.string.drawer_item_settings),
                getConfigButton(),
                getHelpButton(),
                new DividerDrawerItem()
        };
    }

    private SecondaryDrawerItem getConfigButton() {
        return new SecondaryDrawerItem()
                .withName(R.string.drawer_item_config)
                .withIcon(FontAwesome.Icon.faw_cog)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        /*if (!mainActivity.getSettingsFragment().isVisible()) {
                            mainActivity.replaceFragment(mainActivity.getSettingsFragment());
                            if (drawer.isDrawerOpen()) {
                                drawer.closeDrawer();
                            }
                        }*/
                        return true;
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
                        if (mainActivity.getCurrentFragment() != mainActivity.getMainFragment()) {
                            mainActivity.replaceFragment(mainActivity.getMainFragment());
                            if (drawer.isDrawerOpen()) {
                                drawer.closeDrawer();
                            }
                        }
                        return true;
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
                        mainActivity.startActivity(browserIntent);
                        return false;
                    }
                });
    }
}
