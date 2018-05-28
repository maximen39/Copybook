package ru.maximen.copybook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.mikepenz.materialdrawer.Drawer;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import ru.maximen.copybook.drawer.DrawerFactory;
import ru.maximen.copybook.dto.ResponseDto;
import ru.maximen.copybook.fragments.AbstractUpdatebleFragment;
import ru.maximen.copybook.fragments.FragmentAbout;
import ru.maximen.copybook.fragments.FragmentArchive;
import ru.maximen.copybook.fragments.FragmentNote;
import ru.maximen.copybook.fragments.FragmentReminder;
import ru.maximen.copybook.fragments.FragmentSettings;
import ru.maximen.copybook.fragments.FragmentTag;
import ru.maximen.copybook.fragments.FragmentTrash;
import ru.maximen.copybook.widgets.NoteList;
import ru.maximen.copybook.widgets.NoteManager;

public class ListActivity extends AbstractAsyncActivity {

    private Drawer drawer;
    private Toolbar toolbar;
    private String token_key;
    private String username;
    private Fragment fragmentAbout;
    private Fragment fragmentSettings;
    private AbstractUpdatebleFragment fragmentArchive;
    private AbstractUpdatebleFragment fragmentNote;
    private AbstractUpdatebleFragment fragmentReminder;
    private AbstractUpdatebleFragment fragmentTag;
    private AbstractUpdatebleFragment fragmentTrash;
    private NoteManager noteManager;

    private NoteList noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.noteList = new NoteList(this);
        this.token_key = getIntent().getStringExtra("token_key");
        this.username = getIntent().getStringExtra("username");
        initFragments();
        configureStatusBar();
        initViews();
        replaceFragment(fragmentNote, false);
        this.noteManager = new NoteManager(noteList, this, getTokenKey(), Arrays.asList(
                fragmentArchive, fragmentNote, fragmentReminder, fragmentTag, fragmentTrash));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            DrawerFactory drawerFactory = new DrawerFactory(this, toolbar);
            drawer = drawerFactory.initDrawer();
        }
    }

    private void initFragments() {
        this.fragmentAbout = new FragmentAbout();
        this.fragmentArchive = new FragmentArchive();
        this.fragmentNote = new FragmentNote();
        this.fragmentReminder = new FragmentReminder();
        this.fragmentSettings = new FragmentSettings();
        this.fragmentTag = new FragmentTag();
        this.fragmentTrash = new FragmentTrash();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left, R.animator.slide_in_right,
                        R.animator.slide_in_left_reverse, R.animator.slide_in_right_reverse
                ).replace(R.id.fragmentContent, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString());
        }
        fragmentTransaction.commit();
    }

    private void configureStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBar));
        }
    }


    public boolean runLogoutOAuthTask() throws ExecutionException, InterruptedException {
        return new LogoutOAuthTask().execute(token_key).get();
    }

    public class LogoutOAuthTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String url = StaticVariables.OAUTH_URL + "revoke_token";
            HttpAuthentication authentication = new HttpBasicAuthentication("copybookapp", "ASDKLnsdoi324");
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", params[0]);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authentication);
            HttpEntity httpEntity = new HttpEntity<>(body, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, ResponseDto.class);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dismissProgressDialog();
        }
    }

    public Fragment getFragmentAbout() {
        return fragmentAbout;
    }

    public Fragment getFragmentArchive() {
        return fragmentArchive;
    }

    public Fragment getFragmentNote() {
        return fragmentNote;
    }

    public Fragment getFragmentReminder() {
        return fragmentReminder;
    }

    public Fragment getFragmentSettings() {
        return fragmentSettings;
    }

    public Fragment getFragmentTag() {
        return fragmentTag;
    }

    public Fragment getFragmentTrash() {
        return fragmentTrash;
    }

    public String getUsername() {
        return username;
    }

    public String getTokenKey() {
        return token_key;
    }

    private void initViews() {
        this.toolbar = findViewById(R.id.toolbar);
    }

    public NoteList getNoteList() {
        return noteList;
    }
}
