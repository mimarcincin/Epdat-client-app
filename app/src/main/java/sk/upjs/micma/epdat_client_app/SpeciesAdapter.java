package sk.upjs.micma.epdat_client_app;

//import android.support.annotation.NonNull;
//import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

public class SpeciesAdapter extends ListAdapter<Plant, SpeciesViewHolder> {

    public SpeciesAdapter() {
        super(new SpeciesDiff());
        List<Plant> list  = new LinkedList<>();
        Plant s1 = new Plant(new Long(1), "fam1", "gen1", "spec1", "auth1", "S", "S", "S");
        Plant s2 = new Plant(new Long(1), "fam1", "gen1", "spec1", "auth1", "S", "S", "S");
        Plant s3 = new Plant(new Long(1), "fam1", "gen1", "spec1", "auth1", "S", "S", "S");
        for (int i = 0; i < 50; i++) {
           list.add(new Plant(new Long(i),i+"",i+"",i+"",i+"",i+"",i+"",i+""));
        }

        //submitList(list);
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,
                        parent,
                        false);
        return new SpeciesViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder speciesViewHolder, int i) {
        speciesViewHolder.bind(getItem(i));
    }

}
