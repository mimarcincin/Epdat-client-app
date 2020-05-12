package sk.upjs.micma.epdat_client_app;

import java.util.List;

import retrofit2.*;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public interface DatabaseApi {
    //String BASE_URL = "http://10.0.2.2:8080/api/";
    String BASE_URL = "http://192.168.0.112:8080/api/";

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

    @POST("plants")
    Call<Void> addPlant();



    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    DatabaseApi API = RETROFIT.create(DatabaseApi.class);
}
