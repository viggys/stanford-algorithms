package com.viggys.algorithms.part3.week1.qs1and2;

import lombok.*;

import java.util.Comparator;

@ToString(of = { "weight", "length" })
@RequiredArgsConstructor
@Getter @Setter
public class Job {

    private static final Comparator<Job> JOB_COMPARATOR = new JobComparator1();
//    private static final Comparator<Job> JOB_COMPARATOR = new JobComparator2();

    @NonNull private int weight;
    @NonNull private int length;


    private Job parent;
    private Job leftChild;
    private Job rightChild;

    public void insert(Job job) {
        int comparison = JOB_COMPARATOR.compare(job, this);
        if (comparison <= 0) {
            if (this.leftChild == null) {
                this.leftChild = job;
                job.setParent(this.leftChild);
            }
            else {
                this.leftChild.insert(job);
            }
        }
        else {
            if (this.rightChild == null) {
                this.rightChild = job;
                job.setParent(this.rightChild);
            }
            else {
                this.rightChild.insert(job);
            }
        }
    }

}
