package com.example.a41p.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.a41p.EventViewModel;
import com.example.a41p.R;
import com.example.a41p.data.Event;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditEventFragment extends Fragment {

    private TextInputEditText editTextTitle, editTextLocation, editTextDate;
    private AutoCompleteTextView autoCompleteCategory;
    private EventViewModel eventViewModel;
    private Calendar calendar = Calendar.getInstance();
    private int eventId = -1;
    private boolean isEditMode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_event, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextDate = view.findViewById(R.id.editTextDate);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        String[] categories = {"Work", "Social", "Travel", "Personal", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        autoCompleteCategory.setAdapter(adapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        if (getArguments() != null) {
            eventId = getArguments().getInt("eventId", -1);
            if (eventId != -1) {
                isEditMode = true;
                buttonSave.setText(R.string.btn_update_event);
                eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
                    for (Event e : events) {
                        if (e.getId() == eventId) {
                            editTextTitle.setText(e.getTitle());
                            autoCompleteCategory.setText(e.getCategory(), false);
                            editTextLocation.setText(e.getLocation());
                            calendar.setTimeInMillis(e.getDate());
                            updateLabel();
                            break;
                        }
                    }
                });
            }
        }

        editTextDate.setOnClickListener(v -> showDateTimePicker());

        buttonSave.setOnClickListener(v -> saveEvent(view));

        return view;
    }

    private void showDateTimePicker() {
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(requireContext(), (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateLabel();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        editTextDate.setText(sdf.format(calendar.getTime()));
    }

    private void saveEvent(View view) {
        String title = editTextTitle.getText().toString().trim();
        String category = autoCompleteCategory.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String dateStr = editTextDate.getText().toString().trim();

        if (title.isEmpty() || dateStr.isEmpty()) {
            Snackbar.make(view, R.string.error_mandatory_fields, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (calendar.getTimeInMillis() < System.currentTimeMillis() && !isEditMode) {
             Snackbar.make(view, R.string.error_past_date, Snackbar.LENGTH_LONG).show();
             return;
        }

        Event event = new Event(title, category, location, calendar.getTimeInMillis());
        if (isEditMode) {
            event.setId(eventId);
            eventViewModel.update(event);
            Snackbar.make(view, R.string.msg_event_updated, Snackbar.LENGTH_SHORT).show();
        } else {
            eventViewModel.insert(event);
            Snackbar.make(view, R.string.msg_event_saved, Snackbar.LENGTH_SHORT).show();
        }

        Navigation.findNavController(view).popBackStack();
    }
}