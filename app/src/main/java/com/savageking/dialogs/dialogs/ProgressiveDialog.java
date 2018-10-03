package com.savageking.dialogs.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.savageking.dialogs.R;

public class ProgressiveDialog extends DialogFragment
{
    public static final String PROGRESS_DIALOG_TAG = "ProgressiveDialog Tag";
    public static final int PROGRESS_DIALOG_REQUEST = 3;

    private ProgressDialog progress;
    private Handler handler;

    private final int HANDLER_MESSAGE_WHAT_COUNTDOWN = 1;
    private final int HANDLER_MESSAGE_WHAT_FINISHED = 2;
    private int HANDLER_MESSAGE_DELAY = 1000;
    private int countdownCounter = 5;

    public static ProgressiveDialog getInstance()
    {
        return new ProgressiveDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final Handler.Callback callback = new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                switch( msg.what )
                {
                    case HANDLER_MESSAGE_WHAT_COUNTDOWN:

                        executeCountdown();

                        break;

                    case HANDLER_MESSAGE_WHAT_FINISHED:

                        dismissDialog();

                        break;

                }
                return false;
            }
        };

        handler = new Handler( callback );
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        setCancelable(true);

        final String progressDialogMessage = getString( R.string.progress_dialog_message_plural, countdownCounter );
        progress = new ProgressDialog(getActivity());
        progress.setTitle(R.string.progress_dialog_title);
        progress.setMessage( progressDialogMessage );
        progress.setIcon( R.drawable.ic_android );
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progress;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        handler.sendEmptyMessageDelayed( HANDLER_MESSAGE_WHAT_COUNTDOWN, HANDLER_MESSAGE_DELAY );
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        final Fragment mFragment = getTargetFragment();
        if(mFragment != null)
        {
            mFragment.onActivityResult(PROGRESS_DIALOG_REQUEST, Activity.RESULT_CANCELED, null);
        }
    }

    private void dismissDialog()
    {
        if( isAdded() )
        {
            final Fragment fragment = getTargetFragment();
            if(fragment != null)
            {
                fragment.onActivityResult(PROGRESS_DIALOG_REQUEST, Activity.RESULT_OK, null);
            }

            dismiss();
        }
    }

    private void executeCountdown()
    {
        if( isAdded() )
        {
            countdownCounter--;

            final String progressDialogMessage =  ( countdownCounter == 1 ) ? getString( R.string.progress_dialog_message_singular )
                    : getString( R.string.progress_dialog_message_plural, countdownCounter );

            progress.setMessage( progressDialogMessage );

            if( countdownCounter == 1 )
                handler.sendEmptyMessageDelayed( HANDLER_MESSAGE_WHAT_FINISHED, HANDLER_MESSAGE_DELAY );
            else
                handler.sendEmptyMessageDelayed( HANDLER_MESSAGE_WHAT_COUNTDOWN, HANDLER_MESSAGE_DELAY );
        }
    }
}
