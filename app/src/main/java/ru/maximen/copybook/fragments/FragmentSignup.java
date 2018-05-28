package ru.maximen.copybook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;

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
}
