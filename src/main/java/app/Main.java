package app;

<<<<<<< HEAD
public class Main {
=======
import javax.swing.*;
import view.DiscoverDishesView;
import interface_adapter.DiscoverDishesViewModel;

public class Main {
public static void main(String[] args) {
    final JFrame application = new JFrame("Discover Dishes");

    application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    DiscoverDishesView discoverDishesView = new DiscoverDishesView(new DiscoverDishesViewModel());
    application.setContentPane(discoverDishesView);
//    application.pack();
    application.setSize(900, 600);
    application.setVisible(true);
}
>>>>>>> bc320d4 (Establish the basic UI structure. Implementation of Controller, Presenter, etc. are omitted)
}
