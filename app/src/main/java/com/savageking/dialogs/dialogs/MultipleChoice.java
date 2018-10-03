package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.savageking.dialogs.R;

import java.util.ArrayList;

public class MultipleChoice extends DialogFragment
{
    private DialogInterface.OnClickListener doneClick;
    private DialogInterface.OnMultiChoiceClickListener selectedClick;

    public static final String MULTIPLE_CHOICE_DIALOG_TAG = "MultipleChoice Tag";
    public static final int MULTIPLE_CHOICE_DIALOG_REQUEST = 7;
    public static final String MULTIPLE_CHOICE_INTENT_EXTRA = "selection";

    private Intent intent;
    private ArrayList<Integer> selectedItems;

    public static MultipleChoice getInstance()
    {
        return new MultipleChoice();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        intent = new Intent();
        selectedItems = new ArrayList<>();

        doneClick = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                intent.putExtra(MULTIPLE_CHOICE_INTENT_EXTRA, selectedItems );

                final Fragment fragment = getTargetFragment();
                if(fragment != null)
                {
                    fragment.onActivityResult(MULTIPLE_CHOICE_DIALOG_REQUEST, which, intent);
                }
            }
        };

        selectedClick = new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked)
            {
                if (isChecked)
                {
                    selectedItems.add(which);
                }

                else if (selectedItems.contains(which))
                {
                    selectedItems.remove(Integer.valueOf(which));
                }

                final  AlertDialog alertDialog = (AlertDialog) getDialog();
                final Button positiveButton = (Button) alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setEnabled(!selectedItems.isEmpty());
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.multiple_choice_dialog_title)
                .setIcon(R.drawable.ic_pick_a_color)
                .setMultiChoiceItems( R.array.choices , null, selectedClick )
                .setPositiveButton( R.string.multiple_choice_dialog_positive_button, doneClick)
                .setNegativeButton( R.string.multiple_choice_dialog_negative_button, doneClick);

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
            mFragment.onActivityResult(MULTIPLE_CHOICE_DIALOG_REQUEST, Activity.RESULT_CANCELED, intent);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        final  AlertDialog dialog = (AlertDialog) getDialog();
        final Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setEnabled(!selectedItems.isEmpty());
    }
}
