package GUI.Library;

import Client.AppClient;

import javax.swing.*;
import java.awt.*;

public class Library extends JFrame {
    private AppClient client = null;

    public Library(AppClient client){
        super("Reads Profiler - Library");
        this.client = client;
        init();
    }

    private void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(200,0,600,600);
        setLayout(new GridLayout(2, 1));
        setVisible(true);
    }
}
