package GUI.Authentification;

import GUI.MainFrame;
import com.google.common.hash.Hashing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CreateAccount extends JDialog {
    MainFrame parentFrame = null;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;

    public CreateAccount(MainFrame parentFrame) {
        super(parentFrame, "Create", true);

        this.parentFrame = parentFrame;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Create");

        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (createNewAccount(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(CreateAccount.this,
                            "Hi " + getUsername() + "! You have successfully created your account.",
                            "Create Account",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CreateAccount.this,
                            "Invalid username or password",
                            "Create Account",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;

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
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(parentFrame);
    }

    private boolean createNewAccount(String username, String password) {
        PrintWriter out = null;
        BufferedReader in = null;

        out = parentFrame.getClient().getOut();
        out.println(2);
        out.flush();
        out.println(username);
        String hashedPass = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        out.println(hashedPass);
        out.flush();

         in = parentFrame.getClient().getIn();
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            dispose();
        }
        if(response.contentEquals("0")) {
            return false;
        } else {
            return true;
        }

    }


    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
