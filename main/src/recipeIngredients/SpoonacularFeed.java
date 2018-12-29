package recipeIngredients;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class SpoonacularFeed extends AbstractModule {

    private Recipe recipe;
    private RecipeInformation recipeInfo;
    private ExtendedIngredient extendedIngredient;
    private Nutrient nutrient;
    private String text;

    @Inject
    public SpoonacularFeed(Recipe recipe,
                           RecipeInformation recipeInfo,
                           ExtendedIngredient extendedIngredient,
                           Nutrient nutrient, String text) {

        this.recipeInfo = recipeInfo;
        this.recipe = recipe;
        this.extendedIngredient = extendedIngredient;
        this.nutrient = nutrient;
        this.text = text;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public List<Recipe> getRecipeList(){
        return new ArrayList<>();
    }
    public RecipeInformation getRecipeInfo(){
        return recipeInfo;
    }

    public String getJoke(){
        return text;
    }

    public List<Recipe> getRandomRecipes(){
        List<Recipe> list = new ArrayList<>();
        list.add(recipe);
        return list;
    }

}
