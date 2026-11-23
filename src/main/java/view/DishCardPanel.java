package view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DishCardPanel extends JPanel {


    public DishCardPanel (String dishName, String country, String imageUrl, int distToLeft, int distToTop) {
        final JLabel title = new JLabel(dishName);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setBounds(15, 0, 255, 50);

        JLabel imageLabel = new JLabel();
        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));
            Image scaledImage = image.getScaledInstance(
                    150, 130, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            imageLabel.setText("Image unavailable");
        }
        imageLabel.setBounds(10, 40, 150,130);

        JLabel countryLabel = new JLabel(country);
        countryLabel.setBounds(175, 60, 75, 20);

        JButton details = new JButton("Details");
        details.setBounds(175, 90, 75, 20);
        JButton addToBucketList = new JButton("<html>&nbsp;Want<br>to Try<html>");
        addToBucketList.setBounds(170, 120, 85, 40);


        this.setLayout(null);
        this.setBounds(distToLeft, distToTop, 270, 180);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.add(title);
        this.add(imageLabel);
        this.add(details);
        this.add(addToBucketList);
        this.add(countryLabel);
    }

    public DishCardPanel (String dishName, String country, int distToLeft, int distToTop) {
        final JLabel title = new JLabel(dishName);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setBounds(15, 0, 255, 50);

        JLabel imageLabel = new JLabel();
        imageLabel.setText("No Image");
        imageLabel.setBounds(10, 40, 150,130);

        JLabel countryLabel = new JLabel(country);
        countryLabel.setBounds(175, 60, 75, 20);

        JButton details = new JButton("Details");
        details.setBounds(175, 90, 75, 20);
        JButton addToBucketList = new JButton("<html>&nbsp;Want<br>to Try<html>");
        addToBucketList.setBounds(170, 120, 85, 40);


        this.setLayout(null);
        this.setBounds(distToLeft, distToTop, 270, 180);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.add(title);
        this.add(imageLabel);
        this.add(details);
        this.add(addToBucketList);
        this.add(countryLabel);
    }

}
