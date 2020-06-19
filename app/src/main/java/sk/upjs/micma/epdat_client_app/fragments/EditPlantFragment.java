package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.util.Arrays;
import java.util.List;
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

public class EditPlantFragment extends Fragment {
    private Plant plant;
    private TextView plantplant;
    private int familyPosition;
    private Plant editingPlant;
    private int editingFamPos;
    private EditText genusEdit;
    private EditText speciesEdit;
    private EditText authorityEdit;
    private EditText noticeEdit;

    private SearchableSpinner familySpinner;

    private Button clearButton;
    private Button applyButton;

    private DatabaseApi databaseApi = DatabaseApi.API;
    private String[] myResArray;
    public EditPlantFragment(){}
    public EditPlantFragment(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        editingFamPos = familySpinner.getSelectedItemPosition();
        editingPlant = new Plant();
        editingPlant.setFamily("");
        editingPlant.setGenus(genusEdit.getText().toString()+"");
        editingPlant.setSpecies(speciesEdit.getText().toString()+"");
        editingPlant.setAuthority(authorityEdit.getText().toString()+"");
        editingPlant.setNotice(noticeEdit.getText().toString()+"");
        outState.putSerializable("editPlant", editingPlant);
        outState.putInt("editFamPos", editingFamPos);
        outState.putSerializable("plant", plant);
        outState.putInt("famPos", familyPosition);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            editingPlant = (Plant) savedInstanceState.getSerializable("editPlant");
            editingFamPos = savedInstanceState.getInt("editFamPos");
            plant = (Plant) savedInstanceState.getSerializable("plant");
            familyPosition = savedInstanceState.getInt("famPos");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_edit, container, false);
        setHasOptionsMenu(true);
        plantplant = view.findViewById(R.id.plantplant2TextView);

        genusEdit = view.findViewById(R.id.genusPlantEditText);
        speciesEdit = view.findViewById(R.id.speciesPlantEditText);
        authorityEdit = view.findViewById(R.id.authorityPlantEditText);
        noticeEdit = view.findViewById(R.id.noticePlantEditText);

        familySpinner = (SearchableSpinner) view.findViewById(R.id.familyEditSpinner);
        familySpinner.setTitle("Select family");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.families_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myResArray = getResources().getStringArray(R.array.families_array);
        List<String> myResArrayList = Arrays.asList(myResArray);

        familyPosition = myResArrayList.indexOf(plant.getFamily());

        familySpinner.setAdapter(adapter);

        clearButton = view.findViewById(R.id.clearPlantEditButton);
        applyButton = view.findViewById(R.id.applyPlantEditButton);
        applyButton.setText("Apply");
        clearButton.setText("Set defaults");
        setDefaults();
        plantplant.setText(plant.getGenus()+" "+plant.getSpecies());
        applyButton.setText("Apply changes");

        if(editingPlant!=null){
            familySpinner.setSelection(editingFamPos);
            genusEdit.setText(editingPlant.getGenus()+"");
            speciesEdit.setText(editingPlant.getSpecies()+"");
            authorityEdit.setText(editingPlant.getAuthority()+"");
            noticeEdit.setText(editingPlant.getNotice()+"");
        }

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults();
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

                    new AlertDialog.Builder(getContext())
                            .setTitle("Edit plant")
                            .setMessage("Do you really want to edit this plant?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    updatePlant();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
        return view;
    }
    private void setDefaults(){
        System.out.println("fam position: "+familyPosition);

        familySpinner.setSelection(familyPosition);
        genusEdit.setText(plant.getGenus());
        speciesEdit.setText(plant.getSpecies());
        authorityEdit.setText(plant.getAuthority());
        noticeEdit.setText(plant.getNotice()+"");
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

    private void updatePlant() {
        Plant plantUpdate = new Plant();
        plantUpdate.setFamily(familySpinner.getSelectedItem().toString());
        plantUpdate.setGenus(genusEdit.getText().toString());
        plantUpdate.setSpecies(speciesEdit.getText().toString());
        plantUpdate.setAuthority(authorityEdit.getText().toString());
        plantUpdate.setNotice("" + noticeEdit.getText().toString());
        Call<Plant> call = databaseApi.updatePlant(plant.getId()+"", plantUpdate, "Bearer "+((MainActivity) getActivity()).getToken());
        call.enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Species successfully edited", Toast.LENGTH_SHORT).show();
                    ((PlantsTableFragment)getFragmentManager().findFragmentByTag("PLANTS_TAB_F")).updatePlantInList(response.body());
                    ((PlantInfoFragment)getFragmentManager().findFragmentByTag("PLANT_INFO_F")).refreshPlantInfo(response.body());
                    getFragmentManager().popBackStack();
                } else {
                    ((MainActivity) getActivity()).resetToken();
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
