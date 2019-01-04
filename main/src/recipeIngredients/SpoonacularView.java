package recipeIngredients;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
// set up UI look & feel

@Singleton
public class SpoonacularView extends JFrame {
    private JTextArea recipeInfo;
    private JLabel recipeTitle;
    private JLabel foodJoke;
    private ArrayList<String> ingredients = new ArrayList<>();
    private JTextField ingredientField = new HintTextField("apple");
    private JTextArea recipeSummary1;
    private JTextArea recipeSummary2;
    private int recipeID;
    private Map<String, Integer> recipeIDMap = new HashMap<>();
    private DefaultListModel model1 = new DefaultListModel<>();
    private DefaultListModel<String> model2 = new DefaultListModel<>();
    private JList recipeList1;
    private JList<String> recipeList2;
    private String mode;
    private String keyword;
    private JTextField keywordField;
    private JPanel ingredientsPanel;
    private GridBagConstraints constraint;
    private ArrayList<JLabel> ingredientLabels = new ArrayList<>();
    private ArrayList<JButton> removeButtons = new ArrayList<>();
    private JLabel recipePic1 = new JLabel();
    private JLabel recipePic2 = new JLabel();
    private SpoonacularController controller;

    @Inject
    public SpoonacularView(SpoonacularController controller) {
        this.controller = controller;
        setLocation(240, 180);
        setSize(1000, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recipe Box");

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
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 30, 10);

        createIngredientPanel();
        JPanel keywordPanel = new JPanel(fl);
        JPanel mainIngredientsPanel = new JPanel();
        mainIngredientsPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 60, 10));
        JPanel recipePanel1 = new JPanel(new GridLayout(0, 1));
        JPanel recipePanel2 = new JPanel(new GridLayout(0, 1));
        recipePanel1.setBorder(BorderFactory.createEmptyBorder(40, 10, 15, 26));
        recipePanel2.setBorder(BorderFactory.createEmptyBorder(30, 16, 20, 26));


        JPanel tab1 = new JPanel(new GridLayout(0, 3));
        JPanel tab2 = new JPanel(new GridLayout(0, 3));

        foodJoke = new JLabel();
        controller.getRandomJoke();

        keywordField = new JTextField();
        keywordField.setColumns(15);
        keywordPanel.add(new JLabel("Search for..."));
        keywordPanel.add(keywordField);
        recipeSummary1 = new JTextArea();
        recipeSummary1.setEditable(false);
        recipeSummary1.setWrapStyleWord(true);
        recipeSummary1.setLineWrap(true);
        recipeSummary1.setColumns(22);
        recipeSummary1.setRows(21);
        recipeSummary1.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(recipeSummary1);
        scrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recipeList1 = new JList<>(model1);
        recipeList1.setSelectionMode(SINGLE_SELECTION);
        recipeList1.setLayoutOrientation(JList.VERTICAL);
        recipeList1.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {

                JList changedList = (JList) e.getSource();
                Object item = changedList.getSelectedValue();
                recipeID = recipeIDMap.get(item.toString());
                controller.getQuickSummary(recipeID);
                keywordPanel.add(scrollPane1);
            }


        });

        keywordPanel.add(scrollPane1);
        recipePanel1.add(recipeList1);
        tab1.add(keywordPanel);
        tab1.add(recipePanel1);

        tab1.add(recipePic1);

        JPanel recipeSummaryPanel = new JPanel();
        recipeSummaryPanel.setLayout(new BoxLayout(recipeSummaryPanel, BoxLayout.PAGE_AXIS));
        recipeSummaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));

        recipeSummary2 = new JTextArea();
        recipeSummary2.setWrapStyleWord(true);
        recipeSummary2.setLineWrap(true);
        recipeSummary2.setEditable(false);
        recipeSummary2.setColumns(19);
        recipeSummary2.setRows(7);

        JScrollPane scrollPane2 = new JScrollPane(recipeSummary2);
        scrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recipeList2 = new JList<>(model2);
        recipeList2.setSelectionMode(SINGLE_SELECTION);
        recipeList2.setLayoutOrientation(JList.VERTICAL);
        recipeList2.setFixedCellWidth(275);
        recipeList2.addListSelectionListener((ListSelectionEvent e) -> {

            if (!e.getValueIsAdjusting()) {
                JList changedList = (JList) e.getSource();
                Object item = changedList.getSelectedValue();
                recipeID = recipeIDMap.get(item.toString());
                controller.getQuickSummary(recipeID);
                recipeSummaryPanel.add(scrollPane2);
            }
        });
        recipeSummaryPanel.add(scrollPane2);
        JLabel ingredientsLabel = new JLabel("Enter your ingredients here:");
        mainIngredientsPanel.add(ingredientsLabel);
        mainIngredientsPanel.add(ingredientsPanel);
        mainIngredientsPanel.add(recipePanel2);

        tab2.add(mainIngredientsPanel);

        tab2.add(recipeSummaryPanel);

        tab2.add(recipePic2);


        tabbedPane.add("Search for a recipe", tab1);
        tabbedPane.add("Lookup recipes by ingredient", tab2);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x9F1E2C));
        setBackground(new Color(0x9F1E2C));
        JButton searchButton = new JButton("Search");
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));
        menuPanel.setBackground(new Color(0x9F1E2C));
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.white);
        menuPanel.add(searchButton, BorderLayout.EAST);
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

                });
                displayRecipeInfoDialog(controller, recipeList2);

            }
        });

        mainPanel.add(menuPanel, BorderLayout.SOUTH);
        mainPanel.add(foodJoke, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        Border border = BorderFactory.createEmptyBorder(20, 10, 20, 10);
        getRootPane().setBorder(border);
        add(mainPanel);
    }


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
                int placeToPut;
                if (ingredients.size() == 0) {
                    placeToPut = 0;
                } else {
                    placeToPut = ingredients.size();
                }
                ingredients.add(placeToPut, ingredientField.getText());
                ingredientLabels.add(placeToPut, new JLabel(ingredientField.getText()));
                removeButtons.add(placeToPut, new JButton("Remove"));
                removeButtons.get(removeButtons.size() - 1).addActionListener(this::updateButtonsandLabels);
                constraint.gridx = 0;
                constraint.gridy = 3 + ingredientLabels.size() - 1;
                ingredientsPanel.add(ingredientLabels.get(ingredientLabels.size() - 1), constraint);
                constraint.gridx = 1;
                ingredientsPanel.add(removeButtons.get(removeButtons.size() - 1), constraint);
                repaint();
            }
            ingredientField.setText("");
        });
        constraint.gridx = 1;
        constraint.gridy = 0;
        ingredientsPanel.add(addIngredient, constraint);


    }


    private void updateButtonsandLabels(ActionEvent event) {
        JButton source = (JButton) event.getSource();
        int index = removeButtons.indexOf(source);
        ingredientsPanel.remove(source);
        ingredientsPanel.remove(ingredientLabels.get(index));
        ingredients.remove(index);
        ingredientLabels.remove(index);
        removeButtons.remove(index);
        repaint();
        for (int x = index; x < ingredients.size(); x++) {
            constraint.gridx = 0;
            constraint.gridy = x + 3;
            JLabel currentLabel = ingredientLabels.get(x);
            ingredientsPanel.remove(currentLabel);
            ingredientsPanel.add(currentLabel, constraint);
            constraint.gridx = 1;
            ingredientsPanel.remove(removeButtons.get(x));
            ingredientsPanel.add(removeButtons.get(x), constraint);
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
        recipeInfo.setEditable(false);
        recipeInfo.setLineWrap(true);
        recipeInfo.setWrapStyleWord(true);
        recipeInfo.setEditable(false);
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


    void showFindByIngredient(List<Recipe> feed) {
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
            recipeSummary1.setText(title + "\n\n" + summary);
            controller.getRecipeImage(recipe.getId(), 1);

        }
        if (mode.equals("Lookup recipes by ingredient")) {
            recipeSummary2.setText(title + "\n\n" + summary);
            controller.getRecipeImage(recipe.getId(), 2);

        }
    }

    void setJoke(SpoonacularFeed feed) {
        String joke = feed.getJoke();
        foodJoke.setText("Here's a joke to make you smile! " + joke + "...");
    }

    void setImage(String imageString, int numTab) {
        ImageIcon imageIcon;
        BufferedImage image;
        try {
            if (!imageString.isEmpty()) {
                System.setProperty("http.agent", "Chrome");
                image = ImageIO.read(new URL(imageString));
                imageIcon = new ImageIcon(image.getScaledInstance(300, 250, Image.SCALE_DEFAULT));

            } else {
                imageIcon = new ImageIcon(ImageIO.read(new File("no image.png")).
                        getScaledInstance(300, 250, Image.SCALE_DEFAULT));
            }

            if (numTab == 1) {
                recipePic1.setIcon(imageIcon);
            }
            if (numTab == 2) {
                recipePic2.setIcon(imageIcon);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SpoonacularModule());
        SpoonacularView view = injector.getInstance(SpoonacularView.class);
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            // handle exception
        }
        view.setVisible(true);

    }


}