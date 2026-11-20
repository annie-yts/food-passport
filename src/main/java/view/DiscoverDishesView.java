package view;
import data_access.TheMealDBClient;
import interface_adapter.ApiAccessBoundary;
import interface_adapter.DiscoverDishesViewModel;
import interface_adapter.DiscoverDishesViewModelListener;
import interface_adapter.DiscoverDishesState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

/* The View when user discover dishes*/
public class DiscoverDishesView extends JPanel implements DiscoverDishesViewModelListener {

    private final DiscoverDishesViewModel discoverDishesViewModel;
    private final JTextField dishInputField = new JTextField(12);
//    private SignupController signupController = null;

    private final JButton prevPage;
    private final JButton nextPage;
    private final JButton viewMyBucketList;
    private final JButton viewRestaurants;
    private final JButton selectCountry;
    private final JButton search;


    //modules for data access
    private final ApiAccessBoundary apiAccessObject;


    public DiscoverDishesView(DiscoverDishesViewModel discoverDishesViewModel, ApiAccessBoundary apiAccessObject) {

        this.apiAccessObject = apiAccessObject;
        this.discoverDishesViewModel = discoverDishesViewModel;
        discoverDishesViewModel.addListener(this);

        final JLabel title = new JLabel(DiscoverDishesViewModel.TITLE_LABEL);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        // Distance to the left, top of the frame, and the size of the title
        title.setBounds(10, 1, 200, 50);

        //----------------------------------------------------------------------------------
        // Creates the search panel
        JPanel searchPanel = new JPanel();
        dishInputField.setText(DiscoverDishesViewModel.DISH_INPUT_LABEL);
        selectCountry = new JButton(DiscoverDishesViewModel.COUNTRY_LABEL);
        Dimension dimension = new Dimension(170, 30);
        selectCountry.setPreferredSize(dimension);
        selectCountry.setMaximumSize(dimension);
        selectCountry.setMinimumSize(dimension);
        search = new JButton(DiscoverDishesViewModel.SEARCH_BUTTON_LABEL);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        //create the pop manu for countries
        JPopupMenu popupCountries = new JPopupMenu();
        JList<String> countryList = new JList<>(apiAccessObject.getCountry());
        countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(countryList);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        popupCountries.add(scrollPane);

        //add the pop menu into selectCountry
        selectCountry.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        popupCountries.show(selectCountry, 0, selectCountry.getHeight());
                    }
                }
        );

        // let the countryList being selectable
        countryList.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String country = countryList.getSelectedValue();
                        if (country != null) {
                            // update current state
                            DiscoverDishesState currentState = discoverDishesViewModel.getState();
                            currentState.setSearchTarget(country);
                            currentState.setSearchByDish(false);
                            discoverDishesViewModel.setState(currentState);
                            selectCountry.setText(country);
                            popupCountries.setVisible(false);
                            //reset dishInputField
                            dishInputField.setText(DiscoverDishesViewModel.DISH_INPUT_LABEL);
                        }
                    }
                }
                );

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (!dishInputField.getText().equals(DiscoverDishesViewModel.DISH_INPUT_LABEL)) {
                            selectCountry.setText(DiscoverDishesViewModel.COUNTRY_LABEL);
                            DiscoverDishesState currentState = discoverDishesViewModel.getState();
                            currentState.setSearchTarget(dishInputField.getText());
                            currentState.setSearchByDish(true);
                            discoverDishesViewModel.setState(currentState);
                        }
                        
                    }
                }
        );

        searchPanel.add(selectCountry);
        searchPanel.add(dishInputField);
        searchPanel.add(search);
        // Places the search panel in the top right corner of the frame
        searchPanel.setBounds(500, 50, 360, 30);
        // Just for adjustments, visible the frame of panel
        // searchPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        //----------------------------------------------------------------------------------
        // Create pagination panel
        final JPanel paginationPanel = new JPanel();
        prevPage = new JButton(DiscoverDishesViewModel.PREV_PAGE_LABEL);
        paginationPanel.add(prevPage);
        nextPage = new JButton(DiscoverDishesViewModel.NEXT_PAGE_LABEL);
        paginationPanel.add(nextPage);
        // set pagination panel to the bottom left corner
        paginationPanel.setBounds(20, 500, 170, 40);
        // Just for adjustments, visible the frame of panel
        //paginationPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        final JPanel tryPanel = new JPanel();
        viewMyBucketList = new JButton(DiscoverDishesViewModel.BUCKET_LIST_LABEL);
        tryPanel.add(viewMyBucketList);
        viewRestaurants = new JButton(DiscoverDishesViewModel.NEARBY_RESTAURANT_LABEL);
        tryPanel.add(viewRestaurants);
        // set try panel to the bottom right corner
        tryPanel.setBounds(500, 500, 380, 40);
        // Just for adjustments, visible the frame of panel
//        tryPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        // CreateLayout
        this.setLayout(null);
        this.add(title);
        this.add(searchPanel);
        this.add(paginationPanel);
        this.add(tryPanel);


    }


    @Override
    public void stateChanged() {
        return;
    }


    //temporary place main method here to build the view quickly
    public static void main(String[] args) {
        final JFrame application = new JFrame("Discover Dishes");

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DiscoverDishesView discoverDishesView = new DiscoverDishesView(
                new DiscoverDishesViewModel(),
                new TheMealDBClient()
        );
        application.setContentPane(discoverDishesView);
        //    application.pack();
        application.setSize(900, 600);
        application.setVisible(true);
    }

}
