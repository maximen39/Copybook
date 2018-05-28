package ru.maximen.copybook.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.concurrent.ExecutionException;

import ru.maximen.copybook.ListActivity;
import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.StaticVariables;

public class DrawerFactory implements Drawer.OnDrawerItemClickListener {

    private final Activity activity;
    private final Toolbar toolbar;
    private Drawer drawer;

    public DrawerFactory(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
    }

    public Drawer initDrawer() {
        this.drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(getItems())
                .buildForFragment();
        return this.drawer;
    }

    private IDrawerItem[] getItems() {
        return new IDrawerItem[]{
                new DividerDrawerItem(),
                notes(),
                reminders(),
                new DividerDrawerItem(),
                tags(),
                new DividerDrawerItem(),
                archive(),
                trash(),
                new DividerDrawerItem(),
                settings(),
                about(),
                new DividerDrawerItem(),
                logout()
        };
    }

    private SecondaryDrawerItem notes() {
        return new SecondaryDrawerItem()
                .withIdentifier(1)
                .withSetSelected(true)
                .withName(R.string.drawer_item_notes)
                .withIcon(FontAwesome.Icon.faw_lightbulb)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem reminders() {
        return new SecondaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.drawer_item_reminders)
                .withIcon(FontAwesome.Icon.faw_clock)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem tags() {
        return new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.drawer_item_tags)
                .withIcon(FontAwesome.Icon.faw_bookmark)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem archive() {
        return new SecondaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.drawer_item_archive)
                .withIcon(FontAwesome.Icon.faw_archive)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem trash() {
        return new SecondaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.drawer_item_trash)
                .withIcon(FontAwesome.Icon.faw_trash)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem settings() {
        return new SecondaryDrawerItem()
                .withIdentifier(6)
                .withName(R.string.drawer_item_settings)
                .withIcon(FontAwesome.Icon.faw_cog)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem about() {
        return new SecondaryDrawerItem()
                .withIdentifier(7)
                .withName(R.string.drawer_item_about)
                .withIcon(FontAwesome.Icon.faw_info)
                .withOnDrawerItemClickListener(this);
    }

    private SecondaryDrawerItem logout() {
        return new SecondaryDrawerItem()
                .withIdentifier(8)
                .withName(R.string.drawer_item_logout)
                .withIcon(FontAwesome.Icon.faw_info)
                .withOnDrawerItemClickListener(this);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (!StaticVariables.authorized) {
            drawerItem.withSetSelected(false);
            return false;
        }
        ListActivity listActivity = (ListActivity) this.activity;
        int id = (int) drawerItem.getIdentifier();
        switch (id) {
            case 1: {
                if (!listActivity.getFragmentNote().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentNote(), false);
                    this.toolbar.setTitle(R.string.app_name);
                }
                break;
            }
            case 2: {
                if (!listActivity.getFragmentReminder().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentReminder(), false);
                    this.toolbar.setTitle(R.string.drawer_item_reminders);
                }
                break;
            }
            case 3: {
                if (!listActivity.getFragmentTag().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentTag(), false);
                    this.toolbar.setTitle(R.string.drawer_item_tags);
                }
                break;
            }
            case 4: {
                if (!listActivity.getFragmentArchive().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentArchive(), false);
                    this.toolbar.setTitle(R.string.drawer_item_archive);
                }
                break;
            }
            case 5: {
                if (!listActivity.getFragmentTrash().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentTrash(), false);
                    this.toolbar.setTitle(R.string.drawer_item_trash);
                }
                break;
            }
            case 6: {
                if (!listActivity.getFragmentSettings().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentSettings(), false);
                    this.toolbar.setTitle(R.string.drawer_item_settings);
                }
                break;
            }
            case 7: {
                if (!listActivity.getFragmentAbout().isVisible()) {
                    listActivity.replaceFragment(listActivity.getFragmentAbout(), false);
                    this.toolbar.setTitle(R.string.drawer_item_about);
                }
                break;
            }
            case 8: {
                try {
                    if (listActivity.runLogoutOAuthTask()) {
                        SharedPreferences preferences = listActivity.getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

                        if (preferences.contains(MainActivity.APP_PREFERENCES_TOKEN) && preferences.contains(MainActivity.APP_PREFERENCES_NAME)) {
                            preferences.edit()
                                    .remove(MainActivity.APP_PREFERENCES_TOKEN)
                                    .remove(MainActivity.APP_PREFERENCES_NAME).apply();
                        }
                        Intent intent = new Intent(listActivity, MainActivity.class);
                        listActivity.startActivity(intent);
                        listActivity.finish();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return false;
    }
}
