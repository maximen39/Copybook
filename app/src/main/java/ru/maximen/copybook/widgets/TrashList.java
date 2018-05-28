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

public class TrashList extends BaseAdapter {
    private static final String DATE_TIME_FORMAT_12_HOUR = "d MMM, yyyy  h:mm a";
    private static final String DATE_TIME_FORMAT_24_HOUR = "d MMM, yyyy  k:mm";

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Note> trashList;

    public TrashList(Context context) {
        this.trashList = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    TrashList add(Note note) {
        trashList.add(note);
        //notifyDataSetChanged();
        return this;
    }

    TrashList addAll(List<Note> notes) {
        trashList.addAll(notes);
        //notifyDataSetChanged();
        return this;
    }

    TrashList setTrashList(List<Note> note) {
        trashList = note;
        //notifyDataSetChanged();
        return this;
    }

    public TrashList remove(int index) {//TODO
        trashList.remove(index);
        //notifyDataSetChanged();
        return this;
    }

/*    public TrashList remove(Note note) {
        trashList.remove(note);
        notifyDataSetChanged();
        return this;
    }*/

    public List<Note> getTrashList() {
        return trashList;
    }

    @Override
    public int getCount() {
        return trashList.size();
    }

    @Override
    public Object getItem(int position) {
        return trashList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return trashList.get(position).getNoteId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.note_list, parent, false);
        Note note = trashList.get(position);

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
            subTitleView.setTextColor(context.getResources().getColor(R.color.colorRed));
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
}

