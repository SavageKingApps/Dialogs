package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeDialog extends DialogFragment
{
    public static final String TIME_DIALOG_TAG = "TimeDialog Tag";
    public static final int TIME_DIALOG_REQUEST = 2;
    private TimePickerDialog.OnTimeSetListener timeClick;

    public static TimeDialog getInstance()
    {
        return new TimeDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        timeClick = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                final Fragment fragment = getTargetFragment();
                if(fragment != null)
                {
                    fragment.onActivityResult(TIME_DIALOG_REQUEST, Activity.RESULT_OK, null);
                }
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        setCancelable(true);

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        final Activity activity = getActivity();

        return new TimePickerDialog(activity, timeClick, hour, minute, DateFormat.is24HourFormat( activity ));
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        final Fragment mFragment = getTargetFragment();
        if(mFragment != null)
        {
            mFragment.onActivityResult(TIME_DIALOG_REQUEST, Activity.RESULT_CANCELED, null);
        }
    }
}
