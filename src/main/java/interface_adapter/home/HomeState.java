package interface_adapter.home;

/**
 * The state of the Home screen: who is logged in.
 */
public class HomeState {
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}