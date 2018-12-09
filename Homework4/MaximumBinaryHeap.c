/*
    Alex Karacaoglu
    Homework 4
    #3 - Maximum Binary Heap
*/

#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main() {
    int array[5000];
    int count = 0;
    int a;
    while(scanf("%d", &a)>0) {
        array[count] = a;
        bubbleUp(array, count);
        count++;
        printArray(array, count);
        printf("\n");
    }
    count--;
    while (count > 0) {
        swap(array, 0, count);
        bubbleDown(array, 0, count);
        printArray(array, count);
        printf("\n");
        count--;
    }

}

void bubbleUp(int *a, int count){
    int parent = (count - 1) / 2;
    int child = count;
    while (a[parent] < a[child] && parent < child) {
        swap(a, parent, child);
        bubbleUp(a, parent);
    }
    return;
}

void bubbleDown(int * a, int parent, int count) {
    int lchild = 2 * parent + 1;
    int rchild = 2 * parent + 2;
    if (rchild < count && a[parent] < a[rchild] && a[parent] < a[lchild]) {
        if (a[lchild] >= a[rchild]) {
            swap(a, parent, lchild);
            bubbleDown(a, lchild, count);
        }
        else {
            swap(a, parent, rchild);
            bubbleDown(a, rchild, count);
        }
    }
    else if (a[parent] < a[rchild] && rchild < count) {
        swap(a, parent, rchild);
        bubbleDown(a, rchild, count);
    }
    else if (a[parent] < a[lchild] && lchild < count) {
        swap(a, parent, lchild);
        bubbleDown(a, lchild, count);
    }
    return;
}

void printArray(int* a, int count) {
    for (int i = 0; i < count; i++) {
        printf("%d ", a[i]);
    }
}

void swap(int* a, int indexA, int indexB) {
    int item = a[indexA];
    a[indexA] = a[indexB];
    a[indexB] = item;
}
