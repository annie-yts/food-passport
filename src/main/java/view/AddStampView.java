package view;

import Entities.PlaceType;
import interface_adapter.add_stamp.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;

/**
 * The Add Stamp screen: search for a dish (or enter one manually), fill in the
 * details, and save it as a stamp in the user's passport.
 */
public class AddStampView extends JPanel implements AddStampViewModelListener {
    private static final Color ERROR_COLOUR = new Color(180, 30, 30);
    private static final Color SUCCESS_COLOUR = new Color(20, 120, 40);

    private final AddStampViewModel viewModel;
    private final SearchDishController searchController;
    private final StampDishController stampController;

    private final JTextField searchField = new JTextField(20);
    private final JButton searchButton = new JButton(AddStampViewModel.SEARCH_BUTTON_LABEL);
    private final DefaultListModel<String> suggestionListModel = new DefaultListModel<>();
    private final JList<String> suggestionList = new JList<>(suggestionListModel);

    private final JCheckBox manualCheckBox = new JCheckBox(AddStampViewModel.MANUAL_LABEL);
    private final JTextField dishNameField = new JTextField(20);
    private final JComboBox<String> countryBox = new JComboBox<>();

    private final JTextField dateField = new JTextField(10);
    private final JComboBox<PlaceType> placeBox = new JComboBox<>(PlaceType.values());
    private final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
    private final JTextArea notesArea = new JTextArea(3, 20);
    private final JTextField restaurantField = new JTextField(20);

    private final JButton saveButton = new JButton(AddStampViewModel.SAVE_BUTTON_LABEL);
    private final JLabel statusLabel = new JLabel(" ");
    private final JLabel summaryLabel = new JLabel(" ");

    /**
     * Builds the screen and connects it to its view model and controllers.
     *
     * @param viewModel the Add Stamp view model
     * @param searchController sends searches to the Search Dish use case
     * @param stampController sends stamps to the Stamp Dish use case
     */
    public AddStampView(AddStampViewModel viewModel,
                        SearchDishController searchController,
                        StampDishController stampController) {
        this.viewModel = viewModel;
        this.searchController = searchController;
        this.stampController = stampController;
        viewModel.addListener(this);

        buildLayout();
        addListeners();

        stateChanged();
    }

    private void buildLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel(AddStampViewModel.TITLE_LABEL);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(row(title));

        add(row(searchField, searchButton));

        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setVisibleRowCount(6);
        JScrollPane listScroll = new JScrollPane(suggestionList);
        listScroll.setPreferredSize(new Dimension(380, 120));
        add(row(listScroll));

        add(row(manualCheckBox));
        add(row(new JLabel(AddStampViewModel.DISH_NAME_LABEL), dishNameField));
        countryBox.setPreferredSize(new Dimension(220, countryBox.getPreferredSize().height));
        add(row(new JLabel(AddStampViewModel.COUNTRY_LABEL), countryBox));

        dateField.setText(LocalDate.now().toString());
        add(row(new JLabel(AddStampViewModel.DATE_LABEL), dateField));
        add(row(new JLabel(AddStampViewModel.PLACE_LABEL), placeBox));
        add(row(new JLabel(AddStampViewModel.RESTAURANT_LABEL), restaurantField));
        add(row(new JLabel(AddStampViewModel.RATING_LABEL), ratingSpinner));
        notesArea.setLineWrap(true);
        add(row(new JLabel(AddStampViewModel.NOTES_LABEL), new JScrollPane(notesArea)));

