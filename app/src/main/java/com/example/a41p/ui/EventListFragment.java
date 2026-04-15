package com.example.a41p.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.a41p.EventViewModel;
import com.example.a41p.R;
import com.google.android.material.snackbar.Snackbar;

public class EventListFragment extends Fragment {

    private EventViewModel eventViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        EventAdapter adapter = new EventAdapter(
                event -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventId", event.getId());
                    Navigation.findNavController(view).navigate(R.id.action_eventListFragment_to_addEditEventFragment, bundle);
                },
                event -> {
                    eventViewModel.delete(event);
                    Snackbar.make(view, R.string.msg_event_deleted, Snackbar.LENGTH_LONG).show();
                }
        );

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), adapter::submitList);

        androidx.recyclerview.widget.RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        return view;
    }
}