package com.savageking.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.savageking.dialogs.dialogs.DateDialog;
import com.savageking.dialogs.dialogs.MultipleChoice;
import com.savageking.dialogs.dialogs.ProgressiveDialog;
import com.savageking.dialogs.dialogs.SingleChoice;
import com.savageking.dialogs.dialogs.SingleChoiceRadios;
import com.savageking.dialogs.dialogs.StandardDialog;
import com.savageking.dialogs.dialogs.TimeDialog;

public class ShowFragment extends Fragment {

    private final static String TAG = "ShowFragment";
    private View.OnClickListener toolBarClick;
    private View.OnClickListener buttonClick;

    private final int DATE_PICKER_DIALOG = R.id.radio_date_picker;
    private final int TIME_PICKER_DIALOG = R.id.radio_time_picker;
    private final int PROGRESS_DIALOG = R.id.radio_progress_dialog;
    private final int STANDARD_DIALOG = R.id.radio_standard;
    private final int SINGLE_CHOICE_DIALOG = R.id.radio_list_single;
    private final int SINGLE_CHOICE_RADIOS_DIALOG = R.id.radio_list_single_radios;
    private final int MULTIPLE_CHOICE_DIALOG = R.id.radio_list_multiple;

    public static ShowFragment getInstance() {
        return new ShowFragment();
    }
    public static String getInstanceTag() {
        return TAG;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolBarClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        };

        buttonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final View view = getView();
                final RadioGroup radioGroup = view.findViewById( R.id.radio_group );
                final int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                DialogFragment dialog = null;
                final FragmentManager manager = getFragmentManager();

                switch( checkedRadioButtonId )
                {
                    case DATE_PICKER_DIALOG:

                        dialog = DateDialog.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, DateDialog.DATE_DIALOG_REQUEST);
                        dialog.show( manager, DateDialog.DATE_DIALOG_TAG );

                        break;

                    case TIME_PICKER_DIALOG:

                        dialog = TimeDialog.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, TimeDialog.TIME_DIALOG_REQUEST);
                        dialog.show( manager, TimeDialog.TIME_DIALOG_TAG );

                        break;

                    case PROGRESS_DIALOG:

                        dialog = ProgressiveDialog.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, ProgressiveDialog.PROGRESS_DIALOG_REQUEST);
                        dialog.show( manager, ProgressiveDialog.PROGRESS_DIALOG_TAG );

                        break;

                    case STANDARD_DIALOG:

                        dialog = StandardDialog.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, StandardDialog.STANDARD_DIALOG_REQUEST);
                        dialog.show( manager, StandardDialog.STANDARD_DIALOG_TAG );

                        break;

                    case SINGLE_CHOICE_DIALOG:

                        dialog = SingleChoice.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, SingleChoice.SINGLE_CHOICE_DIALOG_REQUEST );
                        dialog.show( manager , SingleChoice.SINGLE_CHOICE_DIALOG_TAG);

                        break;

                    case SINGLE_CHOICE_RADIOS_DIALOG:

                        dialog = SingleChoiceRadios.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, SingleChoiceRadios.SINGLE_CHOICE_RADIO_DIALOG_REQUEST );
                        dialog.show( manager , SingleChoiceRadios.SINGLE_CHOICE_RADIO_DIALOG_TAG );

                        break;

                    case MULTIPLE_CHOICE_DIALOG:

                        dialog = MultipleChoice.getInstance();
                        dialog.setTargetFragment( ShowFragment.this, MultipleChoice.MULTIPLE_CHOICE_DIALOG_REQUEST );
                        dialog.show( manager , MultipleChoice.MULTIPLE_CHOICE_DIALOG_TAG );

                        break;

                    default:

                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.show_fragment, container, false);

        final Toolbar toolBar = view.findViewById(R.id.toolbar);
        toolBar.setTitle(R.string.toolbar_title);
        toolBar.setSubtitle(R.string.toolbar_subtitle);
        toolBar.setNavigationIcon(R.drawable.ic_clear_mtrl_alpha);
        toolBar.setNavigationOnClickListener(toolBarClick);

        final Button button = view.findViewById( R.id.button_show);
        button.setOnClickListener( buttonClick );

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        final int message = ( resultCode == Activity.RESULT_OK ) ? R.string.activity_result_ok : R.string.activity_result_cancel;
        Toast.makeText( getContext(), message, Toast.LENGTH_LONG ).show();
    }
}