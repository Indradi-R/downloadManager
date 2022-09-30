import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showMessageDialog;

public class downloadTab {
    private JTabbedPane downloadTab;
    JPanel mainPanel;
    private JTextField urlTF;
    private JTextField nameTF;
    private JButton downloadBtn;
    private JProgressBar loadingBar;
    private JTable historyTable;
    static String filePath;

    public downloadTab() {
        showHistory();

        System.out.println(filePath);
        downloadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadButton();
            }
        });
    }
    public void progressBar(){ //loading bar method
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                loadingBar.setValue(i + 10);

                // delay the thread
                Thread.sleep(1000);
                i += 20;
            }
        }
        catch (Exception e) {
        }
    }
    public void downloadButton(){
        InputStream inputStream = null;
        FileOutputStream fileOutput = null;
        if(!urlTF.getText().isEmpty() && !nameTF.getText().isEmpty()){
            try{
                URL remoteFile = new URL (urlTF.getText()); //new object to get url
                URLConnection fileStream = remoteFile.openConnection(); //url connect

                //calling I/O Stream
                fileOutput = new FileOutputStream(nameTF.getText()); //save completed download file name
                inputStream = fileStream.getInputStream(); //save completed file

                //prepare writing file
                int data;
                while ((data=inputStream.read())!=-1){
                    fileOutput.write(data);
                }
                //save history file to .txt
                try (BufferedWriter out = new BufferedWriter(new FileWriter("historyList.txt", true))) {
                    out.write(nameTF.getText()+"\n");
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                progressBar();
            } catch (MalformedURLException ex) {
                Logger.getLogger(downloadTab.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(downloadTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                showMessageDialog(null, "File Sucessfully Downloaded");
                urlTF.setText("");
                nameTF.setText("");
                try {
                    inputStream.close();
                    fileOutput.flush();
                    fileOutput.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            showMessageDialog(null, "Field Cannot Empty");
        }
    }
    public void showHistory(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("historyList.txt"));
            String[] columnNames = {"NAME"};
            DefaultTableModel model = (DefaultTableModel)historyTable.getModel();
            model.setColumnIdentifiers(columnNames);

            Object[] tableLines = br.lines().toArray();

            for(int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                model.addRow(dataRow);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}