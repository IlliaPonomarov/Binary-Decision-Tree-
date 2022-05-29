***Binary Decision Diagram***

What is my project about?

I implemented my own algorithm for building a BDD tree in code, using Hash Tables for this.

**Why did I decide to implement it?**

When I searched for information about BDD trees on the Internet, I did not find more than a single example of the implementation of this tree in Java and other programming languages.
I became interested in creating my own version of such a tree.

**What is a Binary Decision Diagram?**

Binary decision diagrams provide a data structure for representing and manipulating Boolean functions in symbolic form. They have been especially effective as the algorithmic basis for symbolic model checkers. A binary decision diagram represents a Boolean function as a directed acyclic graph, corresponding to a compressed form of decision tree. Most commonly, an ordering constraint is imposed among the occurrences of decision variables in the graph, yielding ordered binary decision diagrams (OBDD). Representing all functions as OBDDs with a common variable ordering has the advantages that there is a unique, reduced representation of any function, there is a simple algorithm to reduce any OBDD to the unique form for that function, and there is an associated set of algorithms to implement a wide variety of operations on Boolean functions represented as OBDDs. Recent work in this area has focused on generalizations to represent larger classes of functions, as well as on scaling implementations to handle larger and more complex problems.


Link to full documentation:
https://docs.google.com/document/d/1FCMAi5yOjjb7wIBNtDxWqzFLjjFUjkSU12xyjLZZJ5c/edit?usp=sharing
