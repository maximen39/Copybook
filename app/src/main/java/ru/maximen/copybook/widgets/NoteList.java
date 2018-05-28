package ru.maximen.copybook.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.maximen.copybook.R;
import ru.maximen.copybook.dto.Note;
import ru.maximen.copybook.utils.NoteUtil;

public class NoteList extends BaseAdapter {
    private static final String DATE_TIME_FORMAT_12_HOUR = "d MMM, yyyy  h:mm a";
    private static final String DATE_TIME_FORMAT_24_HOUR = "d MMM, yyyy  k:mm";

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Note> noteList;
    private ArchiveList archiveList;
    private TrashList trashList;
    private ReminderList reminderList;

    public NoteList(Context context) {
        this.noteList = new ArrayList<>();
        this.archiveList = new ArchiveList(context);
        this.trashList = new TrashList(context);
        this.reminderList = new ReminderList(context, this);
        this.context = context;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    public NoteList add(Note note) {
        if (note.isArchive()) {
            archiveList.add(note);
        } else if (note.isTrash()) {
            trashList.add(note);
        } else {
            noteList.add(note);
            //notifyDataSetChanged();
            if (note.getReminder() != null) {
                reminderList.add(note);
            }
        }
        return this;
    }

    public NoteList addAll(List<Note> notes) {
        for (Note note : notes) {
            if (note.isArchive()) {
                archiveList.add(note);
            } else if (note.isTrash()) {
                trashList.add(note);
            } else {
                noteList.add(note);
                //notifyDataSetChanged();
                if (note.getReminder() != null) {
                    reminderList.add(note);
                }
            }
        }
        return this;
    }

    public NoteList setNoteList(List<Note> note) {
        List<Note> notes = new ArrayList<>();
        List<Note> trash = new ArrayList<>();
        List<Note> archive = new ArrayList<>();
        List<Note> reminder = new ArrayList<>();

        for (Note n : note) {
            if (n.isArchive()) {
                archive.add(n);
            } else if (n.isTrash()) {
                trash.add(n);
            } else {
                notes.add(n);
                if (n.getReminder() != null) {
                    reminder.add(n);
                }
            }
        }
        trashList.setTrashList(trash);
        archiveList.setArchiveList(archive);
        reminderList.setReminderList(reminder);
        noteList = notes;
        //notifyDataSetChanged();
        return this;
    }

    public NoteList remove(int index) {//TODO
        noteList.remove(index);
        //notifyDataSetChanged();
        return this;
    }

  /*  public NoteList remove(Note note) {
        noteList.remove(note);
        notifyDataSetChanged();
        return this;
    }*/

    public List<Note> getNoteList() {
        return noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return noteList.get(position).getNoteId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.note_list, parent, false);
        Note note = noteList.get(position);

        ImageView itemColorImageView = view.findViewById(R.id.itemColorImageView);
        TextView titleView = view.findViewById(R.id.titleView);
        TextView subTitleView = view.findViewById(R.id.subTitleView);

        titleView.setText(note.getTitle());
        if (note.getReminder() != null) {
            Date date = note.getReminder().getRemindDate();
            if (DateFormat.is24HourFormat(context)) {
                subTitleView.setText(formatDate(DATE_TIME_FORMAT_24_HOUR, date));
            } else {
                subTitleView.setText(formatDate(DATE_TIME_FORMAT_12_HOUR, date));
            }
            subTitleView.setTextColor(context.getResources().getColor(R.color.colorOrange));
        } else {
            if (note.getContent().length() >= 30) {
                String text = note.getContent().substring(0, 29);
                subTitleView.setText(text.trim().replace("\n", " "));
            } else {
                subTitleView.setText(note.getContent().trim().replace("\n", " "));
            }
            subTitleView.setTextColor(context.getResources().getColor(R.color.colorSerega));
        }

        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(note.getTitle().substring(0, 1), note.getColor() <= 0 ?
                        context.getResources().getColor(NoteUtil.getColorByNumber(note.getNoteId()))
                        : note.getColor());
        itemColorImageView.setImageDrawable(myDrawable);
        return view;
    }

    private String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, Locale.getDefault());
        return simpleDateFormat.format(dateToFormat);
    }

    public TrashList getTrashList() {
        return trashList;
    }

    public ArchiveList getArchiveList() {
        return archiveList;
    }

    public ReminderList getReminderList() {
        return reminderList;
    }

    public void notifyAllDataSetChanged() {
        notifyDataSetChanged();
        getReminderList().notifyDataSetChanged();
        getTrashList().notifyDataSetChanged();
        getArchiveList().notifyDataSetChanged();
    }
}
