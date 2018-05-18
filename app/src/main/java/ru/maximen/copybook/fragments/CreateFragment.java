package ru.maximen.copybook.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import ru.maximen.copybook.CustomListViewAdapter;
import ru.maximen.copybook.R;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;

public class CreateFragment extends DialogFragment {

    private List<Data> dataList;
    private EditText title;
    private EditText subTitle;
    private CheckBox displayCount;
    private Button cancel;
    private Button add;
    private CustomListViewAdapter customListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, null);
        this.title = v.findViewById(R.id.titleee);
        this.subTitle = v.findViewById(R.id.subTitle);
        this.displayCount = v.findViewById(R.id.displayCount);
        this.cancel = v.findViewById(R.id.cancel);
        this.add = v.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.add(new Section(title.getText().toString(), subTitle.getText().toString()));
                customListViewAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        return v;
    }

    public CreateFragment setCustomListViewAdapter(CustomListViewAdapter customListViewAdapter) {
        this.customListViewAdapter = customListViewAdapter;
        return this;
    }

    public CreateFragment setDataList(List<Data> dataList) {
        this.dataList = dataList;
        return this;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}