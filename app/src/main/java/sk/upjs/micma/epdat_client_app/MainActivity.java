package sk.upjs.micma.epdat_client_app;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import sk.upjs.micma.epdat_client_app.fragments.AddPlantFragment;
import sk.upjs.micma.epdat_client_app.fragments.AddRecordFragment;
import sk.upjs.micma.epdat_client_app.fragments.EditPlantFragment;
import sk.upjs.micma.epdat_client_app.fragments.EditRecordFragment;
import sk.upjs.micma.epdat_client_app.fragments.PlantInfoFragment;
import sk.upjs.micma.epdat_client_app.fragments.RecordInfoFragment;
import sk.upjs.micma.epdat_client_app.fragments.SearchFragment;
import sk.upjs.micma.epdat_client_app.fragments.PlantsTableFragment;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_more_vert_black_24dp));
        setSupportActionBar(myToolbar);
        if(savedInstanceState!=null){
            getSupportActionBar().setTitle(savedInstanceState.getString("title"));
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "current");
            if (getVisibleFragment() instanceof SearchFragment) {
                getSupportActionBar().setTitle(this.getTitle());
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

        } else {
            showSearchFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "current", getVisibleFragment());
        outState.putString("title",(String) getSupportActionBar().getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.first_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_plant_t:
                showAddPlantFragment();
                return true;
            case R.id.edit_plant_t:
                PlantInfoFragment f = (PlantInfoFragment) getSupportFragmentManager().findFragmentByTag("PLANT_INFO_F");
                showEditPlantFragment(f.getPlant());
                return true;
            case R.id.add_record_t:
                PlantInfoFragment fr = (PlantInfoFragment) getSupportFragmentManager().findFragmentByTag("PLANT_INFO_F");
                showAddRecordFragment(fr.getPlant());
                return true;
            case R.id.delete_plant_t:
                PlantInfoFragment fra = (PlantInfoFragment) getSupportFragmentManager().findFragmentByTag("PLANT_INFO_F");
                fra.deleteClicked();
                return true;
            case R.id.edit_record_t:
                RecordInfoFragment frag = (RecordInfoFragment) getSupportFragmentManager().findFragmentByTag("RECORD_INFO_F");
                showEditRecordFragment(frag.getRecord(), frag.getPlant());
                return true;
            case R.id.delete_record_t:
                RecordInfoFragment fragm = (RecordInfoFragment) getSupportFragmentManager().findFragmentByTag("RECORD_INFO_F");
                fragm.deleteClicked();
                return true;
            case R.id.refresh_t:
                if (getVisibleFragment() instanceof PlantsTableFragment ){
                    PlantsTableFragment fragme = (PlantsTableFragment) getSupportFragmentManager().findFragmentByTag("PLANTS_TAB_F");
                    fragme.refreshPlants();
                }
                if (getVisibleFragment() instanceof PlantInfoFragment ){
                    PlantInfoFragment fragmen = (PlantInfoFragment) getSupportFragmentManager().findFragmentByTag("PLANT_INFO_F");
                    fragmen.refreshRecords();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getVisibleFragment() instanceof SearchFragment) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(this.getTitle());
        }
        if (getVisibleFragment() instanceof PlantsTableFragment)
            getSupportActionBar().setTitle("Searched results");

        if (getVisibleFragment() instanceof PlantInfoFragment)
            getSupportActionBar().setTitle("Species info");

        if (getVisibleFragment() instanceof RecordInfoFragment)
            getSupportActionBar().setTitle("Record info");
    }

    public androidx.fragment.app.Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<androidx.fragment.app.Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void showSearchFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new SearchFragment(), "SEARCH_F")
                .commit();

    }

    public void showSpeciesTableFragment(Bundle searchInfo) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new PlantsTableFragment(searchInfo), "PLANTS_TAB_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Searched results");
    }

    public void showPlantInfoFragment(Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new PlantInfoFragment(plant), "PLANT_INFO_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Species info");
    }

    public void showRecordInfoFragment(Record record, Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new RecordInfoFragment(record, plant), "RECORD_INFO_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Record info");
    }

    public void showAddRecordFragment(Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new AddRecordFragment(plant), "RECORD_ADD_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Add new record for");
    }

    public void showEditRecordFragment(Record record, Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new EditRecordFragment(record, plant), "RECORD_EDIT_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Edit record of");
    }

    public void showAddPlantFragment() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new AddPlantFragment(), "PLANT_ADD_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Add new species");
    }

    public void showEditPlantFragment(Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new EditPlantFragment(plant), "PLANT_EDIT_F")
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle("Edit species");
    }


}
