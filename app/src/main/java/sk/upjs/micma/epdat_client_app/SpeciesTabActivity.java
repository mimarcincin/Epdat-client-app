package sk.upjs.micma.epdat_client_app;

//import android.arch.lifecycle.ViewModelProvider;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SpeciesTabActivity extends AppCompatActivity {
    private RecyclerView speciesRecyclerView;
    private PlantListViewModel viewModel;
    private Bundle searchInput;
    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_species);
        searchInput = getIntent().getExtras();
        SpeciesAdapter specAdapter = new SpeciesAdapter();

        speciesRecyclerView = findViewById(R.id.speciesRecyclerView);
        speciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        speciesRecyclerView.setAdapter(specAdapter);

        viewModel = new ViewModelProvider(this).get(PlantListViewModel.class);
        viewModel.setSearchInput(searchInput);
        viewModel.getPlants().observe(this, plants -> {
            specAdapter.submitList(plants);
        });


    }
}
