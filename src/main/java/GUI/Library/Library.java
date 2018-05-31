package GUI.Library;

import Client.AppClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Library extends JFrame {
    private AppClient client = null;
    private Table table = null;
    private JButton ratingButton = null;
    private JButton downloadButton = null;

    public Library(AppClient client){
        super("Reads Profiler - Library");
        this.client = client;
        init();
    }

    private void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(200,0,600,600);
        setLayout(new GridLayout(3, 1));
        setVisible(true);
        createTable();
        createRatingOption();
        createDownloadOption();
    }

    private void createTable() {
        table = new Table(client.getOut(),client.getIn());
        JScrollPane scrollableTable = new JScrollPane(table);
        this.add(scrollableTable);
        this.setVisible(true);
    }

    private void createRatingOption() {
        ratingButton = new JButton("Rate a book");
        ratingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RatingDialog ratingDialog = new RatingDialog(client.getMainFrame());
            }
        });
        this.add(ratingButton);
    }

    private void createDownloadOption() {
        downloadButton = new JButton("Download a book");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DownloadDialog downloadDialog = new DownloadDialog(client.getMainFrame());
            }
        });
        this.add(downloadButton);
    }
}
