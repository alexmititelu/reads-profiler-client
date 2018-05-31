package GUI.Library;

import GUI.MainFrame;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DownloadDialog extends JDialog {
    MainFrame parentFrame = null;
    private JTextField isbn;
    private JLabel lbISBN;

    private JButton btnDownload;
    private JButton btnCancel;


    public String getIsbn() {
        return isbn.getText().trim();
    }


    public DownloadDialog(final MainFrame parentFrame) {
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

        panel.setBorder(new LineBorder(Color.GRAY));

        btnDownload = new JButton("Download");

        btnDownload.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (downloadBook(getIsbn())) {
                    JOptionPane.showMessageDialog(DownloadDialog.this,
                            "Thanks for downloading",
                            "Download",
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
        bp.add(btnDownload);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(parentFrame);
    }

    private boolean downloadBook(String isbn) {
        PrintWriter out = null;
        BufferedReader in = null;

        out = parentFrame.getClient().getOut();
        out.println(3);
        out.println(isbn);
        out.close();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("book.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        String line = null;
        Chunk lineForPdf = null;

        while(true) {
            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(line.contentEquals("-1")) {
                break;
            }
            lineForPdf = new Chunk(line,font);
            try {
                document.add(lineForPdf);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        document.close();

        return true;
    }
}
