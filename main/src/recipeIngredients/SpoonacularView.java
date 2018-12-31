package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.tools.javac.util.List;
import sun.text.resources.sr.JavaTimeSupplementary_sr_Latn;


import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

@Singleton

public class SpoonacularView extends JFrame implements ChangeListener {
    private JTextArea recipeInfo;
    private JLabel recipeTitle;
    private JLabel foodJoke;
    private JTextField [] ingredientsEntered = new JTextField[5];
    private JTextArea recipeSummary;
    private int recipeID;
    private JButton searchButton;
    private DefaultListModel model = new DefaultListModel();
    private JList<String> recipeList = new JList<String>(model);
    private String mode;
    private String keyword;
    private JTextField keywordField;
    @Inject
    public SpoonacularView(SpoonacularController controller){
        setLocation(240, 180);
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Box ...");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                controller.stop();
            }
        });
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JTabbedPane source = (JTabbedPane) event.getSource();
                int index = source.getSelectedIndex();
                mode = source.getTitleAt(index);
            }
        };

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(changeListener);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JPanel ingredientsPanel = new JPanel(new GridLayout(0,1));
        JPanel recipePanel = new JPanel(new GridLayout(0,1));
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel(new GridLayout(0,2));
        JPanel recipePanel1 = new JPanel(new GridLayout(0,1));
        foodJoke = new JLabel();

        keywordField = new JTextField();
        recipeTitle = new JLabel();
        recipeInfo = new JTextArea();
        recipeSummary = new JTextArea();
        recipeSummary.setWrapStyleWord(true);
        recipeSummary.setLineWrap(true);
        controller.getRandomJoke();

        recipeID = 15595;

        recipePanel1.add(new JLabel("Enter keyword: "));
        recipePanel1.add(keywordField);
        recipePanel1.add(recipeTitle);
        recipePanel1.add(recipeInfo);
        recipePanel1.add(recipeSummary);
        recipePanel1.add(recipeList);
        tab1.add(recipePanel1);

        ingredientsPanel.add(new JLabel("  Enter up to 5 ingredients"));
        for(int i = 0; i < ingredientsEntered.length; i++){
            ingredientsEntered[i] = new JTextField();
            ingredientsPanel.add(ingredientsEntered[i]);
        }

        recipeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        recipeList.setLayoutOrientation(JList.VERTICAL);
        recipePanel.add(recipeList);
        tab2.add(ingredientsPanel);
        tab2.add(recipePanel);
        tabbedPane.add("1", tab1);
        tabbedPane.add("2", tab2);

        JPanel mainPanel = new JPanel(new BorderLayout());
        //FlowLayout fl = new FlowLayout(FlowLayout.TRAILING, 30, 10);
        //mainPanel.setLayout(fl);
        searchButton = new JButton("Search");
        mainPanel.add(searchButton, BorderLayout.SOUTH);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (mode){
                    case "1":
                        keyword = recipeTitle.getText();
                        findRecipesByKeyword(controller,keyword);
                        recipePanel1.add(recipeList);
                    case "2":
                        StringBuilder sb= new StringBuilder();
                        for(int i = 0; i < ingredientsEntered.length; i++){
                            sb.append(ingredientsEntered[i].getText() + ",");
                        }
                        findByIngredients(controller, sb.toString());
                        recipePanel.add(recipeList);
                }

            }
        });
        Box box = Box.createVerticalBox();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        box.add(tabbedPane);
        box.add( Box.createHorizontalGlue());
        box.add(mainPanel);
        //box.add(foodJoke);
        add(box);

    }

    public void findRecipesByKeyword(SpoonacularController controller, String keyword){
        controller.getRecipesByKeyword(keyword);
        //controller.getQuickSummary(recipeID);

    }
    public void findByIngredients(SpoonacularController controller, String ingredients){
        controller.findByIngredients(ingredients);
    }

    public void findRecipeInfo(SpoonacularController controller, int recipeID){
        controller.getRecipeInformation(recipeID);
    }


    public void showRecipes(RecipeInformation recipeInformation) {
        StringBuilder recipeDetails = new StringBuilder();
        for(int i = 0; i < recipeInformation.getExtendedIngredients().size(); i++){
            recipeDetails.append("\t");
            recipeDetails.append(recipeInformation.getExtendedIngredients().get(i).getOriginalString());
            recipeDetails.append("\n");
        }

        recipeInfo.setText(recipeDetails.toString());
        recipeTitle.setText(recipeInformation.getId() + ": "+recipeInformation.getTitle());
    }

    public void showFindByIngredient(ArrayList<Recipe> feed){
        for(int i = 0; i < feed.size(); i++){
            String recipe = feed.get(i).getId() + "\t" + feed.get(i).getTitle() + "\n";
            model.add(i, recipe);
        }

    }

    public void showRecipesByKeyword(SpoonacularFeed feed){
        for(int i = 0; i < feed.getRecipeList().size(); i++){
            String recipe = feed.getRecipeList().get(i).toString();
            model.add(i, recipe);
        }

    }

    public void showQuickSummary(Recipe recipe) {
        String summary = recipe.getSummary().replaceAll("<[^>]*>", "");
        recipeSummary.setText(summary);
    }

    public void setJoke(SpoonacularFeed feed){
        String joke = feed.getJoke();
        foodJoke.setText(joke+"...");
    }


    @Override
    public void stateChanged(ChangeEvent e) {

    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }
}
