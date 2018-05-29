package ru.maximen.copybook.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ru.maximen.copybook.ListActivity;
import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.StaticVariables;
import ru.maximen.copybook.dto.OAuthToken;

public class FragmentSignin extends AbstractAsyncFragment {

    private TextView error;
    private EditText login;
    private EditText password;
    private Button signin;
    private Button footerButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, null);
        this.error = view.findViewById(R.id.error);
        this.login = view.findViewById(R.id.login);
        this.password = view.findViewById(R.id.password);
        this.signin = view.findViewById(R.id.signin);
        this.footerButton = view.findViewById(R.id.footerButton);
        this.footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(mainActivity.getFragmentSignup(), false);
            }
        });
        this.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                new LoginOAuthTask().execute();
            }
        });
        return view;
    }

    private void setError(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                error.setText(text);
            }
        });
    }

    private class LoginOAuthTask extends AsyncTask<Void, Void, OAuthToken> {

        private String username;
        private String password;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            this.username = FragmentSignin.this.login.getText().toString().trim();
            this.password = FragmentSignin.this.password.getText().toString();
        }

        @Override
        protected OAuthToken doInBackground(Void... params) {
            String url = StaticVariables.OAUTH_URL + "token";
            HttpAuthentication authentication = new HttpBasicAuthentication(StaticVariables.CLIENT_ID, StaticVariables.SECRET);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("username", username);
            body.add("password", password);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authentication);
            HttpEntity httpEntity = new HttpEntity<>(body, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                ResponseEntity<OAuthToken> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OAuthToken.class);
                return response.getBody();
            } catch (HttpClientErrorException ex) {
                dismissProgressDialog();
                if (ex.getStatusCode().value() == 400) {
                    setError("Неправильный логин или пароль!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OAuthToken result) {
            dismissProgressDialog();
            if (result != null) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString(MainActivity.APP_PREFERENCES_TOKEN, result.getAccess_token())
                        .putString(MainActivity.APP_PREFERENCES_NAME, username).apply();
                StaticVariables.authorized = true;

                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("token_key", result.getAccess_token());
                intent.putExtra("username", username);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
}
