package ru.maximen.copybook.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;

public class CreateFragment extends DialogFragment {

    private long section_id;
    private List<Data> dataList;
    private EditText title;
    private EditText subTitle;
    private Button add;
    private MainActivity activity;
    private CustomListViewAdapter customListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, null);
        initView(v);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Section section = new Section();
                section.title(title.getText().toString()).subTitle(subTitle.getText().toString());
                if (section_id != -1) {
                    activity.getLocalService().addChildrenSection(section_id, section);
                } else {
                    activity.getLocalService().addSection(section);
                }
                dataList.add(section);
                customListViewAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        return v;
    }

    private void initView(View v) {
        this.title = v.findViewById(R.id.titleee);
        this.subTitle = v.findViewById(R.id.subTitle);
        this.add = v.findViewById(R.id.add);
    }

    public CreateFragment setCustomListViewAdapter(CustomListViewAdapter customListViewAdapter) {
        this.customListViewAdapter = customListViewAdapter;
        return this;
    }

    public CreateFragment setDataList(long section_id, List<Data> dataList) {
        this.section_id = section_id;
        this.dataList = dataList;
        return this;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}