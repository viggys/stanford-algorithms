package com.viggys.algorithms.part3.week1.qs3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@ToString(of = { "data" })
@RequiredArgsConstructor
public class Heap {

    private VertexComparator comparator = new VertexComparator();
    private List<Vertex> data = new LinkedList<>();

    @NonNull
    private HeapType type;

    public void insert(Vertex vertex) {
        insert(vertex, data.size());
    }

    private void insert(Vertex vertex, int index) {
        data.add(index, vertex);
        bubbleUp(index);
    }

    public boolean isEmpty() {
        return (getSize() == 0);
    }

    public int getSize() {
        return data.size();
    }

    public Vertex pop() {
        if(isEmpty())
            return null;
        Vertex vertex = remove(0);
        return vertex;
    }

    public Vertex remove(Vertex vertex) {
        int index = indexOf(vertex);
        return remove(index);
    }

    private Vertex remove(int index) {
        if(index >= getSize() || index < 0)
            throw new NoSuchElementException("Vertex not in Heap");
        Vertex vertex = data.remove(index);
        int lastIndex = getSize() - 1;
        if(index < lastIndex - 1) {
            Vertex lastVertex = data.remove(lastIndex);
            data.add(index, lastVertex);
        }
        heapify(index);
        return vertex;
    }

    private int indexOf(Vertex vertex) {
        if(data.contains(vertex))
            return data.indexOf(vertex);
        else
            return -1;
    }

    private void heapify(int index) {
        if(!isConsistent(getParentIndex(index), index)) {
            bubbleUp(index);
        }
        else {
            bubbleDown(index);
        }
    }

    private void bubbleUp(int childIndex) {
        int parentIndex = getParentIndex(childIndex);
        while(!isConsistent(parentIndex, childIndex)) {
            swap(parentIndex, childIndex);
            childIndex = parentIndex;
            parentIndex = getParentIndex(childIndex);
        }
    }

    private void bubbleDown(int parentIndex) {
        int leftChildIndex = getLeftChildIndex(parentIndex);
        int rightChildIndex = getRightChildIndex(parentIndex);
        boolean isLeftConsistent = isConsistent(parentIndex, leftChildIndex);
        boolean isRightConsistent = isConsistent(parentIndex, rightChildIndex);
        while(!isLeftConsistent || !isRightConsistent) {
            if(isLeftConsistent && !isRightConsistent) {
                swap(parentIndex, rightChildIndex);
                parentIndex = rightChildIndex;
            }
            else if(!isLeftConsistent && isRightConsistent) {
                swap(parentIndex, leftChildIndex);
                parentIndex = leftChildIndex;
            }
            else {
                int suitableIndex = getSuitableIndex(leftChildIndex, rightChildIndex);
                swap(parentIndex, suitableIndex);
                parentIndex = suitableIndex;
            }
            leftChildIndex = getLeftChildIndex(parentIndex);
            rightChildIndex = getRightChildIndex(parentIndex);
            isLeftConsistent = isConsistent(parentIndex, leftChildIndex);
            isRightConsistent = isConsistent(parentIndex, rightChildIndex);
        }
    }

    private boolean isConsistent(int parentIndex, int childIndex) {
        if(childIndex >= getSize() || parentIndex < 0)
            return true;
        if(type == HeapType.MIN)
            return comparator.compare(data.get(parentIndex), data.get(childIndex)) <= 0;
        else
            return comparator.compare(data.get(parentIndex), data.get(childIndex)) > 0;
    }

    private int getSuitableIndex(int leftChildIndex, int rightChildIndex) {
        Vertex leftChild = data.get(leftChildIndex);
        Vertex rightChild = data.get(rightChildIndex);
        Vertex suitableChild = null;

        if(type == HeapType.MIN)
            suitableChild = comparator.compare(leftChild,rightChild) <= 0 ? leftChild : rightChild;
        else
            suitableChild = comparator.compare(leftChild,rightChild) > 0 ? leftChild : rightChild;

        if(suitableChild.equals(leftChild))
            return leftChildIndex;
        else
            return rightChildIndex;
    }

    private int getParentIndex(int childIndex) {
        return ((childIndex + 1) / 2) - 1;
    }

    private int getLeftChildIndex(int parentIndex) {
        return (2 * (parentIndex + 1)) - 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return (2 * (parentIndex + 1));
    }

    private void swap(int parentIndex, int childIndex) {
        Vertex parent = data.remove(parentIndex);
        data.add(parentIndex, data.remove(childIndex - 1));
        data.add(childIndex, parent);
    }

}

enum HeapType {
    MIN,
    MAX
}
