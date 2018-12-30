package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.tools.javac.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;


import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

@Singleton
public class SpoonacularView extends JFrame {
    private ExtendedIngredient extendedIngredient = new ExtendedIngredient();
    private JTextArea recipeInfo;
    private JTextField recipeTitle;
    private javax.swing.JLabel foodJoke;
    private JTextArea recipeByIngredient;
    private JTextArea recipeSummary;
    private JTextField ingredientsEntered;
    private ImageIcon recipeImage;
    private int recipeID;
    private URL imgURL;
    private JButton enterButton;
    JLabel imageLabel;
    JPanel entries = new JPanel();
    JPanel panel = new JPanel();


    @Inject
    public SpoonacularView(SpoonacularController controller){
        setLocation(240, 180);
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Info ...");
        Color background = new Color(0x071758);
        getContentPane().setBackground(background);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(gbl);
        Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        GridLayout gl = new GridLayout(1, 0, 10, 10);
        panel.setLayout(gbl);
        entries.setLayout(gl);
        panel.setBorder(border);
        ingredientsEntered = new JTextField();
        recipeTitle = new JTextField();
        recipeInfo = new JTextArea();
        foodJoke = new JLabel();
        recipeByIngredient = new JTextArea();
        recipeSummary = new JTextArea();


        //String ingredients = ingredientsEntered.getText();
        //panel.add(scrollPane, BorderLayout.CENTER);
       // panel.add(foodJoke, BorderLayout.SOUTH);
        //panel.add(recipeInfo, BorderLayout.CENTER);
        recipeSummary.setWrapStyleWord(true);
        recipeSummary.setLineWrap(true);

        entries.add(recipeTitle);
        //panel.add(recipeSummary, BorderLayout.CENTER);

        //panel.add(entries, BorderLayout.WEST);
        recipeID = 4630;
        controller.getQuickSummary(recipeID);
        controller.getRecipeInformation(4630);
        //controller.findByIngredients("sugar,flour,cheese", 3);
       // controller.getRandomJoke();
       // controller.searchRecipe();
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                controller.stop();
            }
        });
    }


    public void showRecipeInformation(RecipeInformation recipeInformation)  {
        StringBuilder recipeDetails = new StringBuilder();
        for(int i = 0; i < recipeInformation.getExtendedIngredients().size(); i++){
            recipeDetails.append("\t");
            recipeDetails.append(recipeInformation.getExtendedIngredients().get(i).getOriginalString());
            recipeDetails.append("\n");
        }
        recipeInfo.setText(recipeDetails.toString());
        recipeTitle.setText(recipeInformation.getTitle());

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


    public void showQuickSummary(Recipe recipe) {
        String summary = recipe.getSummary().replaceAll("<[^>]*>", "");
        recipeSummary.setText(summary);
    }
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }


}
