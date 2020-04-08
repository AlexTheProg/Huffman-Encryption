package huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutput {
    private final FileOutputStream output;
    private int BinValue;
    private int count = 0;
    private final int True = 1; //the bits are all 0 except the last one

    public BitOutput(File file) throws IOException
    {
        output = new FileOutputStream(file);
    }

    public void writeBit(String bit) throws IOException
    {
        count++; //incrementeaza count pe bit
        BinValue = BinValue << 1; //left shift bit

        if(bit.equals("1")) //daca bit-ul e 1 salveaza in value ca 1
            BinValue = BinValue | True;

        if(count == 8) ///cand am ajuns la 8 biti scriem in strean
        {
            output.write(BinValue);
            count = 0;
        }
    }
}
