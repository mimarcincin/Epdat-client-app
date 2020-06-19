package sk.upjs.micma.epdat_client_app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.R;

public class SearchFragment extends Fragment {

    private EditText genusEditText;
    private EditText speciesEditText;
    private EditText tissueEditText;
    private SearchableSpinner familySpinner;
    private Button clearButton;
    private FloatingActionButton searchFab;
    private boolean loggedIn;
    private int famPos;
    private String genus;
    private String species;
    private String tissue;

    public SearchFragment() {
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add_plant_t).setVisible(true);
        menu.findItem(R.id.add_record_t).setVisible(false);
        menu.findItem(R.id.edit_plant_t).setVisible(false);
        menu.findItem(R.id.delete_plant_t).setVisible(false);
        menu.findItem(R.id.edit_record_t).setVisible(false);
        menu.findItem(R.id.delete_record_t).setVisible(false);
        menu.findItem(R.id.refresh_t).setVisible(false);
        loggedIn = !((MainActivity) getActivity()).getToken().equals("");
        menu.findItem(R.id.login_t).setVisible(!loggedIn);
        menu.findItem(R.id.logout_t).setVisible(loggedIn);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (familySpinner != null){
        outState.putInt("familyPos", familySpinner.getSelectedItemPosition());
        outState.putString("genus", genusEditText.getText().toString()+"");
        outState.putString("species", speciesEditText.getText().toString()+"");
        outState.putString("tissue", tissueEditText.getText().toString()+"");}
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null){
        this.famPos = savedInstanceState.getInt("familyPos");
        this.genus = savedInstanceState.getString("genus");
        this.species = savedInstanceState.getString("species");
        this.tissue = savedInstanceState.getString("tissue");}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        genusEditText = view.findViewById(R.id.genusEditText);
        speciesEditText = view.findViewById(R.id.speciesEditText);
        tissueEditText = view.findViewById(R.id.tissueEditText);
        familySpinner = view.findViewById(R.id.familyBox);

        clearButton = view.findViewById(R.id.clearSearchButton);
        familySpinner.setSelection(0);
        searchFab = view.findViewById(R.id.search_fab);

        if (savedInstanceState!=null){
            familySpinner.setSelection(famPos);
            genusEditText.setText(genus);
            speciesEditText.setText(species);
            tissueEditText.setText(tissue);
        }

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSpeciesActivity();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familySpinner.setSelection(0);
                genusEditText.setText("");
                tissueEditText.setText("");
                speciesEditText.setText("");
                tissueEditText.setText("");
            }
        });

        familySpinner.setTitle("Select family");
        this.setUpFamilySpinner();
        genusEditText.addTextChangedListener(genusWatcher);
        return view;
    }

    private void setUpFamilySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.families_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familySpinner.setAdapter(adapter);
        familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(familySpinner.getSelectedItem().toString().equals("")||familySpinner.getSelectedItem().toString().equals("Select Family Name"))) {
                    genusEditText.setEnabled(true);
                    tissueEditText.setEnabled(true);
                } else {
                    genusEditText.setEnabled(false);
                    tissueEditText.setEnabled(false);
                    genusEditText.setText("");
                    tissueEditText.setText("");
                    speciesEditText.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void openSpeciesActivity() {
        String family;
        if (familySpinner.getSelectedItem()==null || familySpinner.getSelectedItem().toString().equals("Select Family Name")) {
            family = "";
        } else {
            family = "" + familySpinner.getSelectedItem().toString();
        }

        String genus = "" + genusEditText.getText().toString();
        String species = "" + speciesEditText.getText().toString();
        String tissue = "" + tissueEditText.getText().toString();

        Bundle extras = new Bundle();
        extras.putString("family", family);
        extras.putString("genus", genus);
        extras.putString("species", species);
        extras.putString("tissue", tissue);

        ((MainActivity) getActivity()).showSpeciesTableFragment(extras);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    TextWatcher genusWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!genusEditText.getText().toString().equals("")) {
                speciesEditText.setEnabled(true);
            } else {
                speciesEditText.setEnabled(false);
                speciesEditText.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
