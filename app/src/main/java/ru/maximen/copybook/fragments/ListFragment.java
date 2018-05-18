package ru.maximen.copybook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;

public class ListFragment extends Fragment {

    private List<Data> dataList;
    private ListView listView;
    private MainActivity activity;
    private FloatingActionButton floatingActionButton;
    private CustomListViewAdapter customListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        initViews(view);
        if (dataList != null && dataList.size() > 0) {
            customListViewAdapter = new CustomListViewAdapter(getActivity(), dataList);
            listView.setAdapter(customListViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Data data = (Data) customListViewAdapter.getItem(position);
                    data.onClickAction(activity);
                }
            });
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateFragment createFragment = new CreateFragment()
                        .setCustomListViewAdapter(customListViewAdapter)
                        .setDataList(dataList);
                createFragment.show(activity.getFragmentManager(), "dialog");
            }
        });

        return view;
    }

    public ListFragment setMainActivity(MainActivity activity) {
        this.activity = activity;
        return this;
    }

    private void initViews(View view) {
        this.floatingActionButton = view.findViewById(R.id.fab);
        this.listView = view.findViewById(R.id.listView);
    }

    public void setSection(List<Data> dataList) {
        this.dataList = dataList;
    }
}
