package com.viggys.algorithms.part3.week1.qs1and2;

import java.util.Comparator;

public class JobComparator1 implements Comparator<Job> {

    @Override
    public int compare(Job j1, Job j2) {

        int factor1 = j1.getWeight() - j1.getLength();
        int factor2 = j2.getWeight() - j2.getLength();
        if(factor1 < factor2)
            return -1;
        else if(factor1 > factor2)
            return 1;
        else
            return ((Integer) j1.getWeight()).compareTo((Integer) j2.getWeight());
    }
}
