package ru.maximen.copybook.fragments.pager;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.maximen.copybook.R;

public class FragmentReminderEditor extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private SwitchCompat mToDoDateSwitch;
    private TextView mReminderTextView;
    private Date mUserReminderDate;
    private EditText mDateEditText;
    private EditText mTimeEditText;

    private boolean mUserHasReminder;
    private Date remindMe;

    public void setmUserHasReminder(boolean mUserHasReminder) {
        this.mUserHasReminder = mUserHasReminder;
    }

    public boolean ismUserHasReminder() {
        return mUserHasReminder;
    }

    public Date getmUserReminderDate() {
        return mUserReminderDate;
    }

    public void setRemindMe(Date remindMe) {
        this.remindMe = remindMe;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_remindereditor, container, false);
        mUserDateSpinnerContainingLinearLayout = view.findViewById(R.id.toDoEnterDateLinearLayout);
        mToDoDateSwitch = view.findViewById(R.id.toDoHasDateSwitchCompat);
        mReminderTextView = view.findViewById(R.id.newToDoDateTimeReminderTextView);


        if (mUserHasReminder && (mUserReminderDate != null)) {
            setReminderTextView();
            setEnterDateLayoutVisibleWithAnimations(true);
        }
        if (mUserReminderDate == null) {
            mToDoDateSwitch.setChecked(false);
            mReminderTextView.setVisibility(View.INVISIBLE);
        }

        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setChecked(mUserHasReminder && (mUserReminderDate != null));
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mUserReminderDate = null;
                }
                mUserHasReminder = isChecked;
                setDateAndTimeEditText();
                setEnterDateLayoutVisibleWithAnimations(isChecked);
            }
        });

        mDateEditText = view.findViewById(R.id.newTodoDateEditText);
        mTimeEditText = view.findViewById(R.id.newTodoTimeEditText);

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                if (remindMe != null) {
                    date = mUserReminderDate;
                } else {
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), FragmentReminderEditor.this, year, month, day);
                datePickerDialog.show();
            }
        });


        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                if (remindMe != null) {
                    date = mUserReminderDate;
                } else {
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), FragmentReminderEditor.this, hour, minute, DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
        setDateAndTimeEditText();
        return view;
    }

    private void setDateAndTimeEditText() {
        if (mUserHasReminder && mUserReminderDate != null) {
            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
            String formatToUse;
            if (DateFormat.is24HourFormat(getContext())) {
                formatToUse = "k:mm";
            } else {
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, mUserReminderDate);
            mTimeEditText.setText(userTime);
            mDateEditText.setText(userDate);

        } else {
            mDateEditText.setText(getString(R.string.date_reminder_default));
            boolean time24 = DateFormat.is24HourFormat(getContext());
            Calendar cal = Calendar.getInstance();
            if (time24) {
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            } else {
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
            }
            cal.set(Calendar.MINUTE, 0);
            mUserReminderDate = cal.getTime();
            String timeString;
            if (time24) {
                timeString = formatDate("k:mm", mUserReminderDate);
            } else {
                timeString = formatDate("h:mm a", mUserReminderDate);
            }
            mTimeEditText.setText(timeString);
        }
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int hour, minute;

        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day);

        if (reminderCalendar.before(calendar)) {
            return;
        }

        if (mUserReminderDate != null) {
            calendar.setTime(mUserReminderDate);
        }

        if (DateFormat.is24HourFormat(getContext())) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        } else {

            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);

        calendar.set(year, month, day, hour, minute);
        mUserReminderDate = calendar.getTime();
        setReminderTextView();
        setDateEditText();
    }

    public void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        if (mUserReminderDate != null) {
            calendar.setTime(mUserReminderDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, 0);
        mUserReminderDate = calendar.getTime();

        setReminderTextView();
        setTimeEditText();
    }

    public void setDateEditText() {
        String dateFormat = "d MMM, yyyy";
        mDateEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void setTimeEditText() {
        String dateFormat;
        if (DateFormat.is24HourFormat(getContext())) {
            dateFormat = "k:mm";
        } else {
            dateFormat = "h:mm a";

        }
        mTimeEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void setReminderTextView() {
        if (mUserReminderDate != null) {
            mReminderTextView.setVisibility(View.VISIBLE);
            if (mUserReminderDate.before(new Date())) {
                mReminderTextView.setText(getString(R.string.date_error_check_again));
                mReminderTextView.setTextColor(Color.RED);
                return;
            }
            Date date = mUserReminderDate;
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";

            if (DateFormat.is24HourFormat(getContext())) {
                timeString = formatDate("k:mm", date);
            } else {
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            mReminderTextView.setTextColor(getResources().getColor(R.color.colorW));
            mReminderTextView.setText(finalString);
        } else {
            mReminderTextView.setVisibility(View.INVISIBLE);

        }
    }

    public static String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, Locale.getDefault());
        return simpleDateFormat.format(dateToFormat);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(year, month, dayOfMonth);
    }

    public void setEnterDateLayoutVisible(boolean checked) {
        if (checked) {
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void setEnterDateLayoutVisibleWithAnimations(boolean checked) {
        if (checked) {
            setReminderTextView();
            mUserDateSpinnerContainingLinearLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        } else {
            mUserDateSpinnerContainingLinearLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }

    }

    public Date getRemindMe() {
        return remindMe;
    }
}
