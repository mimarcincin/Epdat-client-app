package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import sk.upjs.micma.epdat_client_app.Plant;
import sk.upjs.micma.epdat_client_app.R;


public class PlantInfoFragment extends Fragment {
    private Plant plant;

    private TextView familyNameInfo;
    private TextView genusNameInfo;
    private TextView speciesNameInfo;
    private TextView authorityNameInfo;
    private TextView noticeNameInfo;
    private TextView createdAtPlantInfo;
    private TextView updatedAtPlantInfo;

    public PlantInfoFragment(Plant plant) {
    this.plant = plant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_info, container, false);

        familyNameInfo = view.findViewById(R.id.familyNameInfo);
        genusNameInfo = view.findViewById(R.id.genusNameInfo);
        speciesNameInfo = view.findViewById(R.id.speciesNameInfo);
        authorityNameInfo = view.findViewById(R.id.authorityNameInfo);
        noticeNameInfo = view.findViewById(R.id.noticeNameInfo);
        createdAtPlantInfo =view.findViewById(R.id.createdPlantAtInfo);
        updatedAtPlantInfo = view.findViewById(R.id.updatedPlantAtNameInfo);

        familyNameInfo.setText(plant.getFamily());
        genusNameInfo.setText(plant.getGenus());
        speciesNameInfo.setText(plant.getSpecies());
        authorityNameInfo.setText(plant.getAuthority());
        noticeNameInfo.setText(plant.getNotice());
        createdAtPlantInfo.setText(plant.getCreatedAt());
        updatedAtPlantInfo.setText(plant.getUpdatedAt());

        return view;
    }
}
