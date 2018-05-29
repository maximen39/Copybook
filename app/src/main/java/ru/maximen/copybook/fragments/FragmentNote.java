package ru.maximen.copybook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

import ru.maximen.copybook.EditorActivity;
import ru.maximen.copybook.ListActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.dto.Note;
import ru.maximen.copybook.dto.Reminder;

import static android.app.Activity.RESULT_OK;

public class FragmentNote extends AbstractUpdatebleFragment {

    private ListView listItems;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, null);
        this.listItems = view.findViewById(R.id.listItems);
        this.floatingActionButton = view.findViewById(R.id.fab);
        final ListActivity listActivity = (ListActivity) getActivity();
        listItems.setAdapter(listActivity.getNoteList());
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) listActivity.getNoteList().getItem(position);
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                intent.putExtra("noteId", note.getNoteId());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                if (note.getReminder() != null) {
                    intent.putExtra("reminder", note.getReminder().getRemindDate().getTime());
                }
                startActivityForResult(intent, 1);
            }
        });
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-: " + resultCode);
        if (data != null && resultCode == RESULT_OK) {
            if (data.hasExtra("noteId")) {
                System.out.println("EDIT: " + data.getIntExtra("noteId", -1));
                System.out.println("TITLE: " + data.getStringExtra("title"));
                System.out.println("CONTENT: " + data.getStringExtra("content"));
                System.out.println("REMINDER: " + data.getLongExtra("reminder", (long) 0));
            } else {
                System.out.println("CREATE");
                System.out.println("TITLE: " + data.getStringExtra("title"));
                System.out.println("CONTENT: " + data.getStringExtra("content"));
                System.out.println("REMINDER: " + data.getLongExtra("reminder", (long) 0));

                Note note = new Note();
                note.setTitle(data.getStringExtra("title"));
                note.setContent(data.getStringExtra("content"));
                if (data.getLongExtra("reminder", (long) 0) > 0) {
                    Reminder reminder = new Reminder();
                    reminder.setRemindDate(new Date(data.getLongExtra("reminder", (long) 0)));
                    note.setReminder(reminder);
                }
                note.setDate_create(new Date());
                ((ListActivity) getActivity()).getNoteManager().addNote(note);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
