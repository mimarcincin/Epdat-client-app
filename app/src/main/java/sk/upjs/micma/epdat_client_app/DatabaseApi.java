package sk.upjs.micma.epdat_client_app;

import java.sql.Struct;
import java.util.List;

import retrofit2.*;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import sk.upjs.micma.epdat_client_app.models.Plant;
import sk.upjs.micma.epdat_client_app.models.Record;

public interface DatabaseApi {
    //String BASE_URL = "http://10.0.2.2:8080/api/";
    String BASE_URL = "http://192.168.0.102:8080/api/";

    @GET("plants")
    Call<List<Plant>> getPlants();

    @GET("plants/F/{family}")
    Call<List<Plant>> getPlantsByFamily(@Path(value = "family", encoded = true) String family);

    @GET("plants/FG/{family}/{genus}")
    Call<List<Plant>> getPlantsByFamilyAndGenus(@Path(value = "family", encoded = true) String family,
                                                @Path(value = "genus", encoded = true) String genus);

    @GET("plants/FGS/{family}/{genus}/{species}")
    Call<List<Plant>> getPlantsByFamilyAndGenusAndSpecies(@Path(value = "family", encoded = true) String family,
                                                          @Path(value = "genus", encoded = true) String genus,
                                                          @Path(value = "species", encoded = true) String species);

    @GET("plants/FGST/{family}/{genus}/{species}/{tissue}")
    Call<List<Plant>> getPlantsByFamilyAndGenusAndSpeciesAndTissue(@Path(value = "family", encoded = true) String family,
                                                                   @Path(value = "genus", encoded = true) String genus,
                                                                   @Path(value = "species", encoded = true) String species,
                                                                   @Path(value = "tissue", encoded = true) String tissue);

    @GET("plants/FT/{family}/{tissue}")
    Call<List<Plant>> getPlantsByFamilyAndTissue(@Path(value = "family", encoded = true) String family,
                                                @Path(value = "tissue", encoded = true) String tissue);

    @GET("plants/FGT/{family}/{genus}/{tissue}")
    Call<List<Plant>> getPlantsByFamilyAndGenusAndTissue(@Path(value = "family", encoded = true) String family,
                                                          @Path(value = "genus", encoded = true) String genus,
                                                          @Path(value = "tissue", encoded = true) String tissue);
    @POST("plants/{plantId}/records")
    Call<Record> addRecord(@Path(value = "plantId", encoded = true) String plantId, @Body Record record);

    @PUT("plants/{plantId}/records/{recordId}")
    Call<Record> updateRecord(@Path(value = "plantId", encoded = true) String plantId,
                            @Path(value = "recordId", encoded = true) String recordId, @Body Record record);

    @DELETE("plants/{plantId}/records/{recordId}")
    Call<Void> deleteRecord(@Path(value = "plantId", encoded = true) String plantId,
                            @Path(value = "recordId", encoded = true) String recordId);
    @POST("plants")
    Call<Plant> addPlant(@Body Plant plant);

    @PUT("plants/{plantId}")
    Call<Plant> updatePlant(@Path(value = "plantId", encoded = true) String plantId, @Body Plant plant);

    @DELETE("plants/{plantId}")
    Call<Void> deletePlant(@Path(value = "plantId", encoded = true) String plantId);

    @GET("plants/{plantId}/records")
    Call<List<Record>> getRecordsByPlantId(@Path(value = "plantId", encoded = true) String plantId);

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    DatabaseApi API = RETROFIT.create(DatabaseApi.class);
}
