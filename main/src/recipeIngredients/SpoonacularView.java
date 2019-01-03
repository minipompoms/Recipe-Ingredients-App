package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

@Singleton
public class SpoonacularView extends JFrame {
    private JTextArea recipeInfo;
    private JLabel recipeTitle;
    private JLabel foodJoke;
    private ArrayList<String> ingredients = new ArrayList<>();
    private JTextField ingredientField = new JTextField("Enter an ingredient here.");
    private JTextArea recipeSummary1;
    private JTextArea recipeSummary2;
    private int recipeID;
    private Map<String, Integer> recipeIDMap = new HashMap<>();
    private DefaultListModel<String> model1 = new DefaultListModel<>();
    private DefaultListModel<String> model2 = new DefaultListModel<>();
    private JList<String> recipeList1;
    private JList<String> recipeList2;
    private String mode;
    private String keyword;
    private JTextField keywordField;
    private JPanel ingredientsPanel;

    @Inject
    public SpoonacularView(SpoonacularController controller) {
        setLocation(240, 180);
        setSize(1000, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Box ...");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                controller.stop();
            }
        });
        ChangeListener changeListener = event -> {
            JTabbedPane source = (JTabbedPane) event.getSource();
            int index = source.getSelectedIndex();
            mode = source.getTitleAt(index);
        };

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(changeListener);
        tabbedPane.setBackground(Color.black);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 30, 10);

        createIngredientPanel();
        JPanel keywordPanel = new JPanel(fl);
        JPanel recipePanel1 = new JPanel(new GridLayout(0, 1));
        JPanel recipePanel2 = new JPanel(new GridLayout(0, 1));
        JPanel tab1 = new JPanel(new GridLayout(0, 2));
        JPanel tab2 = new JPanel(new GridLayout(0, 3));

        foodJoke = new JLabel();
        controller.getRandomJoke();

        keywordField = new JTextField();
        keywordField.setColumns(15);

        recipeSummary1 = new JTextArea();
        recipeSummary1.setWrapStyleWord(true);
        recipeSummary1.setLineWrap(true);
        recipeSummary1.setColumns(30);
        recipeSummary1.setRows(58);
        recipeList1 = new JList<>(model1);
        recipeList1.setSelectionMode(SINGLE_SELECTION);
        recipeList1.setLayoutOrientation(JList.VERTICAL);
        recipeList1.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                Object item = changedList.getSelectedValue();
                recipeID = recipeIDMap.get(item.toString());
                controller.getQuickSummary(recipeID);
                keywordPanel.add(recipeSummary1);
            }


        });
        keywordPanel.add(new JLabel("Search for..."));
        keywordPanel.add(keywordField);
        keywordPanel.add(recipeSummary1);
        recipePanel1.add(recipeList1);
        tab1.add(keywordPanel);
        tab1.add(recipePanel1);


        recipeSummary2 = new JTextArea();
        recipeSummary2.setWrapStyleWord(true);
        recipeSummary2.setLineWrap(true);
        recipeSummary2.setColumns(30);
        recipeSummary2.setRows(34);
        recipeList2 = new JList<>(model2);
        recipeList2.setSelectionMode(SINGLE_SELECTION);
        recipeList2.setLayoutOrientation(JList.VERTICAL);
        recipeList2.addListSelectionListener((ListSelectionEvent e) -> {

            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                Object item = changedList.getSelectedValue();
                recipeID = recipeIDMap.get(item.toString());
                controller.getQuickSummary(recipeID);
                tab2.add(recipeSummary2);
            }
        });
        recipePanel2.add(recipeList2);
        tab2.add(ingredientsPanel);
        tab2.add(recipeSummary2);
        tab2.add(recipePanel2);
        tabbedPane.add("Search for a recipe", tab1);
        tabbedPane.add("Lookup recipes by ingredient", tab2);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.pink);
        setBackground(Color.pink);
        JButton searchButton = new JButton("Search");
        mainPanel.add(searchButton, BorderLayout.AFTER_LAST_LINE);
        searchButton.addActionListener(e -> {
            if (mode.equals("Search for a recipe")) {
                keyword = keywordField.getText();
                findRecipesByKeyword(controller, keyword);
                recipePanel1.add(recipeList1);
                displayRecipeInfoDialog(controller, recipeList1);

            }

            if (mode.equals("Lookup recipes by ingredient")) {
                StringBuilder ingredientsBuilder = new StringBuilder();
                IntStream.range(0, ingredients.size()).forEach(i -> {
                    ingredientsBuilder.append(ingredients.get(i)).append(",");
                    findByIngredients(controller, ingredientsBuilder.toString());
                    recipePanel2.add(recipeList2);
                    displayRecipeInfoDialog(controller, recipeList2);
                });

            }

        });

        mainPanel.add(foodJoke, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        Border border = BorderFactory.createEmptyBorder(20, 10, 20, 10);
        getRootPane().setBorder(border);

        add(mainPanel);

    }

    private GridBagConstraints constraint;

    private void createIngredientPanel() {

        ingredientsPanel = new JPanel(new GridBagLayout());
        constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(3, 2, 3, 2);
        ingredientsPanel.add(ingredientField, constraint);
        JButton addIngredient = new JButton("Add");
        addIngredient.addActionListener(addEvent ->
        {
            if (ingredients.size() < 5) {
                ingredients.add(ingredientField.getText());
                updateButtons();
            }
        });
        constraint.gridx = 1;
        ingredientsPanel.add(addIngredient, constraint);


    }

