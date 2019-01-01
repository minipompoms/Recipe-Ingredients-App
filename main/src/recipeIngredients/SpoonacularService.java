package recipeIngredients;

import io.reactivex.Observable;
import retrofit2.http.*;
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
