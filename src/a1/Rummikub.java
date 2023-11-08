package a1;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Rummikub {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Rummikub");
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Model model = new Model();

        String[] opt = {"yes", "no", "cancel"};
        int chosen = JOptionPane.showOptionDialog(null,"load?","Rummikub",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,opt,opt[1]);
        ArrayList<Gracz> gracze = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        if(chosen == 1){
            for (int i = 0; i < 6; i++) {
                String s = JOptionPane.showInputDialog(null,"gracz " + (i+1) + ":", "Rummikub",JOptionPane.QUESTION_MESSAGE);
                if(s!=null){
                    gracze.add(new Gracz(s));
                }
            }
        }else if(chosen == 0){
            try {
                chooser.showOpenDialog(frame);
                FileReader fr = new FileReader(chooser.getSelectedFile().getAbsolutePath());
                BufferedReader br = new BufferedReader(fr);
                String s = br.readLine();
                while (s!=null) {

                    String [] ss = s.split("\t");
                    gracze.add(new Gracz(ss[1],Integer.valueOf(ss[0])));
                    s = br.readLine();
                }
                model.sortWyniki();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.exit(0);
        }

        int len = gracze.size();

        JList<String> wyniki = new JList<>(model);
        for (Gracz g:
                gracze) {
            model.addPlayer(g);
        }
        JScrollPane scroll = new JScrollPane(wyniki);

        JPanel panel0 = new JPanel(new GridLayout(2,1));

        JPanel panel00 = new JPanel(new GridLayout(2, len));
        for (int i = 0; i < len; i++) {
            panel00.add(new JLabel(gracze.get(i).getImie() + " "));
        }
        JTextField[] fields = new JTextField[len];
        for (int i = 0; i < len; i++) {
            fields[i] = new JTextField("0");
            panel00.add(fields[i]);
        }

        JPanel panel01 = new JPanel(new GridLayout(1,1));
        JButton oblicz = new JButton("Oblicz");
        oblicz.addActionListener(e -> {
            try {
                int sum = 0;
                for (JTextField f :
                        fields) {

                    sum+=Integer.valueOf(f.getText());
                }
                for (int i = 0; i < gracze.size(); i++) {
                    if(Integer.valueOf(fields[i].getText())!=0){
                        gracze.get(i).setPunkty(gracze.get(i).getPunkty()-Integer.valueOf(fields[i].getText()));
                    }else{
                        gracze.get(i).setPunkty(gracze.get(i).getPunkty()+sum);
                    }
                }
                ArrayList<String> newData = new ArrayList<>();
                int index = 0;
                for (Gracz g :
                        gracze) {
                    model.setData(index++, g.toString());
                }
                model.sortWyniki();
                for (JTextField f :
                        fields) {
                    f.setText("");
                }
            }catch(NumberFormatException ex){

            }
        });
        panel01.add(oblicz);

        panel0.add(panel00);
        panel0.add(panel01);

        frame.add(scroll,BorderLayout.CENTER);
        frame.add(panel0,BorderLayout.PAGE_END);

        JMenuBar bar = new JMenuBar();
        JMenuItem save = new JMenuItem("Zapisz");
        save.addActionListener(e->
        {
            try {
                chooser.showOpenDialog(frame);
                FileWriter wr = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                BufferedWriter bw = new BufferedWriter(wr);
                for (int i = 0; i < model.getSize(); i++) {
                    bw.write(model.getElementAt(i)+"\n");
                }
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        bar.add(save);
        frame.setJMenuBar(bar);

        frame.pack();
    }
}
