This is a Java project I wrote that solves KenKen puzzles. A description of KenKen puzzles can be found at http://www.kenkenpuzzle.com/

The majority of the work on this project went into the construction of the Grid and Cage classes where most of the logic takes place. The main solving method is recursive, which takes place in a private method of the main class.

If I get the chance, I would like to find a way to solve the puzzle without having to access the inner classes of the Grid class (Cage, Square). I would also like to create a method to solve non-recursively, as this would be more efficient memory wise, and possibly time wise. 
