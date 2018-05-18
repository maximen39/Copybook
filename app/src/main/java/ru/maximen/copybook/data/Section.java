package ru.maximen.copybook.data;

import java.util.ArrayList;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.fragments.ListFragment;

public class Section extends AbstractSectionData<Data> {

    private ListFragment listFragment;

    public Section(String title, String subTitle) {
        super(title, subTitle);
        this.listFragment = new ListFragment();
    }

    @Override
    public Data onClickAction(MainActivity activity) {
        if (this.getItems().size() > 0) {
            listFragment.setMainActivity(activity).setSection((ArrayList<Data>) this.getItems());
            activity.replaceFragment(listFragment);
        }
        return this;
    }
}
