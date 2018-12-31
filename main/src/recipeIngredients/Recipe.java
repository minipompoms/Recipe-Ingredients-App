package recipeIngredients;

public class Recipe {

    private int id;
    private String title;
    private Integer readyInMinutes;
    private String image;
    private String usedIngredientCount;
    private String missedIngredientCount;
    private String summary;

    public Recipe(){}

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setSummary(String summary) { this.summary = summary; }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public String getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(String usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public String getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(String missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }
    public String getSummary() { return summary; }
    @Override
    public String toString() {
        return "Recipe: " +
                "\nid: " + id +
                "\ntitle: " + title +
                "\nimage: " + image +
                "\nUsed ingredients: " + usedIngredientCount +
                "\nMissed ingredients: " + missedIngredientCount +
                "\nready in: " + readyInMinutes + " minutes\n";
    }
}
