package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.tools.javac.util.List;


import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

@Singleton

public class SpoonacularView extends JFrame {
    private ExtendedIngredient extendedIngredient = new ExtendedIngredient();
    private JTextArea recipeInfo;
    private JTextField recipeTitle;
    private javax.swing.JLabel foodJoke;
    private JTextArea recipeByIngredient;
    private JTextField ingredientsEntered;

    @Inject
    public SpoonacularView(SpoonacularController controller){
        setLocation(240, 180);
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Info ...");
        Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        JPanel panel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.TRAILING, 30, 10);
        JPanel entries = new JPanel();
        entries.setLayout(fl);
        panel.setLayout(new BorderLayout());
        panel.setBorder(border);
        ingredientsEntered = new JTextField();
        recipeTitle = new JTextField();
        recipeInfo = new JTextArea();
        foodJoke = new javax.swing.JLabel();
        recipeByIngredient = new JTextArea();
        //String ingredients = ingredientsEntered.getText();
        JScrollPane scrollPane = new JScrollPane(recipeByIngredient);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(foodJoke, BorderLayout.SOUTH);
        //panel.add(recipeInfo, BorderLayout.CENTER);
        entries.add(recipeTitle);
        panel.add(entries, BorderLayout.WEST);
        add(panel);

        controller.findByIngredients("sugar,flour,cheese");
        controller.getRecipeInformation(15694);
        controller.getRandomJoke();
        controller.searchRecipe();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                controller.stop();
            }
        });
    }


    public void setRecipe(RecipeInformation recipeInformation) {
        StringBuilder recipeDetails = new StringBuilder();
        for(int i = 0; i < recipeInformation.getExtendedIngredients().size(); i++){
            recipeDetails.append("\t");
            recipeDetails.append(recipeInformation.getExtendedIngredients().get(i).getOriginalString());
            recipeDetails.append("\n");
        }
        recipeInfo.setText(recipeDetails.toString());
        recipeTitle.setText(recipeInformation.getId() + ": "+recipeInformation.getTitle());

    }

    public void setFindByIngredient(ArrayList<Recipe> feed){
        StringBuilder recipes = new StringBuilder();
        for(int i = 0; i < feed.size(); i++){
            recipes.append("\t");
            recipes.append(feed.get(i).getId()).append("\t");
            recipes.append(feed.get(i).getTitle());
            recipes.append("\n");

        }
        recipeByIngredient.setText(recipes.toString());

    }

    public void setRecipeList(SpoonacularFeed feed){
        String text = feed.getRecipeList().toString();

        //System.out.println(text);
    }


    public void setJoke(SpoonacularFeed feed){
        String joke = feed.getJoke();
        foodJoke.setText(joke+"...");
    }
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }
}
