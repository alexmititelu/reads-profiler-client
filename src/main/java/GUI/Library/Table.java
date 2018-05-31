package GUI.Library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Table extends JTable {
    private String[] columnNames = {"Gen","Autor","Titlu","An aparitie","ISBN","Rating"};

    DefaultTableModel data = new DefaultTableModel(columnNames,6);


    private PrintWriter out;
    private BufferedReader in;

    public Table(PrintWriter socketOut, BufferedReader socketIn) {
        out = socketOut;
        in = socketIn;
        init();
    }

    private void init() {
        List<String> row = new ArrayList<String>();
        out.println(6);
        String gen;
        String autor;
        String titlu;
        String an;
        String isbn;
        String rating;
        try {
            while (true) {
                gen = in.readLine();
                if (gen.contains("-1")) {
                    break;
                }
                autor = in.readLine();
                titlu = in.readLine();
                an = in.readLine();
                isbn = in.readLine();
                rating = in.readLine();
                row.add(gen);
                row.add(autor);
                row.add(titlu);
                row.add(an);
                row.add(isbn);
                row.add(rating);

                data.addRow(row.toArray());

                row.clear();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.setModel(data);

    }


}
