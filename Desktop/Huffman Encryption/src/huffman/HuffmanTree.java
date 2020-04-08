package huffman;

public class HuffmanTree implements Comparable<HuffmanTree> {
    HuffmanNode newNode;

    public HuffmanTree(){}

    public HuffmanTree(char character, int frequency)
    {
        newNode = new HuffmanNode(character, frequency);
    }

    public HuffmanTree(HuffmanTree ht1, HuffmanTree ht2)
    {
        newNode = new HuffmanNode();
        newNode.leftChild = ht1.newNode; //left child
        newNode.rightChild = ht2.newNode; //right child
        newNode.frequency = ht1.newNode.frequency + ht2.newNode.frequency; //suma frecventelor nodurilor child
    }

    public HuffmanTree(char character, String code)
    {
        HuffmanNode root = new HuffmanNode(character, code);
    }

    @Override
    public int compareTo(HuffmanTree obj)
    {
        if(newNode.frequency < obj.newNode.frequency)
            return 1;
        else if(newNode.frequency == obj.newNode.frequency)
            return 0;
        else
            return -1;
    }

}
