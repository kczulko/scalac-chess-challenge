# Task definition
The problem is   to   find   all   unique   configurations   of   a   set   of   normal chess pieces   on   a chess board   with 
dimensions   M×N   where   none   of   the   pieces   is   in   a   position   to   take   any   of   the   others.   Providing   the   number   of 
results   is   useful,   but   not   enough   to   complete   the   assignment.   Assume   the   colour   of   the   piece   does   not 
matter, and that there are no pawns among the pieces. 
Write a program which takes as input: 
1) The dimensions of the board: M, N 
2) The   number   of   pieces   of   each   type   (King,   Queen,   Bishop,   Rook   and   Knight)   to   try   and   place   on   the board. 

When   returning   your   solution,   please   provide   with   your   answer   the   total   number   of   unique   configurations   for a  ​
7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight. Also provide the time it took to get the final score. Needless to say, the lower the time, the better.

# Answer
There are 3063828 unique solutions. Execution time was ~22 seconds (VMWarePlayer & HP EliteBook 850).

# Understanding the problem
Chess board with size 7x7 contains 49 unique positions. The problem of setting 7 pieces (2 Kings, 2 Queens, 2 Bishops and 1 Knight) on this chess board can be explained as a typical combinatorics problem (more concisely 'permutations without repetition'). More info here:

* https://pl.wikipedia.org/wiki/Wariacja_bez_powt%C3%B3rze%C5%84 (polish)
* http://www.emathematics.net/combinapermutaciones.php (english)

Following the general formula:

![N|Solid](https://wikimedia.org/api/rest_v1/media/math/render/svg/515c1d989702311cb96007667c5a44104323a6ef)

one can apply n = 49 (7x7 chessboard positions) and k = 7 (7 pieces to set) which gives 439238943360 possible pieces combination on 7x7 board. Instead of brute force approach more efficient one is based on 'in-vivo' tree population. So having a chess board with some pieces already set, one can place another piece by choosing all of available positions and placing this piece sequentially on each one available position. It leads to populating another level of chessboards. Repetition of this step for other unplaced pieces will remarkably reduce number of possible solutions which results in efficient and fast answer.

# Application profiling

Application was profiled with a huge help of '-Xprof' JVM option. First algorithm version was based on 'for-comprehension'. It was sequential in nature and had poor performance. Implementation was using backtracking algoritm with stack-unsafe recursive calls. Second algorithm version is fully tail-recursive. Reaching this stack-safe implementation was crutial for the task. It had opened a possibility for parallel computations which in fact helped to reduce performance bottlenecks. Another profiling enhancement, which reduced number of processed cases (branches), introduced only distinct intermediate solutions as input data for the next algorithm iteration. Intermediate solutions uniqueness was initially reached by a List::distinct call, but in fact it had poor performance which one could notice either in '-Xprof' JVM output or in a debug mode. Solution was to use Set instead of List which assures uniqueness of its elements by default (probably there is also some special enhancement for flat map combiner in ParSet implementation (?))

# Tests
To run tests with sbt please execute:
```
sbt testOnly
```

# Application

Parameters are self explained :)

```
sbt 'run --rows 7 --cols 7 --queens 2 --bishops 2 --kings 2 --knights 1 --print 15'
```
### WARNING: 
This sbt running method is using JVM options to set total amount of memory and specific garbage collector (profiling enhancement). It was tested with sbt 0.13.12 and there is no guarantee that local .sbtopts won't be ignored by other sbt launcher (probably sbt 0.13.11 ignores local .sbtopts). To ensure that your sbt supports local .sbtopts please execute 
```
jps -lvm
```
just after launching 
```
sbt 'run --rows 7...'
```
and assure that '-Xmx4G' and '-XX:+UseConcMarkAndSweepGC' options are enabled for VM which is running sbt. If your sbt launcher is ignoring .sbtopts then please try some other solution, e.g.
```
env SBT_OPTS="-Xmx4G -XX:+UseConcMarkAndSweepGC" sbt 'run --rows 7 ...'
```