package view;

import interface_adapter.find_restaurants.FindRestaurantsController;
import interface_adapter.find_restaurants.FindRestaurantsState;
import interface_adapter.find_restaurants.FindRestaurantsViewModel;
import interface_adapter.find_restaurants.FindRestaurantsViewModelListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.util.Map;

/**
 * The Find Restaurants Nearby screen.
 */
public class FindRestaurantsView extends JPanel implements FindRestaurantsViewModelListener {
    private final FindRestaurantsViewModel viewModel;
    private FindRestaurantsController controller;

    private final JComboBox<String> countryBox = new JComboBox<>();
    private final Map<String, String> codesByName = new HashMap<>();
    private final Map<String, String> namesByCode = new HashMap<>();
    private final JTextField addressField = new JTextField(25);
    private final JTextField radiusField = new JTextField("2", 4);
    private final JButton searchButton = new JButton(FindRestaurantsViewModel.SEARCH_BUTTON_LABEL);
    private final JButton backButton = new JButton(FindRestaurantsViewModel.BACK_BUTTON_LABEL);
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JLabel errorLabel = new JLabel(" ");

    /**
     * Builds the screen and registers it to hear about state changes.
     *
     * @param viewModel the view model holding this screen's state
     * @param countryNamesByCode country names keyed by their ISO allpha-3 codes
     */
    public FindRestaurantsView(FindRestaurantsViewModel viewModel, Map<String, String> countryNamesByCode) {
        this.viewModel = viewModel;
        viewModel.addListener(this);

        for (Map.Entry<String, String> entry : countryNamesByCode.entrySet()) {
            codesByName.put(entry.getValue(), entry.getKey());
            namesByCode.put(entry.getKey(), entry.getValue());
            countryBox.addItem(entry.getValue());
        }

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel(FindRestaurantsViewModel.COUNTRY_LABEL + ":"));
        top.add(countryBox);
        top.add(new JLabel(FindRestaurantsViewModel.ADDRESS_LABEL + ":"));
        top.add(addressField);
        top.add(new JLabel(FindRestaurantsViewModel.RADIUS_LABEL + ":"));
        top.add(radiusField);
        top.add(searchButton);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(new JList<>(listModel)), BorderLayout.CENTER);

        final JPanel bottom = new JPanel(new BorderLayout());
        errorLabel.setForeground(Color.RED);
        bottom.add(errorLabel, BorderLayout.CENTER);
        bottom.add(backButton, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        searchButton.addActionListener(event -> {
            if (controller == null) {
                return;
            }
            try {
                final double radius = Double.parseDouble(radiusField.getText().trim());
                final String isoCode = codesByName.get((String) countryBox.getSelectedItem());
                controller.execute(isoCode, addressField.getText().trim(), radius);
            }
            catch (NumberFormatException ex) {
                errorLabel.setText("Radius must be a number.");
            }
        });
    }

    /**
     * Sets the controller that runs the search.
     *
     * @param controller the find restaurants controller
     */
    public void setFindRestaurantsController(FindRestaurantsController controller) {
        this.controller = controller;
    }

    /**
     * Sets what happens when the back button is pressed.
     *
     * @param action the action to run
     */
    public void setOnBack(Runnable action) {
        backButton.addActionListener(event -> action.run());
    }

    @Override
    public void stateChanged() {
        final FindRestaurantsState state = viewModel.getState();
        listModel.clear();
        state.getResultLines().forEach(listModel::addElement);
        errorLabel.setText(state.getErrorMessage().isEmpty() ? " " : state.getErrorMessage());
    }

    /**
     * Selects the given country in the dropdown.
     *
     * @param isoCode the ISO alpha-3 code of the country
     */
    public void selectCountry(String isoCode) {
        final String name = namesByCode.get(isoCode);
        if (name != null) {
            countryBox.setSelectedItem(name);
        }
    }
}
