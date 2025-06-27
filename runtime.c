// runtime.c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// integer input
int input() {
    int x;
    if (scanf("%d", &x) != 1) exit(1);
    return x;
}

// float input
double inputfloat() {
    double x;
    if (scanf("%lf", &x) != 1) exit(1);
    return x;
}

// string input (caller must free)
char* inputstring() {
    char buf[1024];
    if (scanf("%1023s", buf) != 1) exit(1);
    return strdup(buf);
}

// prints
void print_int(int x) {
    printf("%d\n", x);
}

void print_float(double x) {
    printf("%f\n", x);
}

void print_string(char* s) {
    printf("%s\n", s);
}

