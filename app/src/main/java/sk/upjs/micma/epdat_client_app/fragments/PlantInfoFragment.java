package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private RecyclerView recordsRecyclerView;
    private RecordListViewModel viewModel;
    private RecordsAdapter recAdapter;

    private Button editPlantButton;
    private Button deletePlantButton;
    private Button addRecordButton;

    public PlantInfoFragment(){}

    public PlantInfoFragment(Plant plant) {
    this.plant = plant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_info, container, false);

        editPlantButton = view.findViewById(R.id.editPlantButton);
        deletePlantButton = view.findViewById(R.id.deletePlantButton);
        addRecordButton = view.findViewById(R.id.addRecordButton);

        familyNameInfo = view.findViewById(R.id.familyNameInfo);
        genusNameInfo = view.findViewById(R.id.genusNameInfo);
        speciesNameInfo = view.findViewById(R.id.speciesNameInfo);
        authorityNameInfo = view.findViewById(R.id.authorityNameInfo);
        noticeNameInfo = view.findViewById(R.id.noticeNameInfo);
        createdAtPlantInfo =view.findViewById(R.id.createdPlantAtInfo);
        updatedAtPlantInfo = view.findViewById(R.id.updatedPlantAtNameInfo);

        familyNameInfo.setText("Family: "+plant.getFamily());
        genusNameInfo.setText("Genus: "+plant.getGenus());
        speciesNameInfo.setText("Species: "+plant.getSpecies());
        authorityNameInfo.setText("Authority: "+plant.getAuthority());
        noticeNameInfo.setText("Notice: "+plant.getNotice());
        createdAtPlantInfo.setText(plant.getCreatedAt());
        updatedAtPlantInfo.setText(plant.getUpdatedAt());

        editPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showEditPlantFragment(plant);
            }
        });

        deletePlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Title")
                        .setMessage("Do you really want to delete this species?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deletePlant();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showAddRecordFragment(plant);
            }
        });


        recAdapter = new RecordsAdapter(this);
        recordsRecyclerView = view.findViewById(R.id.recordsRecyclerView);
        recordsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recordsRecyclerView.setAdapter(recAdapter);

        viewModel = new ViewModelProvider(this).get(RecordListViewModel.class);
        viewModel.setPlant(plant);
        viewModel.getRecords().observe(this, records -> {
            recAdapter.submitList(records);
        });

        return view;
    }

    private void deletePlant() {
        Call<Void>  call = databaseApi.deletePlant(plant.getId()+"");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Plant successfully deleted", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();;
            }
        });
    }

    @Override
    public void onRecordClick(Record record) {
        ((MainActivity) getActivity()).showRecordInfoFragment(record, plant);
    }
}
