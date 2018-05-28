package ru.maximen.copybook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.maximen.copybook.ListActivity;
import ru.maximen.copybook.R;

public class FragmentArchive extends AbstractUpdatebleFragment {

    private ListView listItems;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive, null);
        this.listItems = view.findViewById(R.id.listItems);
        ListActivity listActivity = (ListActivity) getActivity();
        listItems.setAdapter(listActivity.getNoteList().getArchiveList());
        return view;
    }

    @Override
    public void onUpdateList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ListActivity) getActivity()).getNoteList().getArchiveList().notifyDataSetChanged();
            }
        });
    }
}
