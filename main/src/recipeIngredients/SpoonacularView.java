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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

@Singleton

public class SpoonacularView extends JFrame {
    private JTextArea recipeInfo;
    private JLabel recipeTitle;
    private JLabel foodJoke;
    private JTextField ingredientEntered = new JTextField("Enter your ingredient here.");
    private ArrayList<String> ingredients = new ArrayList<>();
    private JButton addIngredient;
    private JTextArea recipeSummary1;
    private JTextArea recipeSummary2;
    private int recipeID;
    private JButton searchButton;
    private DefaultListModel<String> model1 = new DefaultListModel<>();
    private DefaultListModel<String> model2 = new DefaultListModel<>();
    private JList<String> recipeList1;
    private JList<String> recipeList2;
    private ArrayList<Integer> recipeIDs = new ArrayList<>();
    private String mode;
    private String keyword;
    private JTextField keywordField;
    private String summary;
    private String recipeDetails;
    private JPanel ingredientPanel;

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
        tabbedPane.setBackground(Color.red);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 30, 10);

        JPanel keywordPanel = new JPanel(fl);
        JPanel ingredientSearchPanel = new JPanel();
        ingredientSearchPanel.setLayout(new BoxLayout(ingredientSearchPanel, BoxLayout.PAGE_AXIS));
        createIngredientPanel();
        ingredientSearchPanel.add(ingredientPanel);

        JPanel recipePanel1 = new JPanel(new GridLayout(0, 1));
        JPanel recipePanel2 = new JPanel(new GridLayout(0, 1));
        JPanel tab1 = new JPanel(new GridLayout(0, 2));
        JPanel tab2 = new JPanel(new GridLayout(0, 2));

        foodJoke = new JLabel();
        controller.getRandomJoke();

        keywordField = new JTextField();
        keywordField.setColumns(15);
        recipeTitle = new JLabel();
        recipeInfo = new JTextArea();
        recipeSummary1 = new JTextArea();
        recipeSummary1.setWrapStyleWord(true);
        recipeSummary1.setLineWrap(true);
        recipeSummary1.setColumns(25);
        recipeSummary1.setRows(60);
        recipeList1 = new JList<>(model1);
        recipeList1.setSelectionMode(SINGLE_SELECTION);
        recipeList1.setLayoutOrientation(JList.VERTICAL);
        recipeList1.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                int index = changedList.getSelectedIndex();
                recipeID = recipeIDs.get(index);
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
        recipeSummary2.setColumns(25);
        recipeSummary2.setRows(60);
        recipeList2 = new JList<>(model1);
        recipeList2.setSelectionMode(SINGLE_SELECTION);
        recipeList2.setLayoutOrientation(JList.VERTICAL);
        recipeList2.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                int index = changedList.getSelectedIndex();
                recipeID = recipeIDs.get(index);
                controller.getQuickSummary(recipeID);
                ingredientSearchPanel.add(recipeSummary2);
            }


        });
        ingredientSearchPanel.add(recipeSummary2);
        tab2.add(ingredientSearchPanel);
        recipePanel2.add(recipeList2);
        tab2.add(recipePanel2);

        tabbedPane.add("1", tab1);
        tabbedPane.add("2", tab2);

        JPanel mainPanel = new JPanel(new BorderLayout());
        searchButton = new JButton("Search");
        mainPanel.add(searchButton, BorderLayout.AFTER_LAST_LINE);
        searchButton.addActionListener(e -> {
            if (mode.equals("1")) {
                keyword = keywordField.getText();
                findRecipesByKeyword(controller, keyword);
                recipePanel1.add(recipeList1);

            }

            if (mode.equals("2")) {
                StringBuilder ingredientsBuilder = new StringBuilder();
                IntStream.range(0, ingredients.size()).forEach(i -> {
                    ingredientsBuilder.append(ingredients.get(i)).append(",");
                    findByIngredients(controller, ingredientsBuilder.toString());
                    recipePanel2.add(recipeList2);
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

        ingredientPanel = new JPanel(new GridBagLayout());
        constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(3, 2, 3, 2);
        ingredientPanel.add(ingredientEntered, constraint);
        addIngredient = new JButton("Add");
        addIngredient.addActionListener(addEvent ->
        {
            if (ingredients.size() < 5) {
                ingredients.add(ingredientEntered.getText());
                updateButtons();
            }
        });
        constraint.gridx = 1;
        ingredientPanel.add(addIngredient, constraint);


    }

    ArrayList<JLabel> ingredientLabels = new ArrayList<>();
    ArrayList<JButton> removeButtons = new ArrayList<>();

    private void updateButtons() {
        constraint.gridx = 0;
        constraint.gridy = 3;

        for (String ingredient : ingredients) {
            JLabel current = new JLabel(ingredient);
            JButton removeCurrent = new JButton("Remove");
            ingredientPanel.add(current, constraint);
            constraint.gridx = 1;
            ingredientPanel.add(removeCurrent, constraint);
            constraint.gridx = 0;
            constraint.gridy++;
            removeCurrent.addActionListener(removalEvent ->
            {
                ingredients.remove(ingredient);
                updateButtons();
            });


        }
    }

    private void findRecipesByKeyword(SpoonacularController controller, String keyword) {
        controller.getRecipesByKeyword(keyword);
    }

    private void findByIngredients(SpoonacularController controller, String ingredients) {
        controller.findByIngredients(ingredients);
    }


    void showRecipes(RecipeInformation recipeInformation) {
        System.out.println(recipeInformation.getTitle());

        StringBuilder recipeBuilder = new StringBuilder();
        for (int i = 0; i < recipeInformation.getExtendedIngredients().size(); i++) {
            recipeBuilder.append("\t");
            recipeBuilder.append(recipeInformation.getExtendedIngredients().get(i).getOriginalString());
            recipeBuilder.append("\n");
        }
        recipeDetails = recipeBuilder.toString();
        recipeTitle.setText(recipeInformation.getTitle());
        recipeID = recipeInformation.getId();
    }

    void showFindByIngredient(ArrayList<Recipe> feed) {
        for (int i = 0; i < feed.size(); i++) {
            String recipe = feed.get(i).getTitle() + "\n";
            model2.add(i, recipe);
            recipeIDs.add(feed.get(i).getId());
        }
    }

    void showRecipesByKeyword(SpoonacularFeed feed) {
        for (int i = 0; i < feed.getRecipeList().size(); i++) {
            String recipe = feed.getRecipeList().get(i).getTitle() + "\n";
            model1.add(i, recipe);
            recipeIDs.add(feed.getRecipeList().get(i).getId());

        }

    }

    void showQuickSummary(Recipe recipe) {
        summary = recipe.getSummary().replaceAll("<[^>]*>", "");
        if (mode.equals("1")) {
            recipeSummary1.setText(summary);
        }
        if (mode.equals("2")) {
            recipeSummary2.setText(summary);
        }

    }

    void setJoke(SpoonacularFeed feed) {
        String joke = feed.getJoke();
        foodJoke.setText("Here's a random food joke:    " + joke + "...");
    }


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }
}