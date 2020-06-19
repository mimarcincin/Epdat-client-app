package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.DatabaseApi;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.RecordListViewModel;
import sk.upjs.micma.epdat_client_app.RecordOnClickListener;
import sk.upjs.micma.epdat_client_app.adapters.RecordsAdapter;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Record;

public class PlantInfoFragment extends Fragment implements RecordOnClickListener {
    private Plant plant;
    private DatabaseApi databaseApi = DatabaseApi.API;

    private TextView familyNameInfo;
    private TextView genusNameInfo;
    private TextView speciesNameInfo;
    private TextView authorityNameInfo;
    private TextView noticeNameInfo;
    private TextView createdAtPlantInfo;
    private TextView updatedAtPlantInfo;

    private SwipeRefreshLayout swipey;
    private RecyclerView recordsRecyclerView;
    private RecordListViewModel viewModel;
    private RecordsAdapter recAdapter;
    private boolean loggedIn;
    private Record selectedRecord;

    public PlantInfoFragment() {
    }
    public PlantInfoFragment(Plant plant) {
        this.plant = plant;
    }
    public Plant getPlant() {
        return plant;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("plant", plant);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            this.plant =(Plant) savedInstanceState.getSerializable("plant");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_info, container, false);
        setHasOptionsMenu(true);
        familyNameInfo = view.findViewById(R.id.familyNameInfo);
        genusNameInfo = view.findViewById(R.id.genusNameInfo);
        speciesNameInfo = view.findViewById(R.id.speciesNameInfo);
        authorityNameInfo = view.findViewById(R.id.authorityNameInfo);
        noticeNameInfo = view.findViewById(R.id.noticeNameInfo);
        createdAtPlantInfo = view.findViewById(R.id.createdPlantAtInfo);
        updatedAtPlantInfo = view.findViewById(R.id.updatedPlantAtNameInfo);

        refreshPlantInfo(plant);

        recAdapter = new RecordsAdapter(this);
        recordsRecyclerView = view.findViewById(R.id.recordsRecyclerView);
        recordsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recordsRecyclerView.setAdapter(recAdapter);

        viewModel = new ViewModelProvider(this).get(RecordListViewModel.class);
        viewModel.setPlant(plant);
        viewModel.getRecords().observe(this, records -> {
            recAdapter.submitList(records);
        });

        swipey = view.findViewById(R.id.swipeSwipe);
        swipey.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecords();
                swipey.setRefreshing(false);
            }
        });

        return view;
    }
    public void deleteClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete species")
                .setMessage("Do you really want to delete this species?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deletePlant();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add_plant_t).setVisible(false);
        menu.findItem(R.id.add_record_t).setVisible(true);
        menu.findItem(R.id.edit_plant_t).setVisible(true);
        menu.findItem(R.id.delete_plant_t).setVisible(true);
        menu.findItem(R.id.edit_record_t).setVisible(false);
        menu.findItem(R.id.delete_record_t).setVisible(false);
        menu.findItem(R.id.refresh_t).setVisible(true);
        loggedIn = !((MainActivity) getActivity()).getToken().equals("");
        menu.findItem(R.id.login_t).setVisible(!loggedIn);
        menu.findItem(R.id.logout_t).setVisible(loggedIn);
        super.onPrepareOptionsMenu(menu);

    }

    public void refreshPlantInfo(Plant plant) {
        this.plant = plant;
        familyNameInfo.setText("Family: " + plant.getFamily());
        genusNameInfo.setText("Genus: " + plant.getGenus());
        speciesNameInfo.setText("Species: " + plant.getSpecies());
        authorityNameInfo.setText("Authority: " + plant.getAuthority());
        String not = "Notice: ";
        if (plant.getNotice()!=null && !plant.getNotice().equals("") && !plant.getNotice().equals("null")) {not+=plant.getNotice();} else {not+="-";}
        noticeNameInfo.setText(not);
        createdAtPlantInfo.setText("Created at: "+formatStupidDate(plant.getCreatedAt()));
        updatedAtPlantInfo.setText("Updated at: "+formatStupidDate(plant.getUpdatedAt()));

    }

    private void deletePlant() {
        Call<Void> call = databaseApi.deletePlant(plant.getId() + "", "Bearer "+((MainActivity) getActivity()).getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Plant successfully deleted", Toast.LENGTH_SHORT).show();
                    ((PlantsTableFragment) getFragmentManager().findFragmentByTag("PLANTS_TAB_F")).removePlantFromList(plant);
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).resetToken();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRecordClick(Record record) {
        selectedRecord = record;
        ((MainActivity) getActivity()).showRecordInfoFragment(record, plant);
    }

    public void removeRecordFromList(Record record) {
        viewModel.removeRecord(selectedRecord);
    }

    public void addRecordToList(Record record) {
        viewModel.addRecord(record);
    }

    public void updateRecordInList(Record record) {
        System.out.println("Selected record: "+selectedRecord.toString());
        System.out.println("real record: "+record.toString());
        viewModel.updateRecord(selectedRecord,record);
    }

    public void refreshRecords() {
        viewModel.refresh();
        viewModel.getRecords().observe(getActivity(), records -> {
            recAdapter.submitList(records);
        });
        refreshPlantInfo(plant);
    }

    public String formatStupidDate(String stupidDate){
        String year = "";
        String month = "";
        String day = "";
        String time = "";
        for (int i = 0; i < 4; i++) {
            year+=stupidDate.charAt(i);
        }
        for (int i = 5; i < 7; i++) {
            month+=stupidDate.charAt(i);
        }
        for (int i = 8; i < 10; i++) {
            day+=stupidDate.charAt(i);
        }
        for (int i = 11; i < 16; i++) {
            time+=stupidDate.charAt(i);
        }
        String prettyDate = day+". "+month+". "+year+"  "+time;
        return prettyDate;
    }
}
