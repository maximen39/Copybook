package ru.maximen.copybook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.maximen.copybook.data.Data;

public class CustomListViewAdapter extends BaseAdapter {

    private Context context;
    private List<? extends Data> dataList;

    public CustomListViewAdapter(Context context, List<? extends Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final Data data = dataList.get(position);
        @SuppressLint("ViewHolder") View rowMain = layoutInflater.inflate(R.layout.row_main, parent,
                false);

        TextView nameTextView = rowMain.findViewById(R.id.name_textView);
        nameTextView.setText(data.title());

        TextView positionTextView = rowMain.findViewById(R.id.subName_textView);
        positionTextView.setText(data.subTitle());

        final ImageView imageView = rowMain.findViewById(R.id.star_imageView);
        /*updateImage(imageView, data);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.isStar(!data.isStar());
                updateImage(imageView, data);
            }
        });*/

        return rowMain;
    }
/*
    private void updateImage(ImageView image, Data data) {
        image.setBackgroundResource(data.isStar() ? R.drawable.baseline_star_black_18dp :
                R.drawable.baseline_star_border_black_18dp);
    }*/
}
