package ru.maximen.copybook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

import ru.maximen.copybook.dto.OAuthCheck;
import ru.maximen.copybook.fragments.FragmentSignin;
import ru.maximen.copybook.fragments.FragmentSignup;

public class MainActivity extends AbstractAsyncActivity {
    public static final String APP_PREFERENCES = "tokeStore";
    public static final String APP_PREFERENCES_TOKEN = "token_key";
    public static final String APP_PREFERENCES_NAME = "username";

    private Fragment fragmentSignin;
    private Fragment fragmentSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();

        if (sharedPreferences.contains(APP_PREFERENCES_TOKEN) && sharedPreferences.contains(APP_PREFERENCES_NAME)) {
            OAuthCheck oAuthCheck = null;
            try {
                oAuthCheck = new CheckOAuthTask().execute(
                        sharedPreferences.getString(APP_PREFERENCES_TOKEN, "nullable"))
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (oAuthCheck != null && oAuthCheck.isActive() && oAuthCheck.getUser_name().equalsIgnoreCase(
                    sharedPreferences.getString(APP_PREFERENCES_NAME, ""))) {

                StaticVariables.authorized = true;
                Intent intent = new Intent(this, ListActivity.class);
                intent.putExtra("token_key", oAuthCheck.access_token());
                intent.putExtra("username", oAuthCheck.getUser_name());
                startActivity(intent);
                finish();
                return;
            } else {
                StaticVariables.authorized = false;
            }
        }
        replaceFragment(fragmentSignin, false);
    }

    private void initFragments() {
        this.fragmentSignin = new FragmentSignin();
        this.fragmentSignup = new FragmentSignup();
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

    public Fragment getFragmentSignin() {
        return fragmentSignin;
    }

    public Fragment getFragmentSignup() {
        return fragmentSignup;
    }

    private class CheckOAuthTask extends AsyncTask<String, Void, OAuthCheck> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected OAuthCheck doInBackground(String... params) {
            String url = StaticVariables.OAUTH_URL + "check_token";
            HttpAuthentication authentication = new HttpBasicAuthentication("copybookapp", "ASDKLnsdoi324");
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", params[0]);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authentication);
            HttpEntity httpEntity = new HttpEntity<>(body, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                ResponseEntity<OAuthCheck> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OAuthCheck.class);
                return response.getBody().access_token(params[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OAuthCheck result) {
            dismissProgressDialog();
        }
    }
}
