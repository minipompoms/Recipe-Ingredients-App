package recipeIngredients;
import com.google.inject.AbstractModule;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientsModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//takes calladapterfactory and changes into retrofit
                .build();

        SpoonacularService service = retrofit.create(SpoonacularService.class);

        bind(SpoonacularService.class).toInstance(service);

    }

}