        add(row(saveButton));
        add(row(statusLabel));
        add(row(summaryLabel));
    }

    /** Puts components side by side in one left-aligned row. */
    private JPanel row(JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JComponent component : components) {
            panel.add(component);
        }
        panel.setAlignmentX(LEFT_ALIGNMENT);
        return panel;
    }

    private void addListeners() {
        searchButton.addActionListener(e -> searchController.search(searchField.getText()));
        searchField.addActionListener(e -> searchController.search(searchField.getText()));

        manualCheckBox.addActionListener(e -> {
            viewModel.getState().setManualMode(manualCheckBox.isSelected());
            updateEnabledFields();
        });
        suggestionList.addListSelectionListener(e -> updateEnabledFields());
        placeBox.addActionListener(e -> updateEnabledFields());

        saveButton.addActionListener(e -> onSave());
    }

    private void onSave() {
        AddStampState state = viewModel.getState();
        String dishId;
        String dishName;
        String country;
        String category;
        String thumbnailUrl;

        if (manualCheckBox.isSelected()) {
            dishId = null;
            dishName = dishNameField.getText();
            country = (String) countryBox.getSelectedItem();
            category = null;
            thumbnailUrl = null;
        } else {
            DishSuggestion selected = getSelectedSuggestion();
            if (selected == null) {
                showError("Pick a dish from the list, or tick \"Enter manually\".");
                return;
            }
            dishId = selected.getDishId();
            dishName = selected.getDishName();
            category = selected.getCategory();
            thumbnailUrl = selected.getThumbnailUrl();
            if (selected.getCountry() == null) {
                country = (String) countryBox.getSelectedItem();
            } else {
                country = selected.getCountry();
            }
        }

        String restaurantName;
        if (restaurantField.isEnabled()) {
            restaurantName = restaurantField.getText();
        } else {
            restaurantName = "";
        }

        stampController.stamp(state.getUserId(), dishId, dishName, country, category, thumbnailUrl, dateField.getText(),
                (PlaceType) placeBox.getSelectedItem(), (Integer) ratingSpinner.getValue(), notesArea.getText(),
                restaurantName);
    }

    @Override
    public void stateChanged() {
        AddStampState state = viewModel.getState();

        suggestionListModel.clear();
        for (DishSuggestion suggestion : state.getSuggestions()) {
            String countryText;
            if (suggestion.getCountry() == null) {
                countryText = "pick a country";
            } else {
                countryText = suggestion.getCountry();
            }
            suggestionListModel.addElement(suggestion.getDishName() + " - " + countryText);
        }

        manualCheckBox.setSelected(state.isManualMode());

        List<String> countries = state.getCountries();
        if (countryBox.getItemCount() != countries.size()) {
            countryBox.removeAllItems();
            for (String country : countries) {
                countryBox.addItem(country);
            }
        }

        if (!state.getErrorMessage().isEmpty()) {
            showError(state.getErrorMessage());
        } else if (!state.getSuccessMessage().isEmpty()) {
            statusLabel.setForeground(SUCCESS_COLOUR);
            statusLabel.setText(state.getSuccessMessage());
        } else {
            statusLabel.setText(" ");
        }

        summaryLabel.setText(state.getPassportSummary());

        if (state.isClearForm()) {
            clearForm();
            state.setClearForm(false);
        }

        updateEnabledFields();
    }

    private void showError(String message) {
        statusLabel.setForeground(ERROR_COLOUR);
        statusLabel.setText(message);
    }

    /** Turns fields on or off depending on manual mode, the selected dish, and the place. */
    private void updateEnabledFields() {
        boolean manual = manualCheckBox.isSelected();
        DishSuggestion selected = getSelectedSuggestion();

        suggestionList.setEnabled(!manual);
        dishNameField.setEnabled(manual);
        countryBox.setEnabled(manual || (selected != null && selected.getCountry() == null));
        restaurantField.setEnabled(placeBox.getSelectedItem() == PlaceType.RESTAURANT);
    }

    /** Returns the suggestion picked in the list, or null if none is picked. */
    private DishSuggestion getSelectedSuggestion() {
        int index = suggestionList.getSelectedIndex();
        List<DishSuggestion> suggestions = viewModel.getState().getSuggestions();
        if (manualCheckBox.isSelected() || index < 0 || index >= suggestions.size()) {
            return null;
        }
        return suggestions.get(index);
    }

    /** Empties the form after a successful save, ready for the next dish. */
    private void clearForm() {
        suggestionList.clearSelection();
        manualCheckBox.setSelected(false);
        viewModel.getState().setManualMode(false);
        dishNameField.setText("");
        dateField.setText(LocalDate.now().toString());
        placeBox.setSelectedItem(PlaceType.HOME);
        ratingSpinner.setValue(5);
        notesArea.setText("");
        restaurantField.setText("");
    }
}