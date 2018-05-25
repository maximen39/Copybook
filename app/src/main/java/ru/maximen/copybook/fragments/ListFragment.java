package ru.maximen.copybook.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.data.SectionData;

public class ListFragment extends BaseCopybookFragment {

    private ListView listView;
    private TextView textView;
    private CustomListViewAdapter customListViewAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                CreateFragment createFragment = new CreateFragment()
                        .setCustomListViewAdapter(customListViewAdapter)
                        .setDataList(section().id(), section().getItems());
                createFragment.setActivity(mainActivity());
                createFragment.show(mainActivity().getFragmentManager(), "dialog");
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        initViews(view);

        if (section() != null) {
            customListViewAdapter = new CustomListViewAdapter(getActivity(), section().getItems());
            listView.setAdapter(customListViewAdapter);
            listView.setEmptyView(textView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Data data = (Data) customListViewAdapter.getItem(position);
                    if (data instanceof SectionData) {
                        BaseCopybookFragment copybookFragment = new ListFragment();
                        copybookFragment.section((Section) data).mainActivity(mainActivity());

                        mainActivity().setCurrentFragment(copybookFragment);
                        mainActivity().replaceFragment(copybookFragment);
                    }
                }
            });
        }
        return view;
    }

    private void initViews(View view) {
        this.floatingActionButton = view.findViewById(R.id.fab);
        this.textView = view.findViewById(R.id.empty);
        this.listView = view.findViewById(R.id.listView);
    }
}
