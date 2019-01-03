package recipeIngredients;


import java.util.List;

public class RecipeInformation {


    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private int weightWatcherSmartPoints;
    private String gaps;
    private boolean lowFodmap;
    private boolean ketogenic;
    private boolean whole30;
    private int servings;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private int aggregateLikes;
    private String creditText;
    private String sourceName;
    private List<ExtendedIngredient> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private String image;
    private String imageType;
    private String instructions;

    public RecipeInformation() {}

    public RecipeInformation(boolean vegetarian, boolean vegan, boolean glutenFree,
                             boolean dairyFree, boolean veryHealthy, boolean cheap,
                             boolean veryPopular, boolean sustainable, int weightWatcherSmartPoints,
                             String gaps, boolean lowFodmap, boolean ketogenic, boolean whole30,
                             int servings, String sourceUrl, String spoonacularSourceUrl,
                             int aggregateLikes, String creditText, String sourceName,
                             List<ExtendedIngredient> extendedIngredients, int id, String title,
                             int readyInMinutes, String image, String imageType, String instructions) {

        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
        this.dairyFree = dairyFree;
        this.veryHealthy = veryHealthy;
        this.cheap = cheap;
        this.veryPopular = veryPopular;
        this.sustainable = sustainable;
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
        this.gaps = gaps;
        this.lowFodmap = lowFodmap;
        this.ketogenic = ketogenic;
        this.whole30 = whole30;
        this.servings = servings;
        this.sourceUrl = sourceUrl;
        this.spoonacularSourceUrl = spoonacularSourceUrl;
        this.aggregateLikes = aggregateLikes;
        this.creditText = creditText;
        this.sourceName = sourceName;
        this.extendedIngredients = extendedIngredients;
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.image = image;
        this.imageType = imageType;
        this.instructions = instructions;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public boolean isWhole30() {
        return whole30;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public String getCreditText() {
        return creditText;
    }

    public String getSourceName() {
        return sourceName;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "RecipeInformation:" +
                "\n vegetarian=" + vegetarian +
                "\n vegan=" + vegan +
                "\n glutenFree=" + glutenFree +
                "\n dairyFree=" + dairyFree +
                "\n veryHealthy=" + veryHealthy +
                "\n cheap=" + cheap +
                "\n veryPopular=" + veryPopular +
                "\n sustainable=" + sustainable +
                "\n weightWatcherSmartPoints=" + weightWatcherSmartPoints +
                "\n gaps='" + gaps + '\'' +
                "\n lowFodmap=" + lowFodmap +
                "\n ketogenic=" + ketogenic +
                "\n whole30=" + whole30 +
                "\n servings=" + servings +
                "\n sourceUrl='" + sourceUrl + '\'' +
                "\n spoonacularSourceUrl='" + spoonacularSourceUrl + '\'' +
                "\n aggregateLikes=" + aggregateLikes +
                "\n creditText='" + creditText + '\'' +
                "\n sourceName='" + sourceName + '\'' +
                "\n extendedIngredients=" + extendedIngredients +
                "\n id=" + id +
                "\n title='" + title + '\'' +
                "\n readyInMinutes=" + readyInMinutes +
                "\n image='" + image + '\'' +
                "\n imageType='" + imageType + '\'' +
                "\n instructions='" + instructions;

    }
}
