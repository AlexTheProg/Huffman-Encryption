package huffman;

import java.util.ArrayList;

public class PriorityQueue<E extends Comparable>{
    private ArrayList<E> list = new ArrayList<E>();

    public PriorityQueue(){}

    public void addItem(E item)
    {
        list.add(item);
        int pos = list.size() - 1; //index item inserat

        while(pos > 0 && list.get(pos).compareTo(list.get(pos-1)) > 0){
            E temp = list.get(pos - 1);
            list.set(pos - 1, list.get(pos));
            list.set(pos, temp);
        }
    }

    public E removeItem(){
        if(list.size() == 0)
            return null;

        E removedItem = list.get(0); // get primul item
        list.remove(0); //remove primul item
        int i = 0;

        while(i < list.size()){   //shift toate elem o pozitie inapoi
            list.set(i, list.get(i++));
            i++;
        }
        return removedItem;
    }

    public int getListSize(){
        return list.size();
    }
}
