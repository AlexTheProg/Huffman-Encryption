package huffman;

import java.io.*;

public class BitInput {
    private FileInputStream input;
    private int currentBit = 8;
    private int currentByte;

    public BitInput(File file) throws FileNotFoundException
    {
        input = new FileInputStream(file);
    }

    public boolean readBit() throws IOException
    {
        if(currentBit == 8){
            currentBit = 0;
            currentByte = input.read(); //read byte from file

            if(currentByte == -1)
                throw new EOFException();
        }

        boolean value = (currentByte &(1 << (7 - currentBit))) != 0;
        currentBit++;
        return value;
    }


}

