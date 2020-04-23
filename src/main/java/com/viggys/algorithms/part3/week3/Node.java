package com.viggys.algorithms.part3.week3;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class Node {

    @NonNull
    private long weight;
    private int height = 0;
    private Node parent;
    private Node leftChild;
    private Node rightChild;

}
