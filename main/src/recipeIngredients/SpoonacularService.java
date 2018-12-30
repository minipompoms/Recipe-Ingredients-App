package recipeIngredients;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.tools.javac.util.List;
import io.reactivex.Observable;
import jdk.nashorn.internal.ir.RuntimeNode;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.IOException;
import java.util.ArrayList;

public interface SpoonacularService {

    @GET("recipes/random")
    Observable<SpoonacularFeed> getRandomRecipe(
            @Header("X-RapidAPI-Key") String RapidAPIKey,
            @Query("number") int number);

    @GET("recipes/{id}/summary")
    Observable<Recipe> getQuickSummary(
            @Header("X-RapidAPI-Key") String RapidAPIKey,
            @Path("id") int id);

    @GET("recipes/search")
    Observable<SpoonacularFeed>searchRecipeList(
            @Header("X-RapidAPI-Key") String RapidAPIKey,
            @Query("query") String keyword,
            @Query("number") int number);


    @GET("recipes/findByIngredients")
    Observable<ArrayList<Recipe>>findRecipeByIngredients(
            @Header("X-RapidAPI-Key") String RapidAPIKey,
            @Query("ingredients") String ingredients,
            @Query("number") int number);


    @GET("recipes/{id}/information")
    Observable<RecipeInformation> getRecipeDetails(
            @Header("X-RapidAPI-Key")  String RapidAPIKey,
            @Path("id") int id,
            @Query("includeNutrition") boolean includeNutrition);

    @GET("food/jokes/random")
    Observable<SpoonacularFeed> getFoodJoke(
            @Header("X-RapidAPI-Key") String RapidAPIKey);


}
