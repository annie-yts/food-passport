package app;

import javax.swing.*;

import data_access.TheMealDBClient;
import view.DiscoverDishesView;
import interface_adapter.DiscoverDishesViewModel;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import interface_adapter.DataAccessBoundary;

public class Main {
    public static void main(String[] args) {
        final JFrame application = new JFrame("Discover Dishes");

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DiscoverDishesView discoverDishesView = new DiscoverDishesView(
                new DiscoverDishesViewModel(),
                new TheMealDBClient()
                );
        application.setContentPane(discoverDishesView);
    //    application.pack();
        application.setSize(900, 600);
        application.setVisible(true);
    }
}
