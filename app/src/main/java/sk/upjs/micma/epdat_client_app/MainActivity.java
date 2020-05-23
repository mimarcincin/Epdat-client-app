package sk.upjs.micma.epdat_client_app;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;
        import sk.upjs.micma.epdat_client_app.fragments.PlantInfoFragment;
import sk.upjs.micma.epdat_client_app.fragments.SearchFragment;
import sk.upjs.micma.epdat_client_app.fragments.SpeciesTableFragment;

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
                .replace(R.id.activity_main, new SpeciesTableFragment(searchInfo))
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

}
