package com.viggys.algorithms.part3.week3;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanCodeLength {

    private static NodeComparator COMPARATOR = new NodeComparator();
    private static PriorityQueue<Node> HEAP = new PriorityQueue<>(COMPARATOR);

    public static void main(String[] args) {

        String path = "src/main/resources/huffman.txt";
        readData(path);
        Node root = execute();
        int deepestLevel = getDeepestLevel(root, 0);
//        int deepestLevel = root.getHeight();
        System.out.println("Largest Code Length = " + deepestLevel);
        List<Node> queue = new LinkedList<>();
        queue.add(root);
        int shallowestLevel = getShallowestLevel(queue, 0);
        System.out.println("Smallest Code Length = " + shallowestLevel);
    }

    private static int getShallowestLevel(List<Node> queue, int level) {
        List<Node> childQueue = new LinkedList<>();
        while(!queue.isEmpty()) {
            Node node = queue.remove(0);
            if(node.getHeight() > 0) {
                childQueue.add(node.getLeftChild());
                childQueue.add(node.getRightChild());
            }
            else {
                return level;
            }
        }
        return getShallowestLevel(childQueue, ++level);
    }

    private static int getDeepestLevel(Node root, int level) {
        if (root.getHeight() > 0) {
            Node leftChild = root.getLeftChild();
            Node rightChild = root.getRightChild();
            if(leftChild.getHeight() > rightChild.getHeight()) {
                return getDeepestLevel(leftChild, ++level);
            }
            else {
                return getDeepestLevel(rightChild, ++level);
            }
        }
        else {
            return level;
        }
    }

    private static Node execute() {
        while (HEAP.size() > 1) {
            Node minNode1 = HEAP.poll();
            Node minNode2 = HEAP.poll();
            Node parent = new Node(minNode1.getWeight() + minNode2.getWeight());
            if(COMPARATOR.compare(minNode1, minNode2) > 0) {
                parent.setRightChild(minNode1);
                parent.setLeftChild(minNode2);
            }
            else {
                parent.setLeftChild(minNode1);
                parent.setRightChild(minNode2);
            }
            minNode1.setParent(parent);
            minNode2.setParent(parent);
            parent.setHeight(Math.max(minNode1.getHeight(), minNode2.getHeight()) + 1);
            HEAP.add(parent);
        }
        return HEAP.poll();
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                int symbols = Integer.parseInt(line);
                System.out.println("No. of symbols = " + symbols);
                line = reader.readLine();
                int count = 0;
                while (StringUtils.isNotEmpty(line)) {
                    count++;
                    long weight = Long.parseLong(line);
//                    System.out.println(count + " => " + weight);
                    Node node = new Node(weight);
                    HEAP.add(node);
                    line = reader.readLine();
                }
            }
            finally {
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
