package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import com.savageking.dialogs.R;

public class SingleChoice extends DialogFragment
{
    private DialogInterface.OnClickListener click;

    public static final String SINGLE_CHOICE_DIALOG_TAG = "SingleChoiceDialog Tag";
    public static final int SINGLE_CHOICE_DIALOG_REQUEST = 5;

    public static SingleChoice getInstance()
    {
        return new SingleChoice();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        click = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                final Fragment fragment = getTargetFragment();
                if(fragment != null)
                {
                    fragment.onActivityResult(SINGLE_CHOICE_DIALOG_REQUEST, Activity.RESULT_OK, null);
                }
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
                .setItems( R.array.choices , click );

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
            mFragment.onActivityResult(SINGLE_CHOICE_DIALOG_REQUEST, Activity.RESULT_CANCELED, null);
        }
    }
}
