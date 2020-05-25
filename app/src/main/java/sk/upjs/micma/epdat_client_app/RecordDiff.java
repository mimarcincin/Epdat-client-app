package sk.upjs.micma.epdat_client_app;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import sk.upjs.micma.epdat_client_app.models.Record;

public class RecordDiff extends DiffUtil.ItemCallback{
    @Override
    public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        return ((Record) oldItem).getId() == ((Record) newItem).getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        return areItemsTheSame(oldItem,newItem);
    }
}
