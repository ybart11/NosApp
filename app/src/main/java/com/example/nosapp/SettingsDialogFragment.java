package com.example.nosapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SettingsDialogFragment extends DialogFragment {

    private RadioGroup sortByRadioGroup, sortOrderRadioGroup;
    private RadioButton nameRadioButton,
            ascendingRadioButton, descendingRadioButton;
    private ImageButton cancelButton, selectButton;

    private String sortBy, sortOrder;

    public interface SettingsDialogListener {
        void onSettingsDialogApply(String sortBy, String sortOrder);
    }

    public SettingsDialogFragment() {
        // Required empty public constructor
    }

    public SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sortByRadioGroup = view.findViewById(R.id.radioGroupSortBy);
        nameRadioButton = view.findViewById(R.id.radioName);


        sortOrderRadioGroup = view.findViewById(R.id.radioGroupSortOrder);
        ascendingRadioButton = view.findViewById(R.id.radioAscending);
        descendingRadioButton = view.findViewById(R.id.radioDescending);

        cancelButton = view.findViewById(R.id.cancelButton);
        selectButton = view.findViewById(R.id.selectButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sortByRadioButtonId = sortByRadioGroup.getCheckedRadioButtonId();
                int sortOrderRadioButtonId = sortOrderRadioGroup.getCheckedRadioButtonId();

                if (sortByRadioButtonId == nameRadioButton.getId()) {
                    sortBy = "Name";
                }

                if (sortOrderRadioButtonId == ascendingRadioButton.getId()) {
                    sortOrder = "Ascending";
                } else if (sortOrderRadioButtonId == descendingRadioButton.getId()) {
                    sortOrder = "Descending";
                }

                SettingsDialogListener listener = (SettingsDialogListener) getActivity();
                listener.onSettingsDialogApply(sortBy, sortOrder);
                dismiss();
            }
        });
    }
}

