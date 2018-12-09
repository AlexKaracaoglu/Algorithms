/*
    Alex Karacaoglu
    Algorithms
    Homework 6
    Depth First Search Using Stack
*/

#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

void printResultList(int* resultList, int size) {
    for (int i = 0; i < size; i++) {
        printf("%d ", resultList[i]);
    }
}

void printStack(int *stack, int stackPointer) {
    for (int i = 0; i < stackPointer; i++) {
        printf("%d ", stack[i]);
    }
    printf("\n");
}

int main() {
    int adjacencyList[2048][2048];
    for (int i = 0; i < 2048; i++) {
        for (int j = 0; j < 2048; j++) {
        adjacencyList[i][j] = -1;
        }
    }
    int start;
    char buffer[2048];
    int graphVertexNumber = 0;
    fgets(buffer, sizeof(buffer), stdin);
    start = atoi(buffer);
    fgets(buffer, sizeof(buffer), stdin);
    while (fgets(buffer, sizeof(buffer), stdin) > 0) {
        int count = 0;
        int init_size = sizeof(buffer);
        char delim[] = " ";
        char *ptr = strtok(buffer, delim);
        while (ptr != NULL) {
            int val = atoi(ptr);
            adjacencyList[graphVertexNumber][count] = val;
            ptr = strtok(NULL, delim);
            count++;
        }
        graphVertexNumber++;
    }
    int graphSize = graphVertexNumber;

    int visited[graphSize];
    for (int i = 0; i < graphSize; i++) {
      visited[i] = 0;
    }
    visited[start - 1] = 1;

    int stack[graphSize];
    int stackPointer = 0;
    stack[stackPointer] = start;

    int transitions[graphSize][2];
    int transitionRowPointer = 0;

    int resultList[graphSize];
    int resultListPointer = 0;
    int resultListSize = 1;
    resultList[resultListPointer] = start;
    resultListPointer++;

    while (stackPointer != -1) {
        printStack(stack, stackPointer + 1);
        int i = 0;
        int val = -1;
        int vertex = stack[stackPointer] - 1;
        while (adjacencyList[vertex][i] != -1) {
            int neighbor = adjacencyList[vertex][i];
            if (visited[neighbor - 1] == 0) {
                visited[neighbor - 1] = 1;
                val = neighbor;
                break;
            }
            i++;
        }
        if (val != -1) {
            resultList[resultListPointer] = val;
            resultListPointer++;
            stackPointer++;
            stack[stackPointer] = val;
            transitions[transitionRowPointer][0] = stack[stackPointer-1];
            transitions[transitionRowPointer][1] = stack[stackPointer];
            transitionRowPointer++;
        }
        else {
            stackPointer = stackPointer - 1;
        }
    }
    printf("\n");

    for (int i = 0; i < transitionRowPointer; i++) {
        printf("%d %d\n", transitions[i][0], transitions[i][1]);
    }

    printf("\n");

    printResultList(resultList, resultListPointer);
}
