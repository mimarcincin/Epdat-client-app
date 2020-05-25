package sk.upjs.micma.epdat_client_app;

import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import sk.upjs.micma.epdat_client_app.models.Plant;

public class PlantDiff extends DiffUtil.ItemCallback {

    @Override
    public boolean areItemsTheSame(@NonNull Object o, @NonNull Object t1) {
        return ((Plant) o).getId() == ((Plant) t1).getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Object o, @NonNull Object t1) {
        return Objects.equals(o, t1);
    }
}
