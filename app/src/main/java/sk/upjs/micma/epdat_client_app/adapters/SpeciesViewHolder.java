package sk.upjs.micma.epdat_client_app.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sk.upjs.micma.epdat_client_app.PlantOnClickListener;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Plant;

public class SpeciesViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private TextView familyTextView;
    private TextView genusTextView;
    private TextView speciesTextView;

    public SpeciesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        familyTextView = (TextView) itemView.findViewById(R.id.familyName);
        genusTextView = (TextView) itemView.findViewById(R.id.genusName);
        speciesTextView = (TextView) itemView.findViewById(R.id.speciesName);



    }
    private void setPlantDetails(Plant plant, final PlantOnClickListener plantOnClickListener){
        familyTextView.setText(plant.getFamily());
        genusTextView.setText(plant.getGenus());
        speciesTextView.setText(plant.getSpecies());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantOnClickListener.onPlantClick(plant);
            }
        });
    }

    public void bind(final Plant species, final PlantOnClickListener plantOnClickListener) {
        setPlantDetails(species, plantOnClickListener);
    }
}
