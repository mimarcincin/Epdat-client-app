package sk.upjs.micma.epdat_client_app;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;
import sk.upjs.micma.epdat_client_app.models.Token;
import sk.upjs.micma.epdat_client_app.models.User;

public interface DatabaseApi {
    String BASE_URL = "https://s.ics.upjs.sk/mmarcincin_api/api/";
    //String BASE_URL = "http://localhost:8097/api/"; //tu by mala byt ip adresa pc
    String AUTH_URL = "https://s.ics.upjs.sk/mmarcincin_api/authenticate";

    @GET("plants")
    Call<List<Plant>> getPlants();

    @GET("plants/F/{family}")
    Call<List<Plant>> getPlantsByFamily(@Path(value = "family", encoded = true) String family);

    @GET("plants/G/{genus}")
    Call<List<Plant>> getPlantsByGenus(@Path(value = "genus", encoded = true) String genus);

    @GET("plants/S/{species}")
    Call<List<Plant>> getPlantsBySpecies(@Path(value = "species", encoded = true) String species);

    @GET("plants/T/{tissue}")
    Call<List<Plant>> getPlantsByTissue(@Path(value = "tissue", encoded = true) String tissue);
    /*@GET("plants/FG/{family}/{genus}")
    Call<List<Plant>> getPlantsByFamilyAndGenus(@Path(value = "family", encoded = true) String family,
                                                @Path(value = "genus", encoded = true) String genus);*/

   /* @GET("plants/FGS/{family}/{genus}/{species}")
    Call<List<Plant>> getPlantsByFamilyAndGenusAndSpecies(@Path(value = "family", encoded = true) String family,
                                                          @Path(value = "genus", encoded = true) String genus,
                                                          @Path(value = "species", encoded = true) String species);

    @GET("plants/FGST/{family}/{genus}/{species}/{tissue}")
    Call<List<Plant>> getPlantsByFamilyAndGenusAndSpeciesAndTissue(@Path(value = "family", encoded = true) String family,
                                                                   @Path(value = "genus", encoded = true) String genus,
                                                                   @Path(value = "species", encoded = true) String species,
                                                                   @Path(value = "tissue", encoded = true) String tissue);
*/
    @GET("plants/FT/{family}/{tissue}")
    Call<List<Plant>> getPlantsByFamilyAndTissue(@Path(value = "family", encoded = true) String family,
                                                @Path(value = "tissue", encoded = true) String tissue);

    @GET("plants/GT/{genus}/{tissue}")
    Call<List<Plant>> getPlantsByGenusAndTissue(@Path(value = "genus", encoded = true) String genus,
                                                @Path(value = "tissue", encoded = true) String tissue);

    @POST("plants/{plantId}/records")
    Call<Record> addRecord(@Path(value = "plantId", encoded = true) String plantId, @Body Record record, @Header("Authorization") String token);

    @PUT("plants/{plantId}/records/{recordId}")
    Call<Record> updateRecord(@Path(value = "plantId", encoded = true) String plantId,
                            @Path(value = "recordId", encoded = true) String recordId, @Body Record record, @Header("Authorization") String token);

    @DELETE("plants/{plantId}/records/{recordId}")
    Call<Void> deleteRecord(@Path(value = "plantId", encoded = true) String plantId,
                            @Path(value = "recordId", encoded = true) String recordId, @Header("Authorization") String token);
    @POST("plants")
    Call<Plant> addPlant(@Body Plant plant, @Header("Authorization") String token);

    @PUT("plants/{plantId}")
    Call<Plant> updatePlant(@Path(value = "plantId", encoded = true) String plantId, @Body Plant plant, @Header("Authorization") String token);

    @DELETE("plants/{plantId}")
    Call<Void> deletePlant(@Path(value = "plantId", encoded = true) String plantId, @Header("Authorization") String token);

    @GET("plants/{plantId}/records")
    Call<List<Record>> getRecordsByPlantId(@Path(value = "plantId", encoded = true) String plantId);

    @POST(AUTH_URL)
    Call<Token> login(@Body User user);

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    DatabaseApi API = RETROFIT.create(DatabaseApi.class);
}
