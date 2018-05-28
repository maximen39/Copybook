package ru.maximen.copybook.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.maximen.copybook.R;
import ru.maximen.copybook.dto.Tag;

public class TagList extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Tag> tagList;
    private List<Tag> tagListNot;

    public TagList(Context context) {
        this.tagList = new ArrayList<>();
        this.tagListNot = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    TagList add(Tag tag) {
        tagList.add(tag);
        if (!tagListNot.contains(tag)) {
            tagListNot.add(tag);
        }
        return this;
    }

    TagList addAll(List<Tag> tags) {
        tagList.addAll(tags);
        for (Tag tag : tags) {
            if (!tagListNot.contains(tag)) {
                tagListNot.add(tag);
            }
        }
        return this;
    }

    TagList setTagList(List<Tag> tags) {
        tagList = tags;
        tagListNot.clear();
        for (Tag tag : tags) {
            if (!tagListNot.contains(tag)) {
                tagListNot.add(tag);
            }
        }
        return this;
    }

    public TagList remove(int index) {//TODO
        Tag tag = tagListNot.get(index);
        tagList.remove(tag);
        tagListNot.remove(index);
        return this;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    @Override
    public int getCount() {
        return tagListNot.size();
    }

    @Override
    public Object getItem(int position) {
        return tagListNot.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tagListNot.get(position).getTagId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.tag_list, parent, false);
        Tag tag = tagListNot.get(position);

        TextView titleView = view.findViewById(R.id.titleView);
        TextView numbersView = view.findViewById(R.id.numbersView);

        titleView.setText(tag.getName());
        numbersView.setText(String.valueOf(getEquals(tag)));

        return view;
    }

    private int getEquals(Tag tag) {
        int equals = 0;
        for (Tag tg : getTagList()) {
            if (tg.equals(tag)) {
                equals++;
            }
        }
        return equals;
    }
}

