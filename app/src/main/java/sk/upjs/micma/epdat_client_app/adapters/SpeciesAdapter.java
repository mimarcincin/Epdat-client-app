package sk.upjs.micma.epdat_client_app.adapters;

//import android.support.annotation.NonNull;
//import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import sk.upjs.micma.epdat_client_app.PlantOnClickListener;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.PlantDiff;
import sk.upjs.micma.epdat_client_app.models.Plant;

public class SpeciesAdapter extends ListAdapter<Plant, SpeciesViewHolder> {
    private PlantOnClickListener plantOnClickListener;

    public SpeciesAdapter(PlantOnClickListener plantOnClickListener) {
        super(new PlantDiff());
        this.plantOnClickListener = plantOnClickListener;
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.species_row,
                        parent,
                        false);
        return new SpeciesViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder speciesViewHolder, int i) {
        speciesViewHolder.bind(getItem(i), plantOnClickListener);
    }

}
