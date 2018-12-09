/*
    Alex Karacaoglu
    Algorithms
    Homework 6
    Breadth First Search Using Queue
*/

#include <assert.h>
#include <limits.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void printResultList(int *resultList, int size) {
  for (int i = 0; i < size; i++) {
    printf("%d ", resultList[i]);
  }
}

void printQueue(int *queue, int queueLocation, int queueNext) {
  for (int i = queueNext-1; i > queueLocation; i--) {
    printf("%d ", queue[i]);
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

  int queue[graphSize];
  int queueLocation = 0;
  int queueNext = 0;
  queue[queueNext] = start;
  queueNext++;
  printf("%d\n", start);

  int transitions[graphSize][2];
  int transitionRowPointer = 0;

  int resultList[graphSize];
  int resultListPointer = 0;

  while (queueLocation != queueNext) {
    int i = 0;
    int val;
    int vertex = queue[queueLocation] - 1;
    while (adjacencyList[vertex][i] != -1) {
      int neighbor = adjacencyList[vertex][i];

      if (visited[neighbor - 1] == 0) {

        visited[neighbor - 1] = 1;

        queue[queueNext] = neighbor;

        resultList[resultListPointer] = neighbor;
        resultListPointer++;

        transitions[transitionRowPointer][0] = queue[queueLocation];
        transitions[transitionRowPointer][1] = queue[queueNext];
        transitionRowPointer++;

        queueNext++;
      }
      i++;
    }
    printQueue(queue, queueLocation, queueNext);
    queueLocation++;
  }

  for (int i = 0; i < transitionRowPointer; i++) {
    printf("%d %d\n", transitions[i][0], transitions[i][1]);
  }
  printf("\n");

  printResultList(resultList, resultListPointer);
}
