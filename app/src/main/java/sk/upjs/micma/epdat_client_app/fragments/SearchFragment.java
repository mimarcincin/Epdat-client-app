package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.R;

public class SearchFragment extends Fragment {

    private EditText familyEditText;
    private EditText genusEditText;
    private EditText speciesEditText;
    private EditText tissueEditText;
    private SearchableSpinner familySpinner;
    private Button searchButton;
    private Button addButton;
    private Button clearButton;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        genusEditText = (EditText) view.findViewById(R.id.genusEditText);
        speciesEditText = (EditText) view.findViewById(R.id.speciesEditText);
        tissueEditText = (EditText) view.findViewById(R.id.tissueEditText);
        familySpinner = (SearchableSpinner) view.findViewById(R.id.familyBox);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        addButton = (Button) view.findViewById(R.id.addPlantButton);
        clearButton = view.findViewById(R.id.clearSearchButton);
        familySpinner.setSelection(0);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showAddPlantFragment();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpeciesActivity();
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
