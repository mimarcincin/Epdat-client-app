package sk.upjs.micma.epdat_client_app;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private EditText familyEditText;
    private EditText genusEditText;
    private EditText speciesEditText;
    private EditText tissueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        familyEditText = (EditText) findViewById(R.id.familyEditText);
        genusEditText = (EditText) findViewById(R.id.genusEditText);
        speciesEditText = (EditText) findViewById(R.id.speciesEditText);
        tissueEditText = (EditText) findViewById(R.id.tissueEditText);

        familyEditText.addTextChangedListener(familyWatcher);
        genusEditText.addTextChangedListener(genusWatcher);
    }

    public void openSpeciesActivity(View view) {
        Intent intent = new Intent(this, SpeciesTabActivity.class);

        String family = "" + familyEditText.getText().toString();
        String genus = "" + genusEditText.getText().toString();
        String species = "" + speciesEditText.getText().toString();
        String tissue = "" + tissueEditText.getText().toString();

        Bundle extras = new Bundle();
        extras.putString("family", family);
        extras.putString("genus", genus);
        extras.putString("species", species);
        extras.putString("tissue", tissue);

        intent.putExtras(extras);
        startActivity(intent);
    }


    TextWatcher familyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!familyEditText.getText().toString().equals("")){
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
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher genusWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!genusEditText.getText().toString().equals("")){
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
