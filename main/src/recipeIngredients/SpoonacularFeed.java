package recipeIngredients;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import java.util.List;

public class SpoonacularFeed extends AbstractModule {

    private Recipe recipe;
    private RecipeInformation recipeInfo;
    private ExtendedIngredient extendedIngredient;
    private Nutrient nutrient;
    private String text;
    private List<Recipe> results;



    @Inject
    public SpoonacularFeed(Recipe recipe,
                           RecipeInformation recipeInfo,
                           ExtendedIngredient extendedIngredient,
                           Nutrient nutrient, String text, List<Recipe> results) {

        this.recipeInfo = recipeInfo;
        this.recipe = recipe;
        this.extendedIngredient = extendedIngredient;
        this.nutrient = nutrient;
        this.text = text;
        this.results = results;
    }


    public Recipe getRecipe(){
        return recipe;
    }

    public List<Recipe> getRecipeList(){
        return results;
    }



    public RecipeInformation getRecipeInfo(){
        return recipeInfo;
    }

    public String getJoke(){
        return text;
    }


}
