package recipeIngredients;

import com.google.inject.Provider;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.google.inject.Inject;

import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class SpoonacularController {
    private SpoonacularService service;
    private Provider<SpoonacularView> viewProvider;
    private Disposable disposable;
    String APIKEY = "lIQwnxhTt8mshrspQjiOj9uYDVs5p1K8otZjsncetRKjGas2oN";

    @Inject
    public SpoonacularController(SpoonacularService service, Provider<SpoonacularView> viewProvider) {
        this.viewProvider = viewProvider;
        this.service = service;
    }

    public void getNutrients(int id){
        /*disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRecipeInformation, Throwable::printStackTrace);*/
    }

    public void getRandomJoke(){
        disposable = service.getFoodJoke(APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRandomJoke, Throwable::printStackTrace);
    }

    public void getRecipeInformation(int id) {
        disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRecipeInformation, Throwable::printStackTrace);
    }


    public void getRecipesByKeyword(String keyword){
        disposable = service.searchRecipeList(APIKEY, keyword, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setKeywordSearch, Throwable::printStackTrace);
    }

    public void findByIngredients(String ingredientList){
        disposable = service.findRecipeByIngredients(APIKEY, ingredientList, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setFindByIngredient, Throwable::printStackTrace);
    }

    public void getQuickSummary(int id){
        disposable = service.getQuickSummary(APIKEY, id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setQuickSummary, Throwable::printStackTrace);
    }

    private void setQuickSummary(Recipe recipe) {
        viewProvider.get().showQuickSummary(recipe);
    }


    public void setKeywordSearch(SpoonacularFeed feed){
        viewProvider.get().showRecipesByKeyword(feed);
    }

    public void setFindByIngredient(ArrayList<Recipe> feed){
        viewProvider.get().showFindByIngredient(feed);
    }

    private void setRandomJoke(SpoonacularFeed feed){
        viewProvider.get().setJoke(feed);
    }

    private void setRecipeInformation(RecipeInformation recipeInformation) {
        viewProvider.get().showRecipes(recipeInformation);
    }


    public void stop() {
        disposable.dispose();
    }

}
