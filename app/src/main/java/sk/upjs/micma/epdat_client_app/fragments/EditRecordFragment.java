package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class EditRecordFragment extends Fragment {
    private Record record;
    private Plant plant;
    private Bundle editingRecord;
    private TextView plantplant;
    private CheckBox endopolyCheckBox;
    private EditText chromosomeNumberEditText;
    private EditText ploidyLevelEditText;
    private EditText indexTypeEditText;
    private EditText numberEditText;
    private EditText tissueEditText;
    private Button clearButton;
    private Button applyButton;
    private EditText sourceEditText;
    private DatabaseApi databaseApi = DatabaseApi.API;

    public EditRecordFragment() {
    }

    public EditRecordFragment(Record record, Plant plant) {
        this.record = record;
        this.plant = plant;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        editingRecord = new Bundle();
        editingRecord.putBoolean("endo", endopolyCheckBox.isChecked());
        editingRecord.putString("chrom", chromosomeNumberEditText.getText().toString() + "");
        editingRecord.putString("ploi", ploidyLevelEditText.getText().toString() + "");
        editingRecord.putString("iType", indexTypeEditText.getText().toString() + "");
        editingRecord.putString("numb", numberEditText.getText().toString() + "");
        editingRecord.putString("tiss", tissueEditText.getText().toString() + "");
        editingRecord.putString("sour", sourceEditText.getText().toString()+"");
        outState.putBundle("addRec", editingRecord);
        outState.putSerializable("plant", plant);
        outState.putSerializable("record", record);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add_plant_t).setVisible(false);
        menu.findItem(R.id.add_record_t).setVisible(false);
        menu.findItem(R.id.edit_plant_t).setVisible(false);
        menu.findItem(R.id.delete_plant_t).setVisible(false);
        menu.findItem(R.id.edit_record_t).setVisible(false);
        menu.findItem(R.id.delete_record_t).setVisible(false);
        menu.findItem(R.id.refresh_t).setVisible(false);
        menu.findItem(R.id.login_t).setVisible(false);
        menu.findItem(R.id.logout_t).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            plant = (Plant) savedInstanceState.getSerializable("plant");
            editingRecord = savedInstanceState.getBundle("addRec");
            record = (Record) savedInstanceState.getSerializable("record");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_edit, container, false);
        setHasOptionsMenu(true);
        plantplant = view.findViewById(R.id.plantplantTextView);
        endopolyCheckBox = view.findViewById(R.id.endoCheckBox);
        chromosomeNumberEditText = view.findViewById(R.id.chromNumEditText);
        ploidyLevelEditText = view.findViewById(R.id.ploidyLevEditText);
        indexTypeEditText = view.findViewById(R.id.indextTypeEditText);
        numberEditText = view.findViewById(R.id.numberEditText);
        tissueEditText = view.findViewById(R.id.tissueRecordEditText);
        clearButton = view.findViewById(R.id.clearRecordEditButton);
        applyButton = view.findViewById(R.id.applyRecordEditButton);
        sourceEditText = view.findViewById(R.id.sourceRecordEditText);
        applyButton.setText("Apply changes");
        returnDefaults();
        if (editingRecord != null) {
            endopolyCheckBox.setChecked(editingRecord.getBoolean("endo"));
            chromosomeNumberEditText.setText(editingRecord.getString("chrom") + "");
            ploidyLevelEditText.setText(editingRecord.getString("ploi") + "");
            indexTypeEditText.setText(editingRecord.getString("iType") + "");
            numberEditText.setText(editingRecord.getString("numb") + "");
            tissueEditText.setText(editingRecord.getString("tiss") + "");
            sourceEditText.setText(editingRecord.getString("sour")+"");
        }
        clearButton.setText("Set defaults");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnDefaults();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Edit record")
                        .setMessage("Do you really want to update this record?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                updateRecord();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        return view;
    }

    private void updateRecord() {
        Record record = new Record();
        int end = 0;
        if (endopolyCheckBox.isChecked()) end = 1;
        record.setEndopolyploidy(end);
        record.setChromosomeNumber(chromosomeNumberEditText.getText().toString() + "");
        String ploid = ploidyLevelEditText.getText().toString();
        if (!ploid.equals("")) {
            record.setPloidyLevel(Integer.parseInt(ploid));
        } else {
            record.setPloidyLevel(-1);
        }
        record.setIndexType(indexTypeEditText.getText().toString() + "");
        String num = numberEditText.getText().toString();
        if (!num.equals("")) {
            record.setNumber(Integer.parseInt(num));
        } else {
            record.setNumber(-1);
        }
        record.setTissue(tissueEditText.getText().toString() + "");
        Call<Record> call = databaseApi.updateRecord(plant.getId() + "", this.record.getId() + "", record,
                "Bearer "+((MainActivity) getActivity()).getToken());
        call.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Record successfully edited", Toast.LENGTH_SHORT).show();
                    ((RecordInfoFragment) getFragmentManager().findFragmentByTag("RECORD_INFO_F")).refreshRecordInfo(response.body());
                    ((PlantInfoFragment) getFragmentManager().findFragmentByTag("PLANT_INFO_F")).updateRecordInList(response.body());
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).resetToken();
                }
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void returnDefaults() {
        plantplant.setText(plant.getGenus() + " " + plant.getSpecies());
        endopolyCheckBox.setChecked(record.getEndopolyploidy() == 1);
        chromosomeNumberEditText.setText("" + record.getChromosomeNumber());
        ploidyLevelEditText.setText("" + record.getPloidyLevel());
        indexTypeEditText.setText("" + record.getIndexType());
        numberEditText.setText("" + record.getNumber());
        tissueEditText.setText("" + record.getTissue());
        sourceEditText.setText(""+record.getSource());
    }


}

