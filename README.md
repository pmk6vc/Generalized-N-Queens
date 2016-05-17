# Generalized-N-Queens

A generalization of the well-studied "N Queens" problem, for my final project for CS3102 (Theory of Computation). The original problem is to find all placements of N queens on an N x N chessboard such that no queen is attacking any other queen. This program addresses three generalizations of this original problem:

1) Find and display all solutions for any Q queens on an N x N board

This generalization removes the constraint that only N queens can be placed on an N x N board. Instead, the user can indicate any number of queens as well as the board dimension.

2) Find and display one solution for any Q queens, R rooks, B bishops, and K knights on an N x N board

This generalization extends the type of pieces from just queens to all major chess pieces. The user is prompted to input any number of queens, rooks, bishops, and knights (they do not have to be equal), as well as the board dimension. However, the program will only display the first solution that is found (if a solution exists) and halt; it will not find all possible solutions.

3) Find and print a solution for Q queens on an N x N x N board

This generalization extends the chessboard and the queen's range of motion from 2 dimensions to 3 dimensions. A "3D Queen" is defined as a queen that can move in all of the traditional 2D ways but can also move in vertical files and along the 4 3D diagonals in the cubic chessboard. The constraints of the problem are the same as the original problem: no 2 3D queens should be in a position to attack each other. The solution, if one exists, is represented as a set of 3D coordinates specifying each queen's position, each of which is printed to the console.
