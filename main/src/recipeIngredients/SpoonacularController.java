package recipeIngredients;

import com.google.inject.Provider;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.google.inject.Inject;

import javax.inject.Singleton;

@Singleton
public class SpoonacularController {
    private SpoonacularService service;
    private Provider<SpoonacularView> viewProvider;
    private Disposable disposable;
    String APIKEY = "";

    @Inject
    public SpoonacularController(SpoonacularService service, Provider<SpoonacularView> viewProvider) {
        this.viewProvider = viewProvider;
        this.service = service;
    }

    public void getNutrients(int id){
        disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setData, Throwable::printStackTrace);
    }

    public void getRandomJoke(){
        disposable = service.getFoodJoke(APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRandomJoke, Throwable::printStackTrace);
    }


    public void getRandomRecipe(){
        disposable = service.getRandomRecipe(APIKEY, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRandomRecipe, Throwable::printStackTrace);
    }

    public void getRecipe(int id) {
        disposable = service.getRecipeDetails(APIKEY, id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setData, Throwable::printStackTrace);
    }

    public void searchRecipe(){
        disposable = service.searchRecipe(APIKEY, "burger", 4, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(this::setRecipeSearch, Throwable::printStackTrace);
    }

    public void setRecipeSearch(SpoonacularFeed feed){
        viewProvider.get().setRecipeList(feed);
    }

    public void setRandomRecipe(SpoonacularFeed feed){
            viewProvider.get().setRandomRecipe(feed);
    }

    private void setRandomJoke(SpoonacularFeed feed){
        viewProvider.get().setJoke(feed);
    }

    private void setData(RecipeInformation recipeInformation) {
        viewProvider.get().setRecipe(recipeInformation);
    }


    public void stop() {
        disposable.dispose();
    }

}
