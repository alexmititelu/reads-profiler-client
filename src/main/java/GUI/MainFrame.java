package GUI;


import Client.AppClient;
import GUI.Authentification.CreateAccount;
import GUI.Authentification.LoginDialog;
import GUI.Library.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MainFrame extends JFrame {

    private Library library = null;

    private JButton loginButton = null;
    private JButton createAccountButton = null;
    private String username = null;
    private JButton loggedInUser = null;

    private JButton logoutButton = null;

    private AppClient client = null;

    private boolean loggedIn = false;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public AppClient getClient() {
        return client;
    }

    public MainFrame(AppClient newClient) {

        super("Visual GUI.Reads Profiler");
        client = newClient;
        init();
    }


    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200, 200);
        setLayout(new GridLayout(2, 1));
        if (!loggedIn) {
            showLoginMenu();
            showCreateAccountMenu();
        }
        setVisible(true);
    }


    private void showLoginMenu() {
        loginButton = new JButton("Logare");
        loginButton.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Showing input for username and password.");
                LoginDialog loginDialog = new LoginDialog(client.getMainFrame());
                if (loggedIn) {
                    showAfterLoginMenu();
                }
            }
        });
        this.add(loginButton);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private void showAfterLoginMenu() {
        System.out.println("You have logged in..");
        this.remove(loginButton);
        this.remove(createAccountButton);

        loggedInUser = new JButton(username + "s Library");
        loggedInUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                library = new Library(client);
            }
        });
        loggedInUser.setVisible(true);
        this.add(loggedInUser);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(loggedInUser);
                remove(logoutButton);
                loggedIn = false;
                revalidate();
                init();
                revalidate();
            }
        });
        logoutButton.setVisible(true);
        this.add(logoutButton);
//        repaint();
        revalidate();

    }

    private void showCreateAccountMenu() {
        createAccountButton = new JButton("Create Account");
        createAccountButton.setVisible(true);
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Showing input for creating a user");
                CreateAccount createAccountDialog = new CreateAccount(client.getMainFrame());
            }
        });
        this.add(createAccountButton);
    }


}
