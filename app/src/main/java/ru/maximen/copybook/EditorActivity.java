package ru.maximen.copybook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Date;

import ru.maximen.copybook.fragments.pager.FragmentReminderEditor;
import ru.maximen.copybook.fragments.pager.FragmentTextEditor;
import ru.maximen.copybook.fragments.pager.PagerAdapter;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private EditText title;
    private FragmentTextEditor fragmentTextEditor;
    private FragmentReminderEditor fragmentReminderEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        this.toolbar = findViewById(R.id.toolbar);
        this.title = findViewById(R.id.editText);
        this.viewPager = findViewById(R.id.viewPager);
        this.toolbar.setTitle("Редактор");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        configureStatusBar();
        Intent intent = getIntent();
        this.fragmentTextEditor = new FragmentTextEditor();
        this.fragmentReminderEditor = new FragmentReminderEditor();

        if (intent.hasExtra("noteId")) {
            this.title.setText(intent.getStringExtra("title"));
            this.fragmentTextEditor.setText(intent.getStringExtra("content"));
            if (intent.hasExtra("reminder")) {
                Date date = new Date(intent.getLongExtra("reminder", (long) 0));
                if (!date.before(new Date())) {
                    fragmentReminderEditor.setmUserHasReminder(true);
                    fragmentReminderEditor.setRemindMe(date);
                }
            }
        }

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
        Intent intent = new Intent();
        if (getIntent().hasExtra("noteId")) {
            if (getIntent().getStringExtra("title").equals(this.title.getText().toString()) &&
                    getIntent().getStringExtra("content").equals(this.fragmentTextEditor.getEditText().getText().toString())
                    && !isReminderEdited(getIntent().getLongExtra("reminder", 0))) {
                setResult(RESULT_CANCELED, intent);
            } else {
                intent.putExtra("title", this.title.getText().toString());
                intent.putExtra("content", this.fragmentTextEditor.getEditText().getText().toString());
                intent.putExtra("noteId", getIntent().getIntExtra("noteId", -1));
                if (getReminder() != null) {
                    intent.putExtra("reminder", getReminder().getTime());
                }
                setResult(RESULT_OK, intent);
            }
        } else {
            if (this.title.getText().toString().length() == 0) {
                setResult(RESULT_CANCELED, intent);
            } else {
                intent.putExtra("title", this.title.getText().toString());
                intent.putExtra("content", this.fragmentTextEditor.getEditText().getText().toString());
                if (getReminder() != null) {
                    intent.putExtra("reminder", getReminder().getTime());
                }
                setResult(RESULT_OK, intent);
            }
        }
        finish();
    }

    private Date getReminder() {
        if (fragmentReminderEditor.ismUserHasReminder() && fragmentReminderEditor.getmUserReminderDate() != null && fragmentReminderEditor.getmUserReminderDate().after(new Date())) {
            return fragmentReminderEditor.getmUserReminderDate();
        }
        return null;
    }

    private boolean isReminderEdited(long reminder) {
        return reminder != 0 && getReminder() != null && fragmentReminderEditor.getmUserReminderDate().getTime() != reminder || reminder == 0 && getReminder() != null || reminder != 0 && getReminder() == null;
    }
}
