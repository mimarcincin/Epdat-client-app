package sk.upjs.micma.epdat_client_app.fragments;

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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.DatabaseApi;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;

public class AddRecordFragment extends Fragment {
    private Plant plant;
    private Bundle addingRecord;
    private TextView plantplant;
    private CheckBox endopolyCheckBox;
    private EditText chromosomeNumberEditText;
    private EditText ploidyLevelEditText;
    private EditText indexTypeEditText;
    private EditText numberEditText;
    private EditText tissueEditText;
    private Button clearButton;
    private Button applyButton;
    private DatabaseApi databaseApi = DatabaseApi.API;
    public AddRecordFragment(){}
    public AddRecordFragment(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        addingRecord = new Bundle();
        addingRecord.putBoolean("endo", endopolyCheckBox.isChecked());
        addingRecord.putString("chrom", chromosomeNumberEditText.getText().toString()+"");
        addingRecord.putString("ploi", ploidyLevelEditText.getText().toString()+"");
        addingRecord.putString("iType", indexTypeEditText.getText().toString()+"");
        addingRecord.putString("numb", numberEditText.getText().toString()+"");
        addingRecord.putString("tiss", tissueEditText.getText().toString()+"");
        outState.putBundle("addRec", addingRecord);
        outState.putSerializable("plant", plant);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            plant = (Plant) savedInstanceState.getSerializable("plant");
            addingRecord = savedInstanceState.getBundle("addRec");
        }
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
        super.onPrepareOptionsMenu(menu);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        plantplant.setText(plant.getGenus() + " " + plant.getSpecies());
            if (addingRecord!=null){
                endopolyCheckBox.setChecked(addingRecord.getBoolean("endo"));
                chromosomeNumberEditText.setText(addingRecord.getString("chrom")+"");
                ploidyLevelEditText.setText(addingRecord.getString("ploi")+"");
                indexTypeEditText.setText(addingRecord.getString("iType")+"");
                numberEditText.setText(addingRecord.getString("numb")+"");
                tissueEditText.setText(addingRecord.getString("tiss")+"");
            }
        applyButton.setText("Add record");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endopolyCheckBox.setChecked(true);
                chromosomeNumberEditText.setText("");
                ploidyLevelEditText.setText("");
                indexTypeEditText.setText("");
                numberEditText.setText("");
                tissueEditText.setText("");
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tissueEditText.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Required field: Tissue", Toast.LENGTH_SHORT);
                } else {
                    addRecord();
                }


            }
        });
        return view;
    }

    private void addRecord() {
        Record record = new Record();
        int end = 0;
        if (endopolyCheckBox.isChecked()) end = 1;
        record.setEndopolyploidy(end);
        record.setChromosomeNumber(chromosomeNumberEditText.getText().toString() + "");
        String ploid = ploidyLevelEditText.getText().toString();
        if(!ploid.equals("")) {
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
        Call<Record> call = databaseApi.addRecord(plant.getId() + "", record);
        call.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Record successfully added", Toast.LENGTH_SHORT).show();
                    ((PlantInfoFragment)getFragmentManager().findFragmentByTag("PLANT_INFO_F")).addRecordToList(response.body());
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
