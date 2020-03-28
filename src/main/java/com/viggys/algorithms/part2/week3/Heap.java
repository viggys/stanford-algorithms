package com.viggys.algorithms.part2.week3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class Heap {

    private List<Integer> data = new LinkedList<>();

    @NonNull
    private HeapType type;

    public void insert(Integer number) {
        insert(number, data.size());
    }

    private void insert(Integer number, int index) {
        data.add(index, number);
        bubbleUp(index);
    }

    public boolean isEmpty() {
        return (getSize() == 0);
    }

    public int getSize() {
        return data.size();
    }

    public Integer get(int index) {
        if(index >= getSize())
            throw new NoSuchElementException("Heap is empty");
        return data.get(index);
    }

    public Integer remove(int index) {
        if(index >= getSize())
            throw new NoSuchElementException("Heap is empty");
        Integer element = data.remove(index);
        data.add(index, data.remove(getSize() - 1));
        bubbleDown(index);
        return element;
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
            return data.get(parentIndex) < data.get(childIndex);
        else
            return data.get(parentIndex) > data.get(childIndex);
    }

    private int getSuitableIndex(int leftChildIndex, int rightChildIndex) {
        Integer leftChild = data.get(leftChildIndex);
        Integer rightChild = data.get(rightChildIndex);
        Integer suitableChild = null;

        if(type == HeapType.MIN)
            suitableChild = Math.min(leftChild, rightChild);
        else
            suitableChild = Math.max(leftChild, rightChild);

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
        Integer parent = data.remove(parentIndex);
        data.add(parentIndex, data.remove(childIndex - 1));
        data.add(childIndex, parent);
    }

}

enum HeapType {
    MIN,
    MAX
}
