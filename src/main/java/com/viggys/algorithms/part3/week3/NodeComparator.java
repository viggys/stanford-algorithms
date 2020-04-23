package com.viggys.algorithms.part3.week3;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        return Long.compare(n1.getWeight(), n2.getWeight());
    }
}
