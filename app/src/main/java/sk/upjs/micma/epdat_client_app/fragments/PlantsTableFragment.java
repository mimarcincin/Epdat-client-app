package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.PlantListViewModel;
import sk.upjs.micma.epdat_client_app.PlantOnClickListener;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.adapters.SpeciesAdapter;

public class PlantsTableFragment extends Fragment implements PlantOnClickListener {
    private RecyclerView speciesRecyclerView;
    private PlantListViewModel viewModel;
    private Bundle searchInput;
    private SpeciesAdapter specAdapter;
    private Plant selectedPlant;
    private SwipeRefreshLayout swipyBoy;
    private boolean loggedIn;
    public PlantsTableFragment() {
    }
    public PlantsTableFragment(Bundle searchInput){
        this.searchInput = searchInput;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add_plant_t).setVisible(true);
        menu.findItem(R.id.add_record_t).setVisible(false);
        menu.findItem(R.id.edit_plant_t).setVisible(false);
        menu.findItem(R.id.delete_plant_t).setVisible(false);
        menu.findItem(R.id.edit_record_t).setVisible(false);
        menu.findItem(R.id.delete_record_t).setVisible(false);
        menu.findItem(R.id.refresh_t).setVisible(true);
        loggedIn = !((MainActivity) getActivity()).getToken().equals("");
        menu.findItem(R.id.login_t).setVisible(!loggedIn);
        menu.findItem(R.id.logout_t).setVisible(loggedIn);
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_species_table, container, false);
        setHasOptionsMenu(true);
        specAdapter = new SpeciesAdapter(this);
        specAdapter.setContext(getContext());
        speciesRecyclerView = view.findViewById(R.id.speciesRecyclerView);
        speciesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        speciesRecyclerView.setAdapter(specAdapter);
        swipyBoy = view.findViewById(R.id.swipeRefresh);
        swipyBoy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPlants();
            }
        });

        viewModel = new ViewModelProvider(this).get(PlantListViewModel.class);
        viewModel.setSearchInput(searchInput);
        viewModel.getPlants().observe(this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                specAdapter.submitList(plants);
            }
        });
        return view;
    }

    public void refreshPlants() {
        viewModel.refresh();
        swipyBoy.setRefreshing(false);
    }

    public void addPlantToList(Plant plant){
        viewModel.addPlant(plant);
    }

    public void removePlantFromList(Plant plant){
        viewModel.removePlant(selectedPlant);
    }

    public void updatePlantInList(Plant plant){
        viewModel.updatePlant(selectedPlant, plant);
    }

    @Override
    public void onPlantClick(Plant plant) {
        selectedPlant = plant;
        ((MainActivity) getActivity()).showPlantInfoFragment(plant);
    }


}
