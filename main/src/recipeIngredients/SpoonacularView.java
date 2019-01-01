package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;


import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

@Singleton

public class SpoonacularView extends JFrame  {
    private JTextArea recipeInfo;
    private JLabel recipeTitle;
    private JLabel foodJoke;
    private JTextField[] ingredientsEntered = new JTextField[5];
    private JTextArea recipeSummary1;
    private JTextArea recipeSummary2;
    private int recipeID;
    private JButton searchButton;
    private DefaultListModel<String> model1 = new DefaultListModel<>();
    private DefaultListModel<String> model2 = new DefaultListModel<String>();
    private JList<String> recipeList1;
    private JList<String> recipeList2;
    private ArrayList<Integer> recipeIDs = new ArrayList<>();
    private String mode;
    private String keyword;
    private JTextField keywordField;
    private String summary;
    private String recipeDetails;

    @Inject
    public SpoonacularView(SpoonacularController controller) {
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
        tabbedPane.setBackground(Color.red);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 30, 10);

        JPanel ingredientsPanel = new JPanel(fl);
        JPanel keywordPanel = new JPanel(fl);
        JPanel recipePanel1 = new JPanel(new GridLayout(0, 1));
        JPanel recipePanel2 = new JPanel(new GridLayout(0, 1));
        JPanel tab1 = new JPanel(new GridLayout(0, 2));
        JPanel tab2 = new JPanel(new GridLayout(0, 2));

        foodJoke = new JLabel();
        //controller.getRandomJoke();

        keywordField = new JTextField();
        keywordField.setColumns(15);
        recipeTitle = new JLabel();
        recipeInfo = new JTextArea();
        recipeSummary1 = new JTextArea();
        recipeSummary1.setWrapStyleWord(true);
        recipeSummary1.setLineWrap(true);
        recipeSummary1.setColumns(25);
        recipeSummary1.setRows(60);
        recipeList1 = new JList<String>(model1);
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

        ingredientsPanel.add(new JLabel("  Enter up to 5 ingredients: "));
        for (int i = 0; i < ingredientsEntered.length; i++) {
            ingredientsEntered[i] = new JTextField();
            ingredientsEntered[i].setColumns(25);
            ingredientsPanel.add(ingredientsEntered[i]);
        }
        recipeList2 = new JList<String>(model2);
        recipeList2.setSelectionMode(SINGLE_SELECTION);
        recipeList2.setLayoutOrientation(JList.VERTICAL);
        recipeList2.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                int index = changedList.getSelectedIndex();
                recipeID = recipeIDs.get(index);
                controller.getQuickSummary(recipeID);
                recipeSummary2.setText(summary);
                ingredientsPanel.add(recipeSummary1);
            }


        });
        recipeSummary2 = new JTextArea();
        recipeSummary2.setWrapStyleWord(true);
        recipeSummary2.setLineWrap(true);
        recipeSummary2.setColumns(26);
        recipeSummary2.setRows(34);
        ingredientsPanel.add(recipeSummary2);
        recipePanel2.add(recipeList2);
        tab2.add(ingredientsPanel);
        tab2.add(recipePanel2);

        tabbedPane.add("1", tab1);
        tabbedPane.add("2", tab2);


        JPanel mainPanel = new JPanel(new BorderLayout());
        searchButton = new JButton("Search");
        mainPanel.add(searchButton, BorderLayout.AFTER_LAST_LINE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mode.equals("1")) {
                    keyword = keywordField.getText();
                    findRecipesByKeyword(controller, keyword);
                    recipePanel1.add(recipeList1);

                }

                if (mode.equals("2")) {
                    StringBuilder ingredientsBuilder = new StringBuilder();
                    for (int i = 0; i < ingredientsEntered.length; i++) {
                        ingredientsBuilder.append(ingredientsEntered[i].getText()).append(",");
                        findByIngredients(controller, ingredientsBuilder.toString());
                        recipePanel2.add(recipeList2);
                    }

                }

            }
        });

        displayRecipeInfoDialog(controller);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        Border border = BorderFactory.createEmptyBorder(20, 10, 20, 10);
        getRootPane().setBorder(border);

        add(mainPanel);

    }

    public void findRecipesByKeyword(SpoonacularController controller, String keyword) {
        controller.getRecipesByKeyword(keyword);
    }

    public void findByIngredients(SpoonacularController controller, String ingredients) {
        controller.findByIngredients(ingredients);
    }


    public void displayRecipeInfoDialog(SpoonacularController controller){
        controller.getRecipeInformation(recipeID);

        recipeList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    JOptionPane.showMessageDialog(null, recipeDetails);
                }
            }

        });
    }



    public void showRecipes(RecipeInformation recipeInformation) {
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

    public void showFindByIngredient(ArrayList<Recipe> feed) {
        for (int i = 0; i < feed.size(); i++) {
            String recipe = feed.get(i).getTitle() + "\n";
            model2.add(i, recipe);
            recipeIDs.add(feed.get(i).getId());
        }
    }

    public void showRecipesByKeyword(SpoonacularFeed feed) {
        for (int i = 0; i < feed.getRecipeList().size(); i++) {
            String recipe = feed.getRecipeList().get(i).getTitle() + "\n";
            model1.add(i, recipe);
            recipeIDs.add(feed.getRecipeList().get(i).getId());

        }

    }

    public void showQuickSummary(Recipe recipe) {
        summary = recipe.getSummary().replaceAll("<[^>]*>", "");
        if(mode.equals("1")){
            recipeSummary1.setText(summary);
        }
        if(mode.equals("2")){
            recipeSummary2.setText(summary);
        }

    }

    public void setJoke(SpoonacularFeed feed) {
        String joke = feed.getJoke();
        foodJoke.setText(joke + "...");
    }




    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        view.setVisible(true);

    }
}
