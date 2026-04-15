package com.example.a41p.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a41p.R;
import com.example.a41p.data.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Event event);
    }

    public EventAdapter(OnItemClickListener listener, OnDeleteClickListener deleteListener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getDate() == newItem.getDate();
        }
    };

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = getItem(position);
        holder.textViewTitle.setText(currentEvent.getTitle());
        holder.textViewCategory.setText(currentEvent.getCategory());
        holder.textViewLocation.setText(currentEvent.getLocation());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.textViewDate.setText(sdf.format(new Date(currentEvent.getDate())));
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewCategory;
        private TextView textViewDate;
        private TextView textViewLocation;
        private ImageButton buttonDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                    deleteListener.onDeleteClick(getItem(position));
                }
            });
        }
    }
}