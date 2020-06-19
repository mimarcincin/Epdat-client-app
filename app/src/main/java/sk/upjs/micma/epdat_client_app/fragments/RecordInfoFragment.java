package sk.upjs.micma.epdat_client_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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
    private TextView sourceTextView;
    private DatabaseApi databaseApi = DatabaseApi.API;
    private boolean loggedIn;
    public RecordInfoFragment(){}
    public RecordInfoFragment(Record record, Plant plant) {
        this.record = record;
        this.plant = plant;
    }
    public Record getRecord() {
        return record;
    }
    public Plant getPlant() {
        return plant;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add_plant_t).setVisible(false);
        menu.findItem(R.id.add_record_t).setVisible(false);
        menu.findItem(R.id.edit_plant_t).setVisible(false);
        menu.findItem(R.id.delete_plant_t).setVisible(false);
        menu.findItem(R.id.edit_record_t).setVisible(true);
        menu.findItem(R.id.delete_record_t).setVisible(true);
        menu.findItem(R.id.refresh_t).setVisible(false);
        loggedIn = !((MainActivity) getActivity()).getToken().equals("");
        menu.findItem(R.id.login_t).setVisible(!loggedIn);
        menu.findItem(R.id.logout_t).setVisible(loggedIn);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            this.plant = (Plant) savedInstanceState.getSerializable("plant");
            this.record = (Record) savedInstanceState.getSerializable("record");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("record", record);
        outState.putSerializable("plant", plant);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_info, container, false);
        setHasOptionsMenu(true);
        recordIdTextView = view.findViewById(R.id.RecordIdTextView2);
        endopolyploidyTextView = view.findViewById(R.id.EndopolyploidyTextView2);
        chromosomeNumberTextView = view.findViewById(R.id.ChromosomeNumberTextView2);
        ploidyLevelTextView = view.findViewById(R.id.PloidyLevelTextView2);
        numberTextView = view.findViewById(R.id.NumberTextView2);
        indexTypeTextView = view.findViewById(R.id.IndexTypeTextView2);
        tissueTextView = view.findViewById(R.id.TissueTextView2);
        sourceTextView = view.findViewById(R.id.SourceTextView2);
        createdAtRecordTextView = view.findViewById(R.id.CreatedAtTextView2);
        updatedAtRecordTextView = view.findViewById(R.id.UpdatedAtTextView2);

        refreshRecordInfo(record);

        return view;
    }
    public void deleteClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete record")
                .setMessage("Do you really want to delete this record?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteRecord();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void deleteRecord() {
        Call<Void> call = databaseApi.deleteRecord(plant.getId()+"", record.getId()+"",
                "Bearer "+((MainActivity) getActivity()).getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Record successfully deleted", Toast.LENGTH_SHORT).show();
                    ((PlantInfoFragment) getFragmentManager().findFragmentByTag("PLANT_INFO_F")).removeRecordFromList(record);
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).resetToken();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();;
            }
        });
    }

    public void refreshRecordInfo(Record record) {
        this.record = record;
        recordIdTextView.setText("ID: " + record.getId());
        boolean end = false;
        if (record.getEndopolyploidy()==1) end = true;
        endopolyploidyTextView.setText("Endopolyploidy: "+end);
        String chrom = "Chrom. num.: ";
        if (record.getChromosomeNumber()!=null && !record.getChromosomeNumber().equals("")) {chrom+=record.getChromosomeNumber();} else {chrom+="-";}
        chromosomeNumberTextView.setText(chrom);
        String ploid = "Ploidy level: ";
        if (record.getPloidyLevel()!=-1 && record.getPloidyLevel()!=0) {ploid+=record.getPloidyLevel()+"x";} else {ploid+="-";}
        ploidyLevelTextView.setText(ploid);
        String numb = "Number: ";
        if (record.getNumber()!=-1 && record.getNumber()!=-0) {numb+=record.getNumber();} else {numb+="-";}
        numberTextView.setText(numb);
        String itype = "Index type: ";
        if(record.getIndexType()!=null && !record.getIndexType().equals("")) {itype+=record.getIndexType();} else {itype+="-";}
        indexTypeTextView.setText(itype);
        String source = "Source: ";
        if(record.getSource()!=null && !record.getSource().equals("")) {source+=record.getSource();} else {source+="-";}
        sourceTextView.setText(source);
        tissueTextView.setText("Tissue: " + record.getTissue());
        createdAtRecordTextView.setText("Created at: " + formatStupidDate(record.getCreatedAt()));
        updatedAtRecordTextView.setText("Updated at: " + formatStupidDate(record.getUpdatedAt()));
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
