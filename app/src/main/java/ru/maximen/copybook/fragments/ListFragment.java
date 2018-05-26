package ru.maximen.copybook.fragments;

import android.content.Intent;
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

import ru.maximen.copybook.CreatorActivity;
import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.data.SectionData;
import ru.maximen.copybook.data.contents.SimpleData;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                    } else if (data instanceof SimpleData) {
                        Intent intent = new Intent(mainActivity(), CreatorActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("id", (int)data.id());
                        intent.putExtra("name", data.title());
                        intent.putExtra("are", ((SimpleData) data).getContent());
                        mainActivity().startActivityForResult(intent, 1);
                    }
                }
            });
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity(), CreatorActivity.class);
                intent.putExtra("position", -1);
                intent.putExtra("id", -1);
                intent.putExtra("name", "");
                intent.putExtra("are", "");
                mainActivity().startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        int position = data.getIntExtra("position", -1);
        long id = data.getIntExtra("id",  -1);
        String name = data.getStringExtra("name");
        String are = data.getStringExtra("are");
        if (position == -1 || id == -1) {
            SimpleData simpleData = new SimpleData();
            simpleData.title(name).subTitle("");
            simpleData.setContent(are);
            mainActivity().getLocalService().addData(section().id(), simpleData);
            section().addItem(simpleData);
            customListViewAdapter.notifyDataSetChanged();
        } else {
            SimpleData simpleData = new SimpleData();
            simpleData.title(name).subTitle("").id(id);
            simpleData.setContent(are);
            mainActivity().getLocalService().updateData(section().id(), simpleData);
            section().setItem(position, simpleData);
            customListViewAdapter.notifyDataSetChanged();
        }
        System.out.println("added");
        System.out.println(name);
        System.out.println(id);
    }

    private void initViews(View view) {
        this.floatingActionButton = view.findViewById(R.id.fab);
        this.textView = view.findViewById(R.id.empty);
        this.listView = view.findViewById(R.id.listView);
    }
}
