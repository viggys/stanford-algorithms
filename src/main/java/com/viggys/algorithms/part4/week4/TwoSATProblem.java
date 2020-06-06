package com.viggys.algorithms.part4.week4;

import com.viggys.algorithms.part2.week1.Direction;
import com.viggys.algorithms.part2.week1.Graph;
import com.viggys.algorithms.part2.week1.SCC_Computation;
import com.viggys.algorithms.part2.week1.Vertex;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class TwoSATProblem {

    private static int VARIABLES;
    private static Graph GRAPH;

    public static void main(String[] args) {

        Long startTime = new Date().getTime();
        String[] cases = new String[]{"1", "2", "3", "4", "5", "6"};
        for(String caseId : cases) {
            SCC_Computation.reset();
            String path = "src/main/resources/2sat" + caseId + ".txt";
            readData(path);
            SCC_Computation.DFSLoop(GRAPH, Direction.IN);
            GRAPH.markUnexplored();
            SCC_Computation.DFSLoop(GRAPH, Direction.OUT);
            boolean result = is2SAT(GRAPH.getVertices());
            System.out.print(result ? "1" : "0");
        }
        Long endTime = new Date().getTime();
        double totalTime = ((double) (endTime - startTime) / (1000));
        System.out.println();
        System.out.println("Total Time = " + totalTime + " sec");

    }

    private static boolean is2SAT(Vertex[] vertices) {
        for(int i = 0; i < VARIABLES; i++) {
            Vertex v = vertices[i];
            Vertex v_ = vertices[i + VARIABLES];
            if(v != null && v_ != null) {
                if (v.getLeader() == v_.getLeader()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                int number = Integer.parseInt(line);
                VARIABLES = number;
                GRAPH = new Graph(2 * VARIABLES);
                SCC_Computation.LENGTH = 2 * VARIABLES;
                SCC_Computation.LEADER_COUNT = new int[SCC_Computation.LENGTH];
                line = reader.readLine();
                int count = 0;
                while (StringUtils.isNotEmpty(line)) {
                    count++;
                    String[] data = line.split("[ \t]");
                    int var1 = Integer.parseInt(data[0]);
                    int var2 = Integer.parseInt(data[1]);
//                    System.out.println(count + " ==> " + var1 + ", " + var2);
                    addToGraph(var1, var2);
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

    private static void addToGraph(int var1, int var2) throws Exception {
        if(var1 > 0 && var2 > 0) {
            GRAPH.addEdge(VARIABLES + var1, var2);
            GRAPH.addEdge(VARIABLES + var2, var1);
        }
        else if (var1 < 0 && var2 > 0) {
            GRAPH.addEdge(-var1, var2);
            GRAPH.addEdge(VARIABLES + var2, VARIABLES - var1);
        }
        else if(var1 > 0 && var2 < 0) {
            GRAPH.addEdge(VARIABLES + var1, VARIABLES - var2);
            GRAPH.addEdge(-var2, var1);
        }
        else if(var1 < 0 && var2 < 0) {
            GRAPH.addEdge(-var1, VARIABLES - var2);
            GRAPH.addEdge(-var2, VARIABLES - var1);
        }
        else {
            throw new Exception("Something is wrong");
        }
    }
}
