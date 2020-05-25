package sk.upjs.micma.epdat_client_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.RecordDiff;
import sk.upjs.micma.epdat_client_app.RecordOnClickListener;
import sk.upjs.micma.epdat_client_app.models.Record;

public class RecordsAdapter extends ListAdapter<Record, RecordViewHolder> {
private RecordOnClickListener recordOnClickListener;

    public RecordsAdapter(RecordOnClickListener recordOnClickListener) {
        super(new RecordDiff());
        this.recordOnClickListener = recordOnClickListener;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.records_row,
                        parent,
                        false);
        return new RecordViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bind(getItem(position), recordOnClickListener);
    }
}
