package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.codemodel.internal.JLabel;


import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Singleton

public class SpoonacularView extends JFrame {
    private JTextArea recipeInfo;
    private JTextField recipeTitle;
    private javax.swing.JLabel foodJoke;
    private JTextArea random;


    @Inject
    public SpoonacularView(SpoonacularController controller){
        setLocation(240, 180);
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Info ...");
        Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(border);
        recipeTitle = new JTextField();
        recipeInfo = new JTextArea();
        foodJoke = new javax.swing.JLabel();
        random = new JTextArea();
        //panel.add(random, BorderLayout.CENTER);
        panel.add(foodJoke, BorderLayout.SOUTH);
        panel.add(recipeInfo, BorderLayout.CENTER);
        panel.add(recipeTitle, BorderLayout.NORTH);
        add(panel);
        //controller.getRandomRecipe();
        controller.getRecipe(15694);
        controller.getRandomJoke();
        //controller.searchRecipe();
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

    public void setRandomRecipe(SpoonacularFeed feed){
        String text = feed.getRandomRecipes().toString();
        random.setText(text);

    }

    public void setRecipeList(SpoonacularFeed feed){
        String text = feed.getRecipeList().toString();

        System.out.println(text);
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
