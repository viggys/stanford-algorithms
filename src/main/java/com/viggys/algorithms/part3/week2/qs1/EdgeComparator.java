package com.viggys.algorithms.part3.week2.qs1;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        return Integer.compare(e1.getEdgeCost(), e2.getEdgeCost());
    }

}
