package ru.maximen.copybook;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.Arrays;

import ru.maximen.copybook.fragments.pager.FragmentReminderEditor;
import ru.maximen.copybook.fragments.pager.FragmentTextEditor;
import ru.maximen.copybook.fragments.pager.PagerAdapter;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private FragmentTextEditor fragmentTextEditor;
    private FragmentReminderEditor fragmentReminderEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        this.toolbar = findViewById(R.id.toolbar);
        this.viewPager = findViewById(R.id.viewPager);
        this.toolbar.setTitle("Редактор");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        configureStatusBar();
        this.fragmentTextEditor = new FragmentTextEditor();
        this.fragmentReminderEditor = new FragmentReminderEditor();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Arrays.asList(
                fragmentTextEditor, fragmentReminderEditor));
        viewPager.setAdapter(pagerAdapter);
    }

    private void configureStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBar));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        close();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        finish();
    }
}
