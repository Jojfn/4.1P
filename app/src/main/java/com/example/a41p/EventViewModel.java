package com.example.a41p;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.a41p.data.Event;
import com.example.a41p.data.EventRepository;
import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository mRepository;
    private LiveData<List<Event>> mAllEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllEvents = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    public void insert(Event event) {
        mRepository.insert(event);
    }

    public void update(Event event) {
        mRepository.update(event);
    }

    public void delete(Event event) {
        mRepository.delete(event);
    }
}