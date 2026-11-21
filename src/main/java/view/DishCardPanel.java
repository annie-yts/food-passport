package view;

import javax.swing.*;
import java.awt.*;

public class DishCardPanel extends JPanel {
    private String dishName;
    private String imageUrl;

    public DishCardPanel (String dishName, String imageUrl, int distToLeft, int distToTop) {
        this.dishName = dishName;
        this.imageUrl = imageUrl;
        JButton details = new JButton("Details");
        JButton addToBucketList = new JButton("Add to my bucket list");
        this.setLayout(null);
        this.setBounds(distToLeft, distToTop, 270, 180);
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }
}
