package com.viggys.algorithms.part4.week2;

import lombok.AllArgsConstructor;

import java.util.BitSet;
import java.util.concurrent.RecursiveAction;

import static com.viggys.algorithms.part4.week2.TravellingSalesmanProblem.*;
import static com.viggys.algorithms.part4.week2.TravellingSalesmanProblem.HISTORY;

@AllArgsConstructor
public class ComputeAction extends RecursiveAction {

    private BitSet subset;

    @Override
    protected void compute() {
        execute(subset);
    }

    static void execute(BitSet subset) {
        for(int i = 0; i < subset.size(); i++) {
            if(subset.get(i)) {
                Node destination = NODE_ARR[i];
                BitSet subset_1 = (BitSet) subset.clone();
                subset_1.flip(i);
                if(subset_1.equals(new BitSet(NODE_ARR.length))) {
                    float distance = getDistance(SOURCE, destination);
                    HISTORY[1].get(subset)[i] = distance;
                }
                else {
                    float distance = -1;
                    for(int j = 0; j < subset_1.size(); j++) {
                        if(subset_1.get(j)) {
                            Node stop = NODE_ARR[j];
                            float stopToDest = getDistance(stop, destination);
                            float sourceToStop = HISTORY[0].get(subset_1)[stop.getId() - 2];
                            if((sourceToStop + stopToDest) < distance || distance < 0) {
                                distance = sourceToStop + stopToDest;
                            }
                        }
                    }
                    HISTORY[1].get(subset)[destination.getId() - 2] = distance;
                }
            }
        }
    }
}
