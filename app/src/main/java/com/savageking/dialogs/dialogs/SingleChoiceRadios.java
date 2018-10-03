package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.savageking.dialogs.R;

public class SingleChoiceRadios extends DialogFragment
{
    private DialogInterface.OnClickListener doneClick;
    private DialogInterface.OnClickListener selectedClick;

    public static final String SINGLE_CHOICE_RADIO_DIALOG_TAG = "SingleChoiceRadios Tag";
    public static final int SINGLE_CHOICE_RADIO_DIALOG_REQUEST = 6;
    public static final String SINGLE_CHOICE_RADIO_INTENT_EXTRA = "selection";

    private Intent intent;

    public static SingleChoiceRadios getInstance()
    {
        return new SingleChoiceRadios();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        intent = new Intent();

        doneClick = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                final Fragment fragment = getTargetFragment();
                if(fragment != null)
                {
                    fragment.onActivityResult(SINGLE_CHOICE_RADIO_DIALOG_REQUEST, which, intent);
                }
            }
        };

        selectedClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                intent.putExtra(SINGLE_CHOICE_RADIO_INTENT_EXTRA, which );
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.dialog_single_choice_title)
                .setIcon(R.drawable.ic_pick_a_color)
                .setSingleChoiceItems( R.array.choices , 0, selectedClick )
                .setPositiveButton( R.string.radio_button_dialog_positive_button, doneClick)
                .setNegativeButton( R.string.radio_button_dialog_negative_button, doneClick);

        setCancelable(true);
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        final Fragment mFragment = getTargetFragment();
        if(mFragment != null)
        {
            mFragment.onActivityResult(SINGLE_CHOICE_RADIO_DIALOG_REQUEST, Activity.RESULT_CANCELED, intent);
        }
    }
}
