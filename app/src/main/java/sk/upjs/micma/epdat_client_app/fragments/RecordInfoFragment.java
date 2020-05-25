package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.DatabaseApi;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;

public class RecordInfoFragment extends Fragment {
    private Record record;
    private Plant plant;
    private TextView recordIdTextView;
    private TextView endopolyploidyTextView;
    private TextView chromosomeNumberTextView;
    private TextView ploidyLevelTextView;
    private TextView numberTextView;
    private TextView indexTypeTextView;
    private TextView tissueTextView;
    private TextView createdAtRecordTextView;
    private TextView updatedAtRecordTextView;

    private Button editRecordButton;
    private Button backButton;
    private Button deleteButton;
    private DatabaseApi databaseApi = DatabaseApi.API;

    public RecordInfoFragment(Record record, Plant plant) {
        this.record = record;
        this.plant = plant;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_info, container, false);

        recordIdTextView = view.findViewById(R.id.RecordIdTextView2);
        endopolyploidyTextView = view.findViewById(R.id.EndopolyploidyTextView2);
        chromosomeNumberTextView = view.findViewById(R.id.ChromosomeNumberTextView2);
        ploidyLevelTextView = view.findViewById(R.id.PloidyLevelTextView2);
        numberTextView = view.findViewById(R.id.NumberTextView2);
        indexTypeTextView = view.findViewById(R.id.IndexTypeTextView2);
        tissueTextView = view.findViewById(R.id.TissueTextView2);
        createdAtRecordTextView = view.findViewById(R.id.CreatedAtTextView2);
        updatedAtRecordTextView = view.findViewById(R.id.UpdatedAtTextView2);
        editRecordButton = view.findViewById(R.id.editRecordButton);
        backButton = view.findViewById(R.id.backRecordButton);
        deleteButton = view.findViewById(R.id.deleteRecordButton);

        recordIdTextView.setText("ID: " + record.getId());
        endopolyploidyTextView.setText("Endopolyploidy: " + record.getEndopolyploidy());
        chromosomeNumberTextView.setText("Chromosome Number: " + record.getChromosomeNumber());
        ploidyLevelTextView.setText("Ploidy Level: " + record.getPloidyLevel());
        numberTextView.setText("Number: " + record.getNumber());
        indexTypeTextView.setText("Intex Type: " + record.getIndexType());
        tissueTextView.setText("Tissue: " + record.getTissue());
        createdAtRecordTextView.setText("Created at: " + record.getCreatedAt());
        updatedAtRecordTextView.setText("Updated at: " + record.getUpdatedAt());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        editRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showEditRecordFragment(record, plant);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Title")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteRecord();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return view;
    }

    private void deleteRecord() {
        Call<Void> call = databaseApi.deleteRecord(plant.getId()+"", record.getId()+"");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Record successfully deleted", Toast.LENGTH_SHORT).show();
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
}
