package ru.maximen.copybook.fragments.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.maximen.copybook.R;

public class FragmentTextEditor extends Fragment {

    private EditText editText;
    private String text;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_texteditor, container, false);
        this.editText = view.findViewById(R.id.edit_text);
        if (text != null) {
            this.editText.setText(text);
        }
        return view;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setText(String text) {
        this.text = text;
    }
}
