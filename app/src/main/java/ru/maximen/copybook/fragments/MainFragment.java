package ru.maximen.copybook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.data.contents.Ideas;

public class MainFragment extends Fragment {

    private ListView listView;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        initViews(view);

        List<Section> sections = new ArrayList<>();

        Section ideas = new Section("Идеи", "Количество: 0");
        Section notes = new Section("Заметки", "Количество: 0");
        Section reminder = new Section("Напоминания", "Количество: 0");
        Section todo = new Section("Задачи", "Количество: 0");
        Section articles = new Section("Ссылки на интересные статьи", "Количество: 0");
        Section quotes = new Section("Цитаты", "Количество: 0");
        Section histories = new Section("Истории", "Количество: 0");
        Section bookmarks = new Section("Закладки", "Количество: 0");
        Section booktitle = new Section("Названия книг", "Количество: 0");

        Section idea1 = new Section("Идея 1", "Количество: 0");
        Section idea2 = new Section("Идея 2", "Количество: 0");
        Section idea3 = new Section("Идея 3", "Количество: 0");

        idea1.addItem(new Ideas("Тест", "test"));

        ideas.addItem(idea1);
        ideas.addItem(idea2);
        ideas.addItem(idea3);

        sections.add(ideas);
        sections.add(notes);
        sections.add(reminder);
        sections.add(todo);
        sections.add(articles);
        sections.add(quotes);
        sections.add(histories);
        sections.add(bookmarks);
        sections.add(booktitle);

        final CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(view.getContext(), sections);
        listView.setAdapter(customListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data data = (Data) customListViewAdapter.getItem(position);
                data.onClickAction(activity);
            }
        });

        return view;
    }

    private void initViews(View view) {
        this.listView = view.findViewById(R.id.mainListView);
    }

    public MainFragment setMainActivity(MainActivity activity) {
        this.activity = activity;
        return this;
    }
}
