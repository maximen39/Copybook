package ru.maximen.copybook.fragments;

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
import org.springframework.web.client.RestTemplate;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.StaticVariables;

public class FragmentSignup extends AbstractAsyncFragment {

    private TextView error;
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    private Button signup;
    private Button footerButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, null);
        this.error = view.findViewById(R.id.error);
        this.login = view.findViewById(R.id.login);
        this.password = view.findViewById(R.id.password);
        this.confirmPassword = view.findViewById(R.id.confirmPassword);
        this.signup = view.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                new LoginOAuthTask().execute();
            }
        });
        this.footerButton = view.findViewById(R.id.footerButton);
        this.footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(mainActivity.getFragmentSignin(), false);
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

    private class LoginOAuthTask extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;
        private String confirmPassword;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            this.username = FragmentSignup.this.login.getText().toString().trim();
            this.password = FragmentSignup.this.password.getText().toString();
            this.confirmPassword = FragmentSignup.this.confirmPassword.getText().toString();
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = StaticVariables.OAUTH_URL + "register";
            HttpAuthentication authentication = new HttpBasicAuthentication(StaticVariables.CLIENT_ID, StaticVariables.SECRET);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("username", username);
            requestHeaders.add("password", password);
            requestHeaders.add("confirmPassword", confirmPassword);
            requestHeaders.setAuthorization(authentication);
            HttpEntity httpEntity = new HttpEntity<>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                return response.getBody();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
            if(result != null) {
                if (result.equalsIgnoreCase("200")) {
                    ((MainActivity) getActivity()).replaceFragment(((MainActivity) getActivity()).getFragmentSignin(), false);
                } else {
                    setError(result);
                }
            }
        }
    }
}
