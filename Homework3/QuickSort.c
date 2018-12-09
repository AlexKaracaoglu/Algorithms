/*
 * Alex Karacaoglu
 * Algorithms
 * Homework 3
 * in_place_quick_sort
 */

/*
 * Complete the 'in_place_quick_sort' function below.
 *
 * The function accepts following parameters:
 *  1. FLOAT_ARRAY input_output_array
 *  2. INTEGER start_index
 *  3. INTEGER end_index
 */

void in_place_quick_sort(float* input_output_array, int start_index, int end_index) {
    if (end_index <= start_index) {
        return ;
    }

    //our pivot is the first element
    int pivotIndex = start_index;

    //equals is number of elements equal to pivot (not including pivot), we partition the array as needed
    int equals = partitionAndPlaceEqualsAtEnd(input_output_array, start_index, end_index, &pivotIndex);

    //we placed all elements equal to pivot at end, equalsBlockSwap swaps them to right after the pivot
    equalsBlockSwap(input_output_array, pivotIndex + 1, end_index, equals);

    //run in_place_quick_sort on stuff strictly less than pivot
    in_place_quick_sort(input_output_array, start_index, pivotIndex - 1);

    //run in_place_quick_sort on stuff strictly greater than pivot
    in_place_quick_sort(input_output_array, pivotIndex + equals + 1, end_index);
}

void equalsBlockSwap(float* array, int start, int end, int numberOfEquals) {
    float pivot = array[start-1];
    while (numberOfEquals > 0 && array[start]!= pivot) {
        swap(array, start, end);
        numberOfEquals--;
        start++;
        end--;
    }
}

int partitionAndPlaceEqualsAtEnd(float* array, int start, int end, int* pivotIndex) {
    int left = start + 1;
    int right = end;
    int pivot = pivotIndex[0];
    int equals = 0;
    while (left < right) {
        if (array[right] == array[pivot]) {
            swap(array, right, end-equals);
            equals++;
        }
        if (array[left] > array[pivot] && array[right] < array[pivot]) {
            if (array[left] == array[pivot]) {
                swap(array, left, right);
                swap(array, right, end-equals);
                equals++;
            }
            else {
                swap(array, left, right, pivot);
            }
            left++;
            right--;
        }
        else if (array[left] > array[pivot]) {
            right--;
        }
        else if (array[right] < array[pivot]) {
            left++;
        }
        else {
            left++;
            right--;
        }
    }
    if (left == right) {
        if (array[right] == array[pivot]) {
            swap(array, right, end-equals);
            equals++;
        }
        if (array[right] >= array[pivot]) {
            swap(array, pivot, left - 1);
            pivotIndex[0] = left - 1;
        }
        else {
            swap(array, pivot, left);
            pivotIndex[0] = left;
        }
    }
    else {
        swap(array, pivot, left - 1);
        pivotIndex[0] = left - 1;
    }
    return equals;
}

void swap(float* input_output_array, int indexA, int indexB) {
    float item = input_output_array[indexA];
    input_output_array[indexA] = input_output_array[indexB];
    input_output_array[indexB] = item;
}
