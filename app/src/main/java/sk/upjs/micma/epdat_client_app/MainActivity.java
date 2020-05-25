package sk.upjs.micma.epdat_client_app;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new SearchFragment()).commit();

    }

    public void showSpeciesTableFragment(Bundle searchInfo) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new PlantsTableFragment(searchInfo))
                .addToBackStack(null)
                .commit();
    }

    public void showPlantInfoFragment(Plant plant){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new PlantInfoFragment(plant))
                .addToBackStack(null)
                .commit();
    }

    public void showRecordInfoFragment(Record record, Plant plant) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new RecordInfoFragment(record, plant))
                .addToBackStack(null)
                .commit();
    }
    public void showAddRecordFragment(Plant plant){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new AddRecordFragment(plant))
                .addToBackStack(null)
                .commit();
    }

    public void showEditRecordFragment(Record record, Plant plant){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new EditRecordFragment(record, plant))
                .addToBackStack(null)
                .commit();
    }
    public void showAddPlantFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new AddPlantFragment())
                .addToBackStack(null)
                .commit();
    }
    public void showEditPlantFragment(Plant plant){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new EditPlantFragment(plant))
                .addToBackStack(null)
                .commit();
    }
}
