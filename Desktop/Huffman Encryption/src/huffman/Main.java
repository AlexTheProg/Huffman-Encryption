package huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchElementException {
        char ch = 0;
        HuffmanCode hc = new HuffmanCode();
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("Select options: ");
            System.out.println("1. Compress File");
            System.out.println("2. De-compress File");
            System.out.println("4. Exit");

            int choice = input.nextInt();
            switch(choice)
            {
                case 1:
                    hc.compressFile();
                    break;

                case 2:
                    hc.decodeFile();
                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("[-] ERROR: Wrong Entry \n");
                    break;
            }
            System.out.println("\nDo you want to continue? (Y/N)");
            ch = input.next().charAt(0);
        }while(ch == 'Y' || ch =='y');

    }
}
