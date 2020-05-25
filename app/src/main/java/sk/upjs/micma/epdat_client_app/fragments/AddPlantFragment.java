package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.DatabaseApi;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Plant;

public class AddPlantFragment extends Fragment {
    private TextView editTitleTextView;
    private TextView plantplant;

    private EditText genusEdit;
    private EditText speciesEdit;
    private EditText authorityEdit;
    private EditText noticeEdit;

    private SearchableSpinner familySpinner;

    private Button backButton;
    private Button clearButton;
    private Button applyButton;

    private DatabaseApi databaseApi = DatabaseApi.API;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_edit, container, false);

        editTitleTextView = view.findViewById(R.id.editPlantTitleTextView);
        plantplant = view.findViewById(R.id.plantplant2TextView);

        genusEdit = view.findViewById(R.id.genusPlantEditText);
        speciesEdit = view.findViewById(R.id.speciesPlantEditText);
        authorityEdit = view.findViewById(R.id.authorityPlantEditText);
        noticeEdit = view.findViewById(R.id.noticePlantEditText);

        familySpinner = (SearchableSpinner) view.findViewById(R.id.familyEditSpinner);
        familySpinner.setTitle("Select family");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.families_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        familySpinner.setAdapter(adapter);

        backButton = view.findViewById(R.id.backPlantEditButton);
        clearButton = view.findViewById(R.id.clearPlantEditButton);
        applyButton = view.findViewById(R.id.applyPlantEditButton);
        applyButton.setText("Add Species");

        editTitleTextView.setText("Add");
        plantplant.setText("new species");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familySpinner.setSelection(0);
                genusEdit.setText("");
                speciesEdit.setText("");
                authorityEdit.setText("");
                noticeEdit.setText("");
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familySpinner.getSelectedItem().toString().equals("") || genusEdit.getText().toString().equals("")
                        || speciesEdit.getText().toString().equals("") || authorityEdit.getText().toString().equals("")
                        || familySpinner.getSelectedItem().toString().equals("Select Family Name")) {
                    Toast.makeText(getContext(), "Required fields: Family, Genus, Species, Authority", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Sending", Toast.LENGTH_LONG);
                    postNewPlant();
                }
            }
        });

        return view;
    }

    private void postNewPlant() {
        Plant plant = new Plant();
        plant.setFamily(familySpinner.getSelectedItem().toString());
        plant.setGenus(genusEdit.getText().toString());
        plant.setSpecies(speciesEdit.getText().toString());
        plant.setAuthority(authorityEdit.getText().toString());
        plant.setNotice("" + noticeEdit.getText().toString());
        Call<Plant> call = databaseApi.addPlant(plant);
        call.enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Species successfully added to database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
