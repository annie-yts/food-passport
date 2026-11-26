package view;

import interface_adapter.discover_dishes.*;
import use_case.discover_dishes.ApiException;

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
import java.util.List;
import java.util.Map;

/* The View when user discover dishes*/
public class DiscoverDishesView extends JPanel implements DiscoverDishesViewModelListener {

    private final DiscoverDishesViewModel discoverDishesViewModel;
    private final JTextField dishInputField = new JTextField(12);
    private DiscoverDishesController discoverDishesController = null;

    private final JButton prevPage;
    private final JTextField currPage = new JTextField("1", 2); // "1" since default is first page
    private final JButton nextPage;
    private final JButton apply;
    private final JButton viewMyBucketList;
    private final JButton viewRestaurants;
    private final JButton selectCountry;
    private final JButton search;

    private final Map<String, String> countryNamesByCode;

    public DiscoverDishesView(DiscoverDishesViewModel discoverDishesViewModel, Map<String, String> countryNamesByCode) {
        this.countryNamesByCode = countryNamesByCode;
        this.discoverDishesViewModel = discoverDishesViewModel;
        discoverDishesViewModel.addListener(this);

        final JLabel title = new JLabel(DiscoverDishesViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        // Distance to the left, top of the frame, and the size of the title
        title.setBounds(20, 10, 230, 50);

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

        //create the pop menu for countries
        JPopupMenu popupCountries = new JPopupMenu();
        List<String> countryCodes = List.copyOf(countryNamesByCode.keySet());
        JList<String> countryList = new JList<>(countryCodes.stream().map(countryNamesByCode::get).toArray(String[]::new));

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
                        int index = countryList.getSelectedIndex();
                        if (index >= 0 && !e.getValueIsAdjusting()) {
                            // update current state
                            DiscoverDishesState currentState = discoverDishesViewModel.getState();
                            currentState.setSearchTarget(countryCodes.get(index));
                            currentState.setSearchByDish(false);
                            discoverDishesViewModel.setState(currentState);
                            selectCountry.setText(countryList.getSelectedValue());
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
                            countryList.clearSelection();
                            selectCountry.setText(DiscoverDishesViewModel.COUNTRY_LABEL);
                            DiscoverDishesState currentState = discoverDishesViewModel.getState();
                            currentState.setSearchTarget(dishInputField.getText());
                            currentState.setSearchByDish(true);
                            discoverDishesViewModel.setState(currentState);
                        }

                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        //delete previous panels
                        for (int i = 0; i < 6; i++) {
                            if (currentState.getCurrentDish()[i] != null) { DiscoverDishesView.this.remove
                                    (currentState.getCurrentDish()[i]);}
                        }

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
        apply = new JButton(DiscoverDishesViewModel.APPLY_LABEL);
        paginationPanel.add(apply);
        // set pagination panel to the bottom left corner
        paginationPanel.setBounds(20, 500, 300, 40);
        // Just for adjustments, visible the frame of panel
        //paginationPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        apply.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {

                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        //delete previous panels
                        for (int i = 0; i < 6; i++) {
                            if (currentState.getCurrentDish()[i] != null) { DiscoverDishesView.this.remove
                                    (currentState.getCurrentDish()[i]);}
                        }

                        discoverDishesController.execute(
                                currentState.getSearchTarget(),
                                currentState.getSearchByDish(),
                                currentState.getPage());

                    }
                }
        );

        prevPage.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        DiscoverDishesState currentState = discoverDishesViewModel.getState();
                        if (currentState.getPage() > 1) {
                            int newPage = currentState.getPage() - 1;
                            currPage.setText(String.valueOf(newPage));
                            currentState.setPage(newPage);
                            discoverDishesViewModel.setState(currentState);

                            for (int i = 0; i < 6; i++) {
                                if (currentState.getCurrentDish()[i] != null) { DiscoverDishesView.this.remove
                                        (currentState.getCurrentDish()[i]);}
                            }

//                            // everytime press prevPage, the system will automatically search for dish
//                            discoverDishesController.execute(
//                                    currentState.getSearchTarget(),
//                                    currentState.getSearchByDish(),
//                                    currentState.getPage());

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

                        for (int i = 0; i < 6; i++) {
                            if (currentState.getCurrentDish()[i] != null) { DiscoverDishesView.this.remove
                                    (currentState.getCurrentDish()[i]);}
                        }

//                        // everytime press nextpage, the system will automatically search for dish
//                        discoverDishesController.execute(
//                                currentState.getSearchTarget(),
//                                currentState.getSearchByDish(),
//                                currentState.getPage());
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

                            for (int i = 0; i < 6; i++) {
                                if (currentState.getCurrentDish()[i] != null) { DiscoverDishesView.this.remove
                                        (currentState.getCurrentDish()[i]);}
                            }

//                            // everytime set page, the system will automatically search for dish
//                            discoverDishesController.execute(
//                                    currentState.getSearchTarget(),
//                                    currentState.getSearchByDish(),
//                                    currentState.getPage());

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
        this.setPreferredSize(new Dimension(900, 600));
        this.add(title);
        this.add(searchPanel);
        this.add(paginationPanel);
        this.add(tryPanel);

    }


    @Override
    public void stateChanged(String dishName, String countryCode, int dishIndex) {

        // Delete previous shown dishes
        DiscoverDishesState currState = discoverDishesViewModel.getState();

        // Add current dish
        JPanel thisDishCard = new DishCardPanel(dishName,
                countryNamesByCode.getOrDefault(countryCode, countryCode),
                22 * ((dishIndex / 2) + 1) + // spaces between column based on given index
                        270 * (dishIndex / 2), // horizontal location of panel based on given index
                200 - 100 * (int) Math.pow(-1, dishIndex)); // even index dish on first row of the view panel, vice versa
        currState.setCurrentDish(dishIndex, thisDishCard);
        discoverDishesViewModel.setState(currState);
        this.add(thisDishCard);
        this.revalidate();
        this.repaint();

    }

    @Override
    public void stateChanged(String dishName, String countryCode, String imageUrl, int dishIndex) {
        // Delete previous shown dishes
        DiscoverDishesState currState = discoverDishesViewModel.getState();

        // Add current dish
        JPanel thisDishCard = new DishCardPanel(dishName,
                countryNamesByCode.getOrDefault(countryCode, countryCode),
                imageUrl,
                22 * ((dishIndex / 2) + 1) + // spaces between column based on given index
                        270 * (dishIndex / 2), // horizontal location of panel based on given index
                200 - 100 * (int) Math.pow(-1, dishIndex)); // even index dish on first row of the view panel, vice versa
        currState.setCurrentDish(dishIndex, thisDishCard);
        discoverDishesViewModel.setState(currState);
        this.add(thisDishCard);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void stateChanged(String info) {
        JOptionPane.showMessageDialog(
                this,
                info,
                "Dish Not Found",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    @Override
    public void stateChanged(String info, int pages) {
        JPanel outOfScopePanel = new JPanel();
        outOfScopePanel.setBounds(20, 100, 300, 50); //location that the message shown
        JLabel outOfScopeLabel = new JLabel(info + pages);
        outOfScopeLabel.setBounds(40, 100, 300, 50);
        outOfScopePanel.add(outOfScopeLabel);


        DiscoverDishesState currState = discoverDishesViewModel.getState();
        currState.setCurrentDish(0, outOfScopePanel);

        discoverDishesViewModel.setState(currState);
        this.add(outOfScopePanel);
        this.revalidate();
        this.repaint();


    }
    @Override
    public void stateChanged(ApiException e) {
        JOptionPane.showMessageDialog(
                this,
                "Network error!",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }


    public void setDiscoverDishesController(DiscoverDishesController discoverDishesController) {
        this.discoverDishesController = discoverDishesController;
    }

    public void addBackToProfileButton(Runnable onClick) {
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(260, 20, 150, 30);
        backButton.addActionListener(evt -> onClick.run());
        this.add(backButton);
    }
}
