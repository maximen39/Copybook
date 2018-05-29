package ru.maximen.copybook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.maximen.copybook.EditorActivity;
import ru.maximen.copybook.ListActivity;
import ru.maximen.copybook.R;

public class FragmentNote extends AbstractUpdatebleFragment {

    private ListView listItems;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, null);
        this.listItems = view.findViewById(R.id.listItems);
        this.floatingActionButton = view.findViewById(R.id.fab);
        ListActivity listActivity = (ListActivity) getActivity();
        listItems.setAdapter(listActivity.getNoteList());
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onUpdateList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ListActivity) getActivity()).getNoteList().notifyDataSetChanged();
            }
        });
    }
}
