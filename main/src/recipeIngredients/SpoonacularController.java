package recipeIngredients;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;

@Singleton
public class SpoonacularController {
    private SpoonacularService service;
    private Provider<SpoonacularView> viewProvider;
    private Disposable disposable;
    private String APIKEY = "lIQwnxhTt8mshrspQjiOj9uYDVs5p1K8otZjsncetRKjGas2oN";

    @Inject
    public SpoonacularController(SpoonacularService service, Provider<SpoonacularView> viewProvider) {
        this.viewProvider = viewProvider;
        this.service = service;
    }

    //  public void getNutrients(int id){
        /*disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRecipeInformation, Throwable::printStackTrace);*/
    // }

    void getRandomJoke() {
        disposable = service.getFoodJoke(APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRandomJoke, Throwable::printStackTrace);
    }

    void getRecipeInformation(int id) {
        disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRecipeInformation, Throwable::printStackTrace);
    }

    void getRecipeImage(int id, int numTab) {
        disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(recipeInformation -> setRecipeImage(recipeInformation, numTab), Throwable::printStackTrace);
    }

    private void setRecipeImage(RecipeInformation recipeInformation, int numTab) throws IOException {
        viewProvider.get().setImage(recipeInformation.getImage(), numTab);
    }

    void getRecipesByKeyword(String keyword) {
        disposable = service.searchRecipeList(APIKEY, keyword, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setKeywordSearch, Throwable::printStackTrace);
    }

    void findByIngredients(String ingredientList) {
        disposable = service.findRecipeByIngredients(APIKEY, ingredientList, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single()).distinct()
                .subscribe(this::setFindByIngredient, Throwable::printStackTrace);
    }

    void getQuickSummary(int id) {
        disposable = service.getQuickSummary(APIKEY, id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setQuickSummary, Throwable::printStackTrace);
    }

    private void setQuickSummary(Recipe recipe) {
        viewProvider.get().showQuickSummary(recipe);
    }


    private void setKeywordSearch(SpoonacularFeed feed) {
        viewProvider.get().showRecipesByKeyword(feed);
    }

    private void setFindByIngredient(ArrayList<Recipe> feed) {
        viewProvider.get().showFindByIngredient(feed);
    }

    private void setRandomJoke(SpoonacularFeed feed) {
        viewProvider.get().setJoke(feed);
    }

    private void setRecipeInformation(RecipeInformation recipeInformation) {
        viewProvider.get().showRecipes(recipeInformation);
    }


    void stop() {
        disposable.dispose();
    }

}
