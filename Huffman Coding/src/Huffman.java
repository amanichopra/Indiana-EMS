import java.io.*;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {
    LinkedHashMap<Character, Integer> charFreqs;
    Hashtable<Character, String> codes;
    HuffmanNode tree;


    /**
     * Initialize global variables you create
     */
    public Huffman() {
        //TODO
        charFreqs = new LinkedHashMap<>();
        codes = new Hashtable<>();
        tree = new HuffmanNode();
    }



    /**
     * Produces the output frequency.txt
     *
     * @param input - File containing the message
     * @throws Exception
     */
    public void frequency(String input) throws Exception { 
        //TODO
        BufferedReader br = new BufferedReader(new FileReader(input));

        int c = br.read();
        while (c != -1) {

            char cChar = (char)c;
            if (cChar == ' ') {
                cChar = 'S';
            }

            if (cChar == '\n') {
                cChar = 'N';
            }

            Integer freq = charFreqs.get(cChar);
            if (freq == null) {
                charFreqs.put(cChar, 1);
            } else {
                charFreqs.put(cChar, ++freq);
            }

            c = br.read();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("frequency.txt"));

        for (Character cChar : charFreqs.keySet()) {
            bw.write(cChar + " " + charFreqs.get(cChar) + "\n");
        }

        bw.close();
    }

    
    

    /**
     * Produces the output codes.txt and tree.txt
     *
     * @param freqFile - File containing the frequencies
     * @throws Exception
     */
    public void buildTree(String freqFile) throws Exception {
        /** use the queue below **/
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        //TODO
        charFreqs = new LinkedHashMap<>();
        Scanner fileReader = new Scanner(new File(freqFile));
        while (fileReader.hasNext()) {
            charFreqs.put(fileReader.next().charAt(0), fileReader.nextInt());
        }

        int index = 0;
        for (Character c : charFreqs.keySet()) {
            int freq = charFreqs.get(c);
            HuffmanNode node = new HuffmanNode();
            node.frequency = freq;
            node.letter = c;
            node.index = index++;
            queue.add(node);
        }

        while (queue.size() > 1) {
            HuffmanNode min1 = queue.poll();
            HuffmanNode min2 = queue.poll();
            HuffmanNode combNode = new HuffmanNode();
            combNode.frequency = min1.frequency + min2.frequency;
            combNode.left = min1;
            combNode.left.prev = combNode;
            combNode.right = min2;
            combNode.right.prev = combNode;
            combNode.letter = Character.MIN_VALUE;
            queue.add(combNode);
        }

        HuffmanNode tree = queue.poll();

        BufferedWriter bw = new BufferedWriter(new FileWriter("tree.txt"));
        preOrderTraverseWriteString(tree, bw);
        bw.close();

        String s = "";
        bw = new BufferedWriter(new FileWriter("codes.txt"));
        preOrderTraverseCode(tree, s, bw);
        bw.close();


    }

    public void preOrderTraverseWriteString(HuffmanNode tree, BufferedWriter bw) throws Exception {
        if (tree.left == null || tree.right == null) {
            return;
        }


        if (tree.left.letter == Character.MIN_VALUE) {
           bw.write(Integer.toString(tree.left.frequency));
        } else {
            bw.write(tree.left.letter);
        }

        bw.write('-');

        if (tree.letter == Character.MIN_VALUE) {
            bw.write(Integer.toString(tree.frequency));
        } else {
            bw.write(tree.letter);
        }

        bw.write('-');

        if (tree.right.letter == Character.MIN_VALUE) {
            bw.write(Integer.toString(tree.right.frequency));
        } else {
            bw.write(tree.right.letter);
        }

        bw.newLine();


        preOrderTraverseWriteString(tree.left, bw);
        preOrderTraverseWriteString(tree.right, bw);
    }

    public void preOrderTraverseCode(HuffmanNode tree, String s, BufferedWriter bw) throws Exception {
        if (tree == null) {
            return;
        }

        if (tree.left == null && tree.right == null) {
            bw.write(Character.toString(tree.letter) + " " + s);
            bw.newLine();
        }
        preOrderTraverseCode(tree.left,s + "0", bw);
        preOrderTraverseCode(tree.right,s + "1", bw);
    }





    /**
     * Produces the output encode.bin
     *
     * @param code - File containing the bit codes
     * @param message -  File containing the message
     * @throws Exception
     */
    public void encode(String code, String message) throws Exception { 
        //TODO
        try (BufferedReader br = new BufferedReader(new FileReader(code))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                String[] lineContents = line.split(" ");
                codes.put(lineContents[0].charAt(0), lineContents[1]);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(message)); BufferedWriter bw =
                new BufferedWriter(new FileWriter("encode.bin"))) {
            int c = br.read();

            while (c != -1) {
                if ((char)c == ' ') {
                    bw.write(codes.get('S'));
                } else if ((char)c == '\n') {
                    bw.write(codes.get('N'));
                } else {
                    bw.write(codes.get((char)c));
                }
                c = br.read();
            }
            bw.newLine();
        }



    }



    /**
     * Produces the output decode.txt
     *
     * @param tree - File containing the Huffman tree
     * @param encode - - File containing the encoded message
     * @throws Exception
     */
    public void decode(String tree, String encode) throws Exception { 
        //TODO
        BufferedReader br = new BufferedReader(new FileReader(tree));
        HuffmanNode treeStructure = buildTree(br);

        br = new BufferedReader(new FileReader(encode));
        int c = br.read();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("decode.txt"))) {
            HuffmanNode iterTree = treeStructure;

            while (c != -1) {
                if ((char)c == '0') {
                    if (iterTree.left.letter == Character.MIN_VALUE) {
                        iterTree = iterTree.left;
                    } else {
                        if (iterTree.left.letter == 'S') {
                            bw.write(' ');
                        } else if (iterTree.left.letter == 'N') {
                            bw.write('\n');
                        } else {
                            bw.write(iterTree.left.letter);
                        }
                        iterTree = treeStructure;
                    }
                } else {
                    if (iterTree.right.letter == Character.MIN_VALUE) {
                        iterTree = iterTree.right;
                    } else {
                        if (iterTree.right.letter == 'S') {
                            bw.write(' ');
                        } else if (iterTree.right.letter == 'N') {
                            bw.write('\n');
                        } else {
                            bw.write(iterTree.right.letter);
                        }
                        iterTree = treeStructure;
                    }
                }
                c = br.read();
            }

            bw.newLine();
        }



    }

    public HuffmanNode buildTree(BufferedReader br) throws Exception {
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            String[] lineContents = line.split("-");
            HuffmanNode root = new HuffmanNode();
            root.frequency = Integer.parseInt(lineContents[1]);
            HuffmanNode leftChild = new HuffmanNode();
            HuffmanNode rightChild = new HuffmanNode();

            try {
                leftChild.frequency = Integer.parseInt(lineContents[0]);

            } catch (NumberFormatException e) {
                leftChild.letter = lineContents[0].charAt(0);
                leftChild.frequency = Integer.MIN_VALUE;
            }

            try {
                rightChild.frequency = Integer.parseInt(lineContents[2]);
            } catch (NumberFormatException e) {
                rightChild.letter = lineContents[2].charAt(0);
                rightChild.frequency = Integer.MIN_VALUE;
            }

            root.left = leftChild;
            root.right = rightChild;

            nodes.add(root);
        }

        while (nodes.size() > 1) {
            HuffmanNode min = nodes.poll();
            for (Object n : nodes.toArray()) {
                HuffmanNode node = (HuffmanNode)n;

                if (node.left.frequency == min.frequency) {
                    nodes.remove(node);
                    node.left = min;
                    nodes.add(node);
                    break;
                }

                if (node.right.frequency == min.frequency) {
                    nodes.remove(node);
                    node.right = min;
                    nodes.add(node);
                    break;
                }

            }
        }

        return nodes.poll();
    }



    /**
     * Auxiliary class for Huffman
     *
     */
    class HuffmanNode implements Comparable<HuffmanNode> {
        int frequency;
        int index;
        char letter;
        HuffmanNode left;
        HuffmanNode right;
        HuffmanNode prev;
        

        /**
         * Uses frequency to determine the nodes order in the queue
         * Note: DO NOT MODIFY THIS FUNCTION
         *
         * @param node of type HuffmanNode
         * @return frequency of key node subtracted by frequency of node from parameter
         */
        @Override
        public int compareTo(HuffmanNode node) {
            return frequency - node.frequency;
        }

        @Override
        public boolean equals(Object obj) {
            HuffmanNode node = (HuffmanNode)obj;
            return frequency == node.frequency;
        }
    }

//    public static void main(String[] args) throws Exception {
//        Huffman huffman = new Huffman();
//        String messageFile = "/Users/Aman/IdeaProjects/CS 251/Project 5/LocalTests/inputFiles/testMessage1.txt";
//        huffman.frequency(messageFile);
//        huffman.buildTree("frequency.txt");
//        huffman.encode("codes.txt", messageFile);
//        huffman.decode("tree.txt", "encode.bin");
//    }


}