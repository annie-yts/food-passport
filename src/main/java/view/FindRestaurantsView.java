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

/**
 * The Find Restaurants Nearby screen.
 */
public class FindRestaurantsView extends JPanel implements FindRestaurantsViewModelListener {
    private final FindRestaurantsViewModel viewModel;

    private final JTextField isoField = new JTextField("JP", 4);
    private final JTextField addressField = new JTextField(25);
    private final JTextField radiusField = new JTextField("2", 4);
    private final JButton searchButton = new JButton(FindRestaurantsViewModel.SEARCH_BUTTON_LABEL);
    private final JButton backButton = new JButton(FindRestaurantsViewModel.BACK_BUTTON_LABEL);
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JLabel errorLabel = new JLabel(" ");

    public FindRestaurantsView(FindRestaurantsViewModel viewModel, FindRestaurantsController controller) {
        this.viewModel = viewModel;
        viewModel.addListener(this);

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel(FindRestaurantsViewModel.COUNTRY_LABEL + ":"));
        top.add(isoField);
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
            try {
                final double radius = Double.parseDouble(radiusField.getText().trim());
                controller.execute(isoField.getText().trim(), addressField.getText().trim(), radius);
            }
            catch (NumberFormatException ex) {
                errorLabel.setText("Radius must be a number.");
            }
        });
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void stateChanged() {
        final FindRestaurantsState state = viewModel.getState();
        listModel.clear();
        state.getResultLines().forEach(listModel::addElement);
        errorLabel.setText(state.getErrorMessage().isEmpty() ? " " : state.getErrorMessage());
    }
}
