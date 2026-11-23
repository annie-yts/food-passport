package interface_adapter;

import java.util.ArrayList;
import java.util.List;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel {

    private final List<ViewManagerModelListener> listeners = new ArrayList<>();

    private String currentViewName = "";

    public String getCurrentViewName() {
        return this.currentViewName;
    }

    public void setState(String currentViewName) {
        this.currentViewName = currentViewName;
    }

    public void addListener(ViewManagerModelListener listener) {
        listeners.add(listener);
    }

    public void notifyViewChanged() {
        for (ViewManagerModelListener listener : listeners) {
            listener.viewChanged();
        }
    }

}