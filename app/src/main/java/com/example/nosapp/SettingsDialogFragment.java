package com.example.nosapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.fragment.app.DialogFragment;



public class SettingsDialogFragment extends DialogFragment {

    private String sortBy, sortOrder;

    public interface SettingsDialogListener {
        void onSettingsDialogApply(String sortBy, String sortOrder);
    }

    public SettingsDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_settings, container);

        RadioGroup rgSortby = view.findViewById(R.id.radioGroupSortBy);
        rgSortby.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = view.findViewById(R.id.radioName);
                RadioButton rbStartDate = view.findViewById(R.id.radioStartDate);

                if (rbName.isChecked()) {
                    getContext().getSharedPreferences("NosAppPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortby", "logo").apply();
                } else if (rbStartDate.isChecked()) {
                    getContext().getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).edit().putString("sortby","startDate").apply();
                } else {
                    getContext().getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).edit().putString("sortby","episodes").apply();
                }
            }
        });

        RadioGroup rgSortOrder = view.findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = view.findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getContext().getSharedPreferences("NosAppPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                } else {
                    getContext().getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).edit().putString("sortorder","DESC").apply();
                }
            }
        });

        Button cancelButton = view.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button selectButton = view.findViewById(R.id.buttonSelect);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE);
                sortBy = sharedPreferences.getString("sortby", "");
                sortOrder = sharedPreferences.getString("sortorder", "");
                saveItem(sortBy, sortOrder);
            }
        });
        return view;
    }


    private void saveItem(String sortBy, String sortOrder) {
        SettingsDialogListener listener = (SettingsDialogListener) getActivity();
        listener.onSettingsDialogApply(sortBy, sortOrder);
        getDialog().dismiss();
    }
}


