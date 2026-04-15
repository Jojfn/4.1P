package com.example.a41p.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class EventRepository {
    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    public void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            mEventDao.insert(event);
        });
    }

    public void update(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            mEventDao.update(event);
        });
    }

    public void delete(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            mEventDao.delete(event);
        });
    }
}