package interface_adapter;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

public interface ViewManagerModelListener {

    /**
     * Update which view is currently being displayed.
     */
    void viewChanged();
}