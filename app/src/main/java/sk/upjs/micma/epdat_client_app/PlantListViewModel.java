package sk.upjs.micma.epdat_client_app;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.models.Plant;

public class PlantListViewModel extends ViewModel {


    private Bundle searchInput = new Bundle();
    private DatabaseApi databaseApi = DatabaseApi.API;
    private MutableLiveData<List<Plant>> plants;

    public LiveData<List<Plant>> getPlants() {
        if (plants == null) {
            plants = new MutableLiveData<>();
            refresh();
        }
        return plants;
    }
    public void addPlant(Plant plant){
        ArrayList<Plant> newValue = new
                ArrayList<>(plants.getValue());
        newValue.add(plant);
        plants.postValue(newValue);
    }
    public void removePlant(Plant plant){
        ArrayList<Plant> newValue = new
                ArrayList<>(plants.getValue());
        newValue.remove(plant);
        plants.postValue(newValue);
    }
    public void updatePlant(Plant oldP, Plant newP){
        ArrayList<Plant> newValue = new
                ArrayList<>(plants.getValue());
        newValue.remove(oldP);
        newValue.add(newP);
        plants.postValue(newValue);
    }

    private void refresh() {
        Call<List<Plant>> call;
        String family = searchInput.get("family").toString();
        String genus = searchInput.get("genus").toString();
        String species = searchInput.get("species").toString();
        String tissue = searchInput.get("tissue").toString();

        if (!family.equals("")) {
            if (!genus.equals("")) {
                if (!species.equals("")){
                    if(!tissue.equals("")){
                        call = databaseApi.getPlantsByFamilyAndGenusAndSpeciesAndTissue(family, genus, species, tissue);
                    } else {
                        call = databaseApi.getPlantsByFamilyAndGenusAndSpecies(family, genus, species);
                    }
                } else {
                    if(!tissue.equals("")){
                        call = databaseApi.getPlantsByFamilyAndGenusAndTissue(family, genus, tissue);
                    } else {
                        call = databaseApi.getPlantsByFamilyAndGenus(family, genus);
                    }
                }
            } else {
                if(!tissue.equals("")){
                    call = databaseApi.getPlantsByFamilyAndTissue(family, tissue);
                } else {
                    call = databaseApi.getPlantsByFamily(family);
                }
            }
        } else {
            call = databaseApi.getPlants();
        }


        call.enqueue(
                new Callback<List<Plant>>() {
                    @Override
                    public void onResponse(Call<List<Plant>> call,
                                           Response<List<Plant>> response) {
                        if (response.isSuccessful()) {
                            PlantListViewModel.this.plants.postValue(response.body());
                            System.out.println("this should work: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plant>> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("NOT WORKIIIIIIIIIIIIIIINGGGGGGGGG");
                    }
                }


        );

    }

    public void setSearchInput(Bundle searchInput) {
        this.searchInput = searchInput;
    }
}
