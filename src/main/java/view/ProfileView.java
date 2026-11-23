package view;

import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.profile.ProfileViewModelListener;

import javax.swing.*;
import java.awt.*;

/*
 * Adapted from the CSC207 ca-login example (LoggedInView):
 * https://github.com/annie-yts/ca-login
 * Changes: renamed to ProfileView; removed the change-password controls
 * (they move to the Account Settings screen); Log Out moved to the Home screen;
 * added a Back button to Home.
 */

/**
 * The Profile screen, reached from the Home screen. Stats, Export and
 * Account Settings will be added here.
 */
public class ProfileView extends JPanel implements ProfileViewModelListener {

    private final ProfileViewModel profileViewModel;

    private final JLabel username = new JLabel();
    private final JButton back = new JButton("Back");

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        this.profileViewModel.addListener(this);

        final JLabel title = new JLabel("Profile");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(5));
        this.add(username);
        this.add(Box.createVerticalStrut(15));
        this.add(back);
    }

    /** What happens when Back is clicked (connected in AppBuilder). */
    public void setOnBack(Runnable action) {
        back.addActionListener(evt -> action.run());
    }

    /**
     * The profile state was updated.
     */
    @Override
    public void stateChanged() {
        final ProfileState state = profileViewModel.getState();
        username.setText("Username: " + state.getUsername());
    }

    /**
     * A password change attempt occurred.
     * Not used on this screen yet: change password will live on the Account Settings screen.
     */
    @Override
    public void passwordChanged() {
    }
}