package ru.maximen.copybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class CustomListViewAdapter extends BaseAdapter {

    private Context context;
    private String[] title;
    private String[] subtitle;

    public CustomListViewAdapter(Context context, String[] titles, String[] subtitles) {
        this.context = context;
        this.title = titles;
        this.subtitle = subtitles;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.row_main, parent, false);

        TextView nameTextView = rowMain.findViewById(R.id.name_textView);
        nameTextView.setText(title[position]);

        TextView positionTextView = rowMain.findViewById(R.id.position_textview);
        positionTextView.setText(subtitle[position]);


        return rowMain;
    }
}
