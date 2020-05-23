package sk.upjs.micma.epdat_client_app;

//import android.support.annotation.NonNull;
//import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

public class SpeciesAdapter extends ListAdapter<Plant, SpeciesViewHolder> {
    private PlantOnClickListener plantOnClickListener;

    public SpeciesAdapter(PlantOnClickListener plantOnClickListener) {
        super(new SpeciesDiff());
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
