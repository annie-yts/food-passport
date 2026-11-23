package view;

import interface_adapter.logout.LogoutController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.profile.ProfileViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Adapted from the CSC207 ca-login example (LoggedInView):
 * https://github.com/annie-yts/ca-login
 * Changes: renamed to ProfileView; removed the change-password controls
 * (they move to the Account Settings screen); kept the username and Log Out button.
 */

/**
 * The View for the user's profile, shown after logging in.
 */
public class ProfileView extends JPanel implements ActionListener, ProfileViewModelListener {

    private final ProfileViewModel profileViewModel;
    private LogoutController logoutController;

    private final JLabel username;
    private final JButton logOut;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        this.profileViewModel.addListener(this);

        final JLabel title = new JLabel("Profile");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        logOut.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(logOut)) {
            logoutController.execute();
        }
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    /**
     * The profile state was updated.
     */
    @Override
    public void stateChanged() {
        final ProfileState state = profileViewModel.getState();
        username.setText(state.getUsername());
    }

    /**
     * A password change attempt occurred.
     * Not used on this screen yet: change password will live on the Account Settings screen.
     */
    @Override
    public void passwordChanged() {
    }
}