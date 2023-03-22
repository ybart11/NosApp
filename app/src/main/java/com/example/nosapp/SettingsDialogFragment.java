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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


//public class SettingsDialogFragment extends DialogFragment {
//
////    private RadioGroup sortByRadioGroup, sortOrderRadioGroup;
////    private RadioButton nameRadioButton,
////            ascendingRadioButton, descendingRadioButton;
////    private Button cancelButton, selectButton;
//
//    private String sortBy, sortOrder;
//
//    public interface SettingsDialogListener {
//        void onSettingsDialogApply(String sortBy, String sortOrder);
//    }
//
//    public SettingsDialogFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.select_settings, container);
//
//
//        RadioGroup rgSortOrder = view.findViewById(R.id.radioGroupSortOrder);
//        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup arg0, int arg1) {
//                RadioButton rbAscending = findViewById(R.id.radioAscending);
//                if (rbAscending.isChecked()) {
//                    getSharedPreferences("NosAppPreferences",
//                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
//                } else {
//                    getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
//                }
//            }
//        });
//
//        Button saveButton = view.findViewById(R.id.buttonCancel);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
//        Button cancelButton = view.findViewById(R.id.buttonSelect);
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveItem(sortBy, sortOrder);
//            }
//        });
//        return view;
//    }
//
//    private void saveItem(String sortBy, String sortOrder) {
//        SettingsDialogListener activity = (SettingsDialogListener) getActivity();
//        activity.onSettingsDialogApply(sortBy, sortOrder);
//        getDialog().dismiss();
//    }
//}
//    public SettingsDialogFragment newInstance() {
//        return new SettingsDialogFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.select_settings, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        sortByRadioGroup = view.findViewById(R.id.radioGroupSortBy);
//        nameRadioButton = view.findViewById(R.id.radioName);
//
//
//        sortOrderRadioGroup = view.findViewById(R.id.radioGroupSortOrder);
//        ascendingRadioButton = view.findViewById(R.id.radioAscending);
//        descendingRadioButton = view.findViewById(R.id.radioDescending);
//
//        cancelButton = view.findViewById(R.id.buttonCancel);
//        selectButton = view.findViewById(R.id.buttonSelect);
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int sortByRadioButtonId = sortByRadioGroup.getCheckedRadioButtonId();
//                int sortOrderRadioButtonId = sortOrderRadioGroup.getCheckedRadioButtonId();
//
//                if (sortByRadioButtonId == nameRadioButton.getId()) {
//                    sortBy = "Name";
//                }
//
//                if (sortOrderRadioButtonId == ascendingRadioButton.getId()) {
//                    sortOrder = "Ascending";
//                } else if (sortOrderRadioButtonId == descendingRadioButton.getId()) {
//                    sortOrder = "Descending";
//                }
//
//                SettingsDialogListener listener = (SettingsDialogListener) getActivity();
//                listener.onSettingsDialogApply(sortBy, sortOrder);
//                dismiss();
//            }
//        });
//    }


