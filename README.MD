# WNMTSS4J - a small but effiecient sudoku solver in Java

## Introduction
Life is short. _Waste No More Time Solving Sudokus for Java (WNMTSS4J)_ does exactly what its name says: It allows you
to solve these riddles very quickly. The computation takes only a few milliseconds
for common sudokus, and only a few for the most complex sudukos that have ever been designed.

The actual motivation behind this tool were numerous [Geocaches](http://www.geocaching.com) which require a Sudoku to be solved.

## How to run it

1. Get the JAR file from the Github [releases](/releases) page. Alternatively, build this project using maven, using `mvn clean install`. You'll find a `jar` file in the target folder in this case.
2. Run the solver by providing a sudoku file as text file:
   
```java -jar target/sudoku-0.0.1-jar-with-dependencies.jar ~/sudoku-files/wildcat1.txt```

## Sudoku File Format
The solver uses the following file format:

```wildcat1.txt
9
XXX26X7X1
68XX7XX9X
19XXX45XX
82X1XXX4X
XX46X29XX
X5XXX3X28
XX93XXX74
X4XX5XX36
7X3X18XXX
```


**X** denotes a blank field in the sudoku.

**Digits** obviously are used to mark given numbers.

**9**: The first line corresponds to the grid size. For Sudokus, this has to be set to 9. In the future, this
program might also support other grid sizes, for example hexadokus (16). 

The example above is the [`wildcatjan17` example](https://sandiway.arizona.edu/sudoku/examples.html), originally
published by Arizona Daily Wildcat on Tuesday, Jan 17th 2006.

## Some chatter about the algorithms

The solver can be run in a recursive (depth-first search, default) or iterative mode. 
The iterative mode can be turned on using the optional `--iterative` command line option.
While the recursive mode normally finds a solution faster, the iterative algorithm
outperforms recursive search for specific sudokus that have been designed to attack backtracking.

Both algorithms use constraint propagation to limit the search space: Instead of
blindly trying out a lot of numbers, both solvers always construct valid partial sudoku grids. To do
so, it uses BitSets to efficiently model the numbers already taken in the current row, column and subgrid.
Using bitwise OR between these constraints, we can efficiently decide which numbers are left
for the current cell. If the set of possible values is empty, we have reached a dead end. Otherwise, this
algorithm simply continues until all cells are filled.