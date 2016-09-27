# Task definition
The problem is to find all unique configurations of a set of normal chess pieces on a chess board with dimensions M×N where none of the   pieces is in a position to take any of the others. Providing the number of results is useful, but not enough to complete the assignment.   Assume the colour of the piece does not matter, and that there are no pawns among the pieces. 
Write a program which takes as input:

1. The dimensions of the board: M, N
2. The number of pieces of each type (King, Queen, Bishop, Rook and Knight) to try and place on the board. 

When returning your solution, please provide with your answer the total number of unique configurations for a 7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight. Also provide the time it took to get the final score. Needless to say, the lower the time, the better.

# Answer
There are 3063828 unique solutions. Execution time was ~22 seconds (VMWarePlayer & HP EliteBook 850).

# Execution

## Application

```
$ sbt 'run --help'
[info] Loading project definition from /home/kczulko/scala/scalac-chess-challenge/project
[info] Set current project to scalac-chess-challenge (in build file:/home/kczulko/scala/scalac-chess-challenge/)
[info] Running Main --help
Usage: Scalac's chess challenge [options]

  --rows <value>     number of rows
  --cols <value>     number of cols
  --kings <value>    number of kings
  --rooks <value>    number of rooks
  --queens <value>   number of queens
  --bishops <value>  number of bishops
  --knights <value>  number of knights
  --print <value>    number of boards to print
  --help             prints this usage

Exception: sbt.TrapExitSecurityException thrown from the UncaughtExceptionHandler in thread "run-main-0"
[success] Total time: 1 s, completed Sep 27, 2016 7:08:02 PM
```
Running Scalac's chess challenge task:
```
$ sbt 'run --rows 7 --cols 7 --queens 2 --kings 2 --bishops 2 --knights 1 --print 1'
[info] Loading project definition from /home/kczulko/scala/scalac-chess-challenge/project
[info] Set current project to scalac-chess-challenge (in build file:/home/kczulko/scala/scalac-chess-challenge/)
[info] Running Main --rows 7 --cols 7 --queens 2 --kings 2 --bishops 2 --knights 1 --print 1
19:11:47.769 INFO  @@  Placing piece... There are still 6 pieces left to place.
19:11:47.893 INFO  @@  Placing piece... There are still 5 pieces left to place.
19:11:48.006 INFO  @@  Placing piece... There are still 4 pieces left to place.
19:11:48.235 INFO  @@  Placing piece... There are still 3 pieces left to place.
19:11:48.754 INFO  @@  Placing piece... There are still 2 pieces left to place.
19:11:50.585 INFO  @@  Placing piece... There are still 1 pieces left to place.
19:11:59.185 INFO  @@  Placing piece... There are still 0 pieces left to place.
19:12:09.746 INFO  @@  Found 3063828 solutions in 22.061964380000003 seconds
19:12:09.752 INFO  @@  Printing no more than 1 solutions: 
----Q--
--Q----
K------
------N
---B--B
-------
K------
[success] Total time: 23 s, completed Sep 27, 2016 7:12:09 PM
```

### WARNING: 
This execution method (through sbt run) is using JVM options to set total amount of memory and specific garbage collector (profiling enhancement). It was tested with sbt 0.13.12 and there is no guarantee that local .sbtopts won't be ignored by other sbt launcher (probably sbt 0.13.11 ignores local .sbtopts). To ensure that your sbt supports local .sbtopts please execute 
```
jps -lvm
```
just after launching 
```
sbt 'run --rows 7...'
```
and assure that '-Xmx4G' and '-XX:+UseConcMarkSweepGC' options are enabled for VM which is running sbt. If your sbt launcher ignores .sbtopts then please try some other solution, e.g.
```
env SBT_OPTS="-Xmx4G -XX:+UseConcMarkSweepGC" sbt 'run --rows 7 ...'
```

## Tests

```
$ sbt testOnly
```

## Scalastyle

```
$ sbt scalastyle
```

## Code coverage

```
$ sbt clean coverage test coverageReport
```

# Understanding the problem
Chess board with size 7x7 contains 49 unique positions. The problem of setting 7 pieces (2 Kings, 2 Queens, 2 Bishops and 1 Knight) on this chess board can be explained as a typical combinatorics problem (more concisely 'permutations without repetition'). More info here:

* https://pl.wikipedia.org/wiki/Wariacja_bez_powt%C3%B3rze%C5%84 (polish)
* http://www.emathematics.net/combinapermutaciones.php (english)

Following the general formula:

![N|Solid](https://wikimedia.org/api/rest_v1/media/math/render/svg/515c1d989702311cb96007667c5a44104323a6ef)

One can apply n = 49 (7x7 chessboard positions) and k = 7 (7 pieces to set) which gives 439238943360 possible pieces combination on 7x7 board. Instead of brute force approach more efficient one is based on 'in-vivo' tree population. So having a chess board with some pieces already set, one can place another piece by choosing all of available positions and placing this piece sequentially on each one available position. It leads to populating another level of chessboards. Repetition of this step for other unplaced pieces will remarkably reduce number of possible solutions which results in efficient and fast computation.

# Application profiling

Application was profiled with '-Xprof' JVM option. First algorithm version was based on 'for-comprehension'. It was sequential in nature and had poor performance. Implementation was using backtracking algorithm with stack-unsafe recursive calls. Second algorithm version is fully tail-recursive. Reaching this stack-safe implementation was crucial for the task. It had opened a possibility for parallel computations which in fact helped to reduce performance bottlenecks. Another profiling enhancement, which reduced number of processed cases (branches), introduced only distinct intermediate solutions as input data for the next algorithm iteration. Intermediate solutions uniqueness was initially reached by a List::distinct call, but in fact it had poor performance which one could notice either in '-Xprof' JVM output or in a debug mode. Solution was to use Set instead of List which assures uniqueness of its elements by default (probably there is some special enhancement for flat map combiner in ParSet implementation(?))

# TODO
1. Property-based testing.
2. Cache for each piece placed on each position instead of ```.map(candidate)``` call.
3. scalafmt