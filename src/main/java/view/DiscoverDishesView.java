package view;
import interface_adapter.DiscoverDishesViewModel;
import interface_adapter.DiscoverDishesViewModelListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

/* The View when user discover dishes*/
public class DiscoverDishesView extends JPanel implements ActionListener, DiscoverDishesViewModelListener {
    private final DiscoverDishesViewModel discoverDishesViewModel;
    private final JTextField dishInputField = new JTextField(15);
//    private SignupController signupController = null;

    private final JButton prevPage;
    private final JButton nextPage;
    private final JButton viewMyBucketList;
    private final JButton viewRestaurants;
    private final JButton selectCountry;


    public DiscoverDishesView(DiscoverDishesViewModel discoverDishesViewModel) {

        this.discoverDishesViewModel = discoverDishesViewModel;
        discoverDishesViewModel.addListener(this);

        final JLabel title = new JLabel(DiscoverDishesViewModel.TITLE_LABEL);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        // Distance to the left, top of the frame, and the size of the title
        title.setBounds(10, 1, 200, 50);

        // Creates the search panel
        JPanel searchPanel = new JPanel();
        JTextField inputDish = new JTextField(11);
        inputDish.setText("or search by dish...");
        selectCountry = new JButton(DiscoverDishesViewModel.COUNTRY_LABEL);
        searchPanel.add(selectCountry);
        searchPanel.add(inputDish);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        // Places the search panel in the top right corner of the frame
        searchPanel.setBounds(600, 50, 260, 30);
        // Just for adjustments, visible the frame of panel
        // searchPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));


        // Create pagination panel
        final JPanel paginationPanel = new JPanel();
        prevPage = new JButton(DiscoverDishesViewModel.PREV_PAGE_LABEL);
        paginationPanel.add(prevPage);
        nextPage = new JButton(DiscoverDishesViewModel.NEXT_PAGE_LABEL);
        paginationPanel.add(nextPage);
        // set pagination panel to the bottom left corner
        paginationPanel.setBounds(20, 500, 170, 40);
        // Just for adjustments, visible the frame of panel
//        paginationPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        final JPanel tryPanel = new JPanel();
        viewMyBucketList = new JButton(DiscoverDishesViewModel.BUCKET_LIST_LABEL);
        tryPanel.add(viewMyBucketList);
        viewRestaurants = new JButton(DiscoverDishesViewModel.NEARBY_RESTAURANT_LABEL);
        tryPanel.add(viewRestaurants);
        // set try panel to the bottom left corner
        tryPanel.setBounds(500, 500, 380, 40);
        // Just for adjustments, visible the frame of panel
//        tryPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        //建立布局
        this.setLayout(null);
        this.add(title);
        this.add(searchPanel);
        this.add(paginationPanel);
        this.add(tryPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        return;
    };

    @Override
    public void stateChanged() {
        return;
    }

}
