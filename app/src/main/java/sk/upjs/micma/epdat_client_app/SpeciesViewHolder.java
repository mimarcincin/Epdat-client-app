package sk.upjs.micma.epdat_client_app;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpeciesViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;


    public SpeciesViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(android.R.id.text1);
    }
    public void bind(final Plant species){
        textView.setText(species.getSpecies());
    }
}
