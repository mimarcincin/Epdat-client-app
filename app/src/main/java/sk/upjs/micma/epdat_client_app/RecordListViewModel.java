package sk.upjs.micma.epdat_client_app;

import org.json.JSONArray;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;

public class RecordListViewModel extends ViewModel {
    private Plant plant;
    private DatabaseApi databaseApi = DatabaseApi.API;
    private MutableLiveData<List<Record>> records;

    public LiveData<List<Record>> getRecords() {
        if (records == null) {
            records = new MutableLiveData<>();
            refresh();
        }
        return records;
    }
    public void addRecord(Record record){
        ArrayList<Record> newValue = new
                ArrayList<>(records.getValue());
        newValue.add(record);
        records.postValue(newValue);
    }
    public void removeRecord(Record record){
        ArrayList<Record> newValue = new
                ArrayList<>(records.getValue());
        newValue.remove(record);
        records.postValue(newValue);
    }
    public void updateRecord(Record oldR, Record newR){
        ArrayList<Record> newValue = new
                ArrayList<>(records.getValue());
        newValue.remove(oldR);
        newValue.add(newR);
        records.postValue(newValue);
    }
    private void refresh() {
        Call<List<Record>> call = databaseApi.getRecordsByPlantId(""+plant.getId());
        System.out.println("trying with this id: "+plant.getId());
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                if (response.isSuccessful()) {

                    RecordListViewModel.this.records.postValue(response.body());
                    System.out.println("this should work: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Didnt work.");
            }
        });
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
