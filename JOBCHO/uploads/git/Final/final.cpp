#include <iostream>
#include <time.h>
#include <ctime>
using namespace std;

void Selection_Sort(int arr[], int size) {
    double t1 = clock();
    cout << "Selection Sort 과정 : " << endl;
    int index = 0;

    for(int i=0;i<size;i++) {
        int min = arr[index];
        int minIndex;
        for(int k=i;k<size;k++) {
            if(min >= arr[k]) {
                min = arr[k]; // min = 1 = arr[8]
                minIndex = k; // 8
            }
        } 

        arr[minIndex] = arr[index]; // arr[8] 자리에 arr[0] 을 넣어
        arr[index] = min; // arr[0] 자리에 min(1)을 넣고 index + 1
        index++;

        cout << "Step " << i + 1 << " :  ";

        for(int n=0;n<size;n++) {
            cout << arr[n] << " ";
        }
        cout << endl;
    }
    double t2 = clock();
    cout << (t2 - t1)/CLOCKS_PER_SEC << "초" << endl;
}

void Quick_Sort(int arr[], int size) {
    double t1 = clock();
    cout << "Quick Sort 과정 : " << endl;

    int index = 0;
    int lastindex = 10;
    int p = arr[index];

    double t2 = clock();
    cout << (t2 - t1)/CLOCKS_PER_SEC << "초" << endl;
}

void Merge_Sort(int arr[], int size) {
    double t1 = clock();

    cout << "Merge Sort 과정 : " << endl;
    double t2 = clock();
    cout << (t2 - t1)/CLOCKS_PER_SEC << "초" << endl;
}

int main() {
    int arr[] = {7,3,5,10,2,8,6,4,1,9};

    Selection_Sort(arr, 10);
    Quick_Sort(arr, 10);
    Merge_Sort(arr, 10);

    return 0;
}