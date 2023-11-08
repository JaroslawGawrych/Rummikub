package a1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Model extends AbstractListModel {
    private ArrayList<String> data;

    public Model() {
        data = new ArrayList<>();
    }

    public int getSize() {
        return data.size();
    }

    public Object getElementAt(int index) {
        return data.get(index);
    }

    public void addPlayer(Gracz g) {
        int index = data.size();
        data.add(g.toString());
        fireIntervalAdded(this, index, index);
        data.sort(String::compareTo);
        fireContentsChanged(this, 0, data.size());
    }

    public void sortWyniki(){
        data.sort(String::compareTo);
        Collections.reverse(data);
        fireContentsChanged(this, 0, data.size());
    }

    public void setData(int i, String s) {
        data.set(i,s);
    }
}
