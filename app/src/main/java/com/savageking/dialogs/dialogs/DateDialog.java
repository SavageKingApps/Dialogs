package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateDialog extends DialogFragment
{
    public static final String DATE_DIALOG_TAG = "DateDialog Tag";
    public static final int DATE_DIALOG_REQUEST = 1;
    private DatePickerDialog.OnDateSetListener dateClick;

    public static DateDialog getInstance()
    {
        return new DateDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dateClick = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                final Fragment fragment = getTargetFragment();
                if(fragment != null)
                {
                    fragment.onActivityResult(DATE_DIALOG_REQUEST, Activity.RESULT_OK, null);
                }
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        setCancelable(true);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateClick, year, month, day);
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        final Fragment mFragment = getTargetFragment();
        if(mFragment != null)
        {
            mFragment.onActivityResult(DATE_DIALOG_REQUEST, Activity.RESULT_CANCELED, null);
        }
    }
}
