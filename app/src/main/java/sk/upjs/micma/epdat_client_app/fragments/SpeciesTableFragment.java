package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.Plant;
import sk.upjs.micma.epdat_client_app.PlantListViewModel;
import sk.upjs.micma.epdat_client_app.PlantOnClickListener;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.SpeciesAdapter;


public class SpeciesTableFragment extends Fragment implements PlantOnClickListener {
    private RecyclerView speciesRecyclerView;
    private PlantListViewModel viewModel;
    private Bundle searchInput;
    private SpeciesAdapter specAdapter;
    private TextView familyTextView;
    public SpeciesTableFragment() {
        // Required empty public constructor
    }
    public SpeciesTableFragment(Bundle searchInput){
        this.searchInput = searchInput;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_species_table, container, false);

        specAdapter = new SpeciesAdapter(this);
        speciesRecyclerView = view.findViewById(R.id.speciesRecyclerView);
        speciesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        speciesRecyclerView.setAdapter(specAdapter);

        familyTextView = view.findViewById(R.id.recHeaderTextView);
        familyTextView.setText("Searched results");

        viewModel = new ViewModelProvider(this).get(PlantListViewModel.class);
        viewModel.setSearchInput(searchInput);
        viewModel.getPlants().observe(this, plants -> {
            specAdapter.submitList(plants);
        });
        return view;
    }

    @Override
    public void onPlantClick(Plant plant) {
        ((MainActivity) getActivity()).showPlantInfoFragment(plant);
    }


}
