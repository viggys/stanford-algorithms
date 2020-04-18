package com.viggys.algorithms.part3.week2.qs2;

import lombok.*;

import java.util.BitSet;

@ToString(of = { "id", "rank" })
@Getter
public class Vertex {

    private int id;

    @Setter
    private Vertex parent = this;

    @Setter
    private int rank = 0;
    private BitSet data;

    public Vertex(int id, int dataSize) {
        this.id = id;
        this.data = new BitSet(dataSize);
    }

    public void setData(String data[]) {
        for(int index = 0; index < data.length; index++) {
            int value = Integer.parseInt(data[index]);
            if(value == 1) {
                this.data.set(index);
            }
        }
    }

    public boolean getByteAtIndex(int index) {
        return data.get(index);
    }

}
