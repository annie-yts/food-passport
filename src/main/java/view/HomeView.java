package view;

import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;
import interface_adapter.home.HomeViewModelListener;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import java.awt.*;

/**
 * The Home screen: the hub shown after logging in, with a button for each
 * part of the app and a Log Out button.
 */
public class HomeView extends JPanel implements HomeViewModelListener {

    private final HomeViewModel homeViewModel;
    private final JLabel loggedInAs = new JLabel();

    private final JButton addStamp = new JButton("Add Stamp");
    private final JButton worldMap = new JButton("World Map");
    private final JButton discover = new JButton("Discover");
    private final JButton profile = new JButton("Profile");
    private final JButton logOut = new JButton("Log Out");
    private final JButton findRestaurants = new JButton("Find Restaurants");

    private LogoutController logoutController;

    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addListener(this);

        final JLabel title = new JLabel("Home");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        loggedInAs.setAlignmentX(Component.CENTER_ALIGNMENT);

        // The main buttons in a 3 x 2 grid
        final JPanel grid = new JPanel(new GridLayout(3, 2, 10, 10));
        for (JButton button : new JButton[] {addStamp, worldMap, discover, findRestaurants, profile}) {
            button.setPreferredSize(new Dimension(150, 50));
            grid.add(button);
        }
        grid.setMaximumSize(grid.getPreferredSize());
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);

        logOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOut.addActionListener(evt -> logoutController.execute());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(5));
        this.add(loggedInAs);
        this.add(Box.createVerticalStrut(15));
        this.add(grid);
        this.add(Box.createVerticalStrut(15));
        this.add(logOut);
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    /** What happens when Add Stamp is clicked (connected in AppBuilder). */
    public void setOnAddStamp(Runnable action) {
        addStamp.addActionListener(evt -> action.run());
    }

    /** What happens when World Map is clicked (connected in AppBuilder). */
    public void setOnWorldMap(Runnable action) {
        worldMap.addActionListener(evt -> action.run());
    }

    /** What happens when Discover is clicked (connected in AppBuilder). */
    public void setOnDiscover(Runnable action) {
        discover.addActionListener(evt -> action.run());
    }

    /** What happens when Find Restaurants is clicked (connected in AppBuilder). */
    public void setOnFindRestaurants(Runnable action) { findRestaurants.addActionListener(evt -> action.run());}

    /** What happens when Profile is clicked (connected in AppBuilder). */
    public void setOnProfile(Runnable action) {
        profile.addActionListener(evt -> action.run());
    }

    /**
     * The home state was updated.
     */
    @Override
    public void stateChanged() {
        final HomeState state = homeViewModel.getState();
        loggedInAs.setText("Logged in as: " + state.getUsername());
    }
}