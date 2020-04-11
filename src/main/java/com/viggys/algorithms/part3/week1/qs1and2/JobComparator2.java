package com.viggys.algorithms.part3.week1.qs1and2;

import java.util.Comparator;

public class JobComparator2 implements Comparator<Job> {

    @Override
    public int compare(Job j1, Job j2) {

        double factor1 = ((double) j1.getWeight()) / ((double) j1.getLength());
        double factor2 = ((double) j2.getWeight()) / ((double) j2.getLength());

        return ((Double) factor1).compareTo((Double) factor2);
    }
}
