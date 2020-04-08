package huffman;

public class HuffmanNode extends HuffmanTree {
    char character;
    int frequency;
    HuffmanNode leftChild;
    HuffmanNode rightChild;
    String code = "";

    public HuffmanNode(){}

    public HuffmanNode(char character, int frequency)
    {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(char character, String code)
    {
        this.character = character;
        this.code = code;
    }
}
