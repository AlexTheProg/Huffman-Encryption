package huffman;

import java.io.*;
import java.lang.String;
import java.util.Scanner;

public class HuffmanCode {
    public HuffmanCode(){}

    public String[] getCodes(HuffmanNode node){
        if(node == null)
            return null; //daca nu e tree return null

        String[] codes = new String[256];
        assignCodes(node, codes);
        return codes;

    }

    public void assignCodes(HuffmanNode node, String[] codes)
    {
        if(node == null)
            return;
        if(node.leftChild != null)
        {
            node.leftChild.code = node.code + "0"; //left child code to 0
            assignCodes(node.leftChild, codes); // call recursiv

            node.rightChild.code = node.code + "1"; //right child to 1
            assignCodes(node.rightChild, codes); /// call recursiv
        }
        else{
            codes[(int)node.character] = node.code;
        }
    }



    public HuffmanTree getHuffmanTree(int[] counts){
        //se creeaza priority queue
        PriorityQueue<HuffmanTree> pq = new PriorityQueue<HuffmanTree>();
        for(int i=0; i<counts.length; i++)
        {
            if(counts[i] > 0)
                pq.addItem(new HuffmanTree((char)i, counts[i])); //daca frecventa e mai mare de 0 adaugam in queue
        }
        while(pq.getListSize() > 1)
        {
            HuffmanTree ht1 = pq.removeItem(); //scoate primul element
            HuffmanTree ht2 = pq.removeItem(); ///scoate al doilea element
            pq.addItem(new HuffmanTree(ht1, ht2)); // seteaza primul element ca left child si al doilea ca right child si adauga nodul in queue
        }
        return pq.removeItem(); //return adresa root
    }

    public int[] getCharFrequency(String fileName) throws FileNotFoundException, IOException
    {
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(new File(fileName))); //deschidere fisier
        int[] counts = new int[256];
        int read;

        while((read = fileInput.read()) != -1)
        {
            counts[read]++; //daca char apare creste valoarea la pozitia corespunzatoare
        }
        fileInput.close();
        return counts;
    }

    public void generateHuffmanTable(String fileName) throws FileNotFoundException, IOException{
        int[] counts = getCharFrequency(fileName);
        System.out.printf("%-15s%-15s%-15s%-15s%\n", "ASCII Code", "Character", "Frequency", "Code");
        HuffmanTree t = getHuffmanTree(counts); //build tree
        String[] codes = getCodes(t.newNode); //get codes array
        for(int i=0; i<codes.length; i++){
            if(counts[i] != 0)
                System.out.printf("%-15d%-15s%-15d%-15s\n",i,(char)i+"",counts[i],codes[i]);
        }
    }

    public void compressFile() throws FileNotFoundException, IOException {
        System.out.println("[*] Enter file name: ");
        Scanner input = new Scanner(System.in);
        String file = input.next();
        System.out.println("[+] Generating codes ");
        generateHuffmanTable(file); //print huff table to screen

        System.out.println("[+] Compressing file");
        int[] codes = getCharFrequency(file); //freq of chars
        HuffmanTree ht = getHuffmanTree(codes); //build huff tree
        String[] assignCodes = getCodes((ht.newNode)); //assign codes to nodes of tree
        System.out.println("[!] Please wait....");
        encode(file, assignCodes); //encode the file

        System.out.println("---Compression Done---");
        PrintWriter output = new PrintWriter(new File("Table.fr")); //scrie table de fr in fisier
        for(int i=0; i<codes.length; i++)
        {
            output.write(String.valueOf(codes[i]));
            output.println();
        }
        output.flush();
        output.close();
    }

    public void encode(String filename, String[] codes) throws IOException{
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(new File(filename))); //open input file
        BitOutput output = new BitOutput(new File(filename + ".cmp"));
        int r;
        while((r = fileInput.read()) != -1){
            output.writeBit(codes[r]);
        }
    }

    public void decodeFile() throws FileNotFoundException, IOException{
        System.out.print("[*] Enter the file name:");
        Scanner scan=new Scanner(System.in);
        String compFile=scan.next();        //get compressed file name/path
        System.out.println("[+] Opening Frequency Table: Table.fr");
        BufferedReader br=new BufferedReader(new FileReader("Table.fr"));// open frequency table
        String line;
        String[] fr=new String[256];
        int counter=0;
        while((line=br.readLine())!=null){//read frequency table file till end and save contents in string array
            if(!line.matches("null")){
                fr[counter]=line;
            }
            counter++;
        }
        int [] codes=new int[256];
        for(int j=0;j<256;j++){// convert string array to integer array
            codes[j]=Integer.parseInt(fr[j]);
        }
        System.out.println("[+] Constructing Huffman Tree......");
        HuffmanTree ht=getHuffmanTree(codes);// build huffmanTree
        System.out.println("[+] Setting code paths.....");
        String[] Codes=getCodes(ht.newNode);// assign codes to nodes of tree
        System.out.println("[+] Opening Encoded file");
        BitInput bitInput=new BitInput(new File(compFile));
        String test="";
        System.out.println("[*] Decoding");
        System.out.println("[!] Please Wait......");
        try{
            while(true){// call the getLeaves() method till the end of compressed file
                test=test+getLeaves(ht.newNode,bitInput);
            }
        }catch(EOFException e){}
        System.out.println("----DONE-----\n Decoded:");
        PrintWriter output=new PrintWriter(new File("Decoded.txt"));//open new file for restoring
        output.write(test);// write decoded text to the file
        output.close();
        System.out.println(test);// print decoded text to the screen


    }

    public String getLeaves(HuffmanNode root, BitInput bitInput) throws IOException{
        String text="";
        if(root==null)//base condition 1
            return null;
        else if(root.leftChild==null && root.rightChild==null)//base condition 2
            text=text+root.character;//save char in string
        else{
            if(bitInput.readBit())//read 1 bit from file if it is 1
                text=text+getLeaves(root.rightChild,bitInput);//if bit is 1 goto right
            else//if bit is 0
                text=text+getLeaves(root.leftChild,bitInput);//if bit is 0 goto left
        }
        return text;
    }

}