//    ArrayList<JLabel> ingredientLabels = new ArrayList<>();
    //  ArrayList<JButton> removeButtons = new ArrayList<>();

    private void updateButtons() {
        constraint.gridx = 0;
        constraint.gridy = 3;

        for (String ingredient : ingredients) {
            JLabel current = new JLabel(ingredient);
            JButton removeCurrent = new JButton("Remove");
            ingredientsPanel.add(current, constraint);
            constraint.gridx = 1;
            ingredientsPanel.add(removeCurrent, constraint);
            constraint.gridx = 0;
            constraint.gridy++;
            removeCurrent.addActionListener(removalEvent ->
            {
                ingredients.remove(ingredient);
                updateButtons();
            });
            repaint();

        }
    }

    private void findRecipesByKeyword(SpoonacularController controller, String keyword) {
        controller.getRecipesByKeyword(keyword);
    }

    private void findByIngredients(SpoonacularController controller, String ingredients) {
        controller.findByIngredients(ingredients);
    }


    private void displayRecipeInfoDialog(SpoonacularController controller, JList recipeList) {

        recipeList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    controller.getRecipeInformation(recipeID);
                    createDialog();
                }
            }

        });


    }

    private void createDialog() {
        JDialog d = new JDialog();
        recipeTitle = new JLabel();
        recipeInfo = new JTextArea();
        recipeInfo.setLineWrap(true);
        recipeInfo.setWrapStyleWord(true);
        d.setSize(700, 500);
        d.setTitle(recipeTitle.getText());
        d.add(recipeInfo);
        d.setLocationRelativeTo(SpoonacularView.this);
        d.setVisible(true);
    }


    void showRecipes(RecipeInformation recipeInformation) {

        StringBuilder recipeBuilder = new StringBuilder();
        recipeBuilder.append("\t").append(recipeInformation.getTitle()).append("\n\n");
        for (int i = 0; i < recipeInformation.getExtendedIngredients().size(); i++) {
            recipeBuilder.append("  ");
            recipeBuilder.append(recipeInformation.getExtendedIngredients().get(i).getOriginalString());
            recipeBuilder.append("\n");
        }
        recipeInfo.setText(recipeBuilder.toString());
        recipeTitle.setText(recipeInformation.getTitle());
        recipeID = recipeInformation.getId();
    }


    void showFindByIngredient(ArrayList<Recipe> feed) {
        for (int i = 0; i < feed.size(); i++) {
            String recipe = " " + feed.get(i).getTitle() + "\n";
            model2.add(i, recipe);
            recipeIDMap.putIfAbsent(recipe, feed.get(i).getId());
        }
    }

    void showRecipesByKeyword(SpoonacularFeed feed) {
        for (int i = 0; i < feed.getRecipeList().size(); i++) {
            String recipe = " " + feed.getRecipeList().get(i).getTitle() + "\n";
            model1.add(i, recipe);
            recipeIDMap.putIfAbsent(recipe, feed.getRecipeList().get(i).getId());
        }

    }

    void showQuickSummary(Recipe recipe) {
        String summary = recipe.getSummary().replaceAll("<[^>]*>", "");
        String title = "\n" + recipe.getTitle();
        if (mode.equals("Search for a recipe")) {
            recipeSummary1.setText(title + "\n" + summary);
        }
        if (mode.equals("Lookup recipes by ingredient")) {
            recipeSummary2.setText(title + "\n" + summary);
        }
    }

    void setJoke(SpoonacularFeed feed) {
        String joke = feed.getJoke();
        foodJoke.setText("Here's a joke to make you smile! " + joke + "...");
    }


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }
}