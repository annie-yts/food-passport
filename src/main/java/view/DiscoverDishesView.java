package view;
import data_access.TheMealDBClient;
import interface_adapter.DiscoverDishesController;
import use_case.discover_dishes.ApiAccessBoundary;
import interface_adapter.DiscoverDishesViewModel;
import interface_adapter.DiscoverDishesViewModelListener;
import interface_adapter.DiscoverDishesState;
import use_case.discover_dishes.DiscoverDishesInputBoundary;
import use_case.discover_dishes.DiscoverDishesInteractor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

/* The View when user discover dishes*/
public class DiscoverDishesView extends JPanel implements DiscoverDishesViewModelListener {

    private final DiscoverDishesViewModel discoverDishesViewModel;
    private final JTextField dishInputField = new JTextField(12);
    private DiscoverDishesController discoverDishesController = null;

    private final JButton prevPage;
    private final JTextField currPage = new JTextField("1", 2);
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
                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        discoverDishesController.execute(
                                currentState.getSearchTarget(),
                                currentState.getSearchByDish(),
                                currentState.getPage());
                        
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
        paginationPanel.add(currPage);
        nextPage = new JButton(DiscoverDishesViewModel.NEXT_PAGE_LABEL);
        paginationPanel.add(nextPage);
        // set pagination panel to the bottom left corner
        paginationPanel.setBounds(20, 500, 200, 40);
        // Just for adjustments, visible the frame of panel
        //paginationPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        prevPage.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        if (currentState.getPage() > 1) {
                            int newPage = currentState.getPage() - 1;
                            currPage.setText(String.valueOf(newPage));
                            currentState.setPage(newPage);
                            discoverDishesViewModel.setState(currentState);
                        }

                    }
                }
        );
        nextPage.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        int newPage = currentState.getPage() + 1;
                        currPage.setText(String.valueOf(newPage));
                        currentState.setPage(newPage);
                        discoverDishesViewModel.setState(currentState);
                    }
                }
        );

        ((AbstractDocument) currPage.getDocument())
                .setDocumentFilter(new DocumentFilter() {

                    @Override
                    public void replace(FilterBypass fb, int offset,
                                        int length, String text, AttributeSet attrs)
                            throws BadLocationException {

                        String current = fb.getDocument().getText(
                                0, fb.getDocument().getLength());

                        String next = current.substring(0, offset)
                                + (text == null ? "" : text)
                                + current.substring(offset + length);

                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        if (next.matches("[1-9][0-9]*")) {
                            super.replace(fb, offset, length, text, attrs);

                            currentState.setPage(Integer.parseInt(next));
                            discoverDishesViewModel.setState(currentState);

                        } else {
                            fb.replace(0,
                                    fb.getDocument().getLength(),
                                    String.valueOf(currentState.getPage()),
                                    attrs);
                        }
                    }

                    @Override
                    public void insertString(FilterBypass fb, int offset,
                                             String text, AttributeSet attrs)
                            throws BadLocationException {
                        replace(fb, offset, 0, text, attrs);
                    }

                    @Override
                    public void remove(FilterBypass fb, int offset, int length)
                            throws BadLocationException {
                        replace(fb, offset, length, "", null);
                    }
                });


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

    public void setDiscoverDishesController(DiscoverDishesController discoverDishesController) {
        this.discoverDishesController = discoverDishesController;
    }

    //temporary place main method here to build the view quickly
    public static void main(String[] args) {
        final JFrame application = new JFrame("Discover Dishes");

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ApiAccessBoundary theMealDBObject = new TheMealDBClient();
        DiscoverDishesView discoverDishesView = new DiscoverDishesView(
                new DiscoverDishesViewModel(),
                theMealDBObject
        );
        DiscoverDishesInputBoundary interactor = new DiscoverDishesInteractor(theMealDBObject);
        DiscoverDishesController dishesController = new DiscoverDishesController(interactor);
        discoverDishesView.setDiscoverDishesController(dishesController);
        application.setContentPane(discoverDishesView);
        //    application.pack();
        application.setSize(900, 600);
        application.setVisible(true);
    }

}
