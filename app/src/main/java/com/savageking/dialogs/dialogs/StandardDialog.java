package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.savageking.dialogs.R;

public class StandardDialog extends DialogFragment
{
    private DialogInterface.OnClickListener click;

    public static final String STANDARD_DIALOG_TAG = "StandardDialog Tag";
    public static final int STANDARD_DIALOG_REQUEST = 4;

    public static StandardDialog getInstance()
    {
        return new StandardDialog();
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
                    fragment.onActivityResult(STANDARD_DIALOG_REQUEST, which, null);
                }
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.standard_dialog_title)
                .setMessage( R.string.standard_dialog_message)
                .setIcon(R.drawable.ic_android)
                .setPositiveButton( R.string.standard_dialog_positive_button, click)
                .setNegativeButton( R.string.standard_dialog_negative_button, click);

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
            mFragment.onActivityResult(STANDARD_DIALOG_REQUEST, Activity.RESULT_CANCELED, null);
        }
    }
}
