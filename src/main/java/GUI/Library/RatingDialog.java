package GUI.Library;

import GUI.Authentification.LoginDialog;
import GUI.MainFrame;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;


public class RatingDialog extends JDialog {

    MainFrame parentFrame = null;
    private JTextField isbn;
    private JTextField rating;
    private JLabel lbISBN;
    private JLabel lbRating;
    private JButton btnSubmit;
    private JButton btnCancel;
    private boolean succeeded;

    public String getIsbn() {
        return isbn.getText().trim();
    }

    public String getRating() {
        return rating.getText().trim();
    }

    public RatingDialog(final MainFrame parentFrame) {
        super(parentFrame, "Rate", true);
        //
        this.parentFrame = parentFrame;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbISBN = new JLabel("ISBN: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbISBN, cs);

        isbn = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(isbn, cs);

        lbRating = new JLabel("Rating: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbRating, cs);

        rating = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(rating, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnSubmit = new JButton("Submit");

        btnSubmit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (rate(getIsbn(), getRating())) {
                    JOptionPane.showMessageDialog(RatingDialog.this,
                            "Thanks for rating",
                            "Rate",
                            JOptionPane.INFORMATION_MESSAGE);

                    parentFrame.getClient().getMainFrame().setLoggedIn(true);
                    dispose();
                }
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnSubmit);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(parentFrame);
    }

    private boolean rate(String isbn, String rating) {
        PrintWriter out = null;
        BufferedReader in = null;

        out = parentFrame.getClient().getOut();
        out.println(5);
        out.println(isbn);
        out.println(rating);
        out.close();
        return true;
    }
}
