What is a Binary Decision Diagram?

Binary decision diagrams provide a data structure for representing and manipulating Boolean functions in symbolic form. They have been especially effective as the algorithmic basis for symbolic model checkers. A binary decision diagram represents a Boolean function as a directed acyclic graph, corresponding to a compressed form of decision tree. Most commonly, an ordering constraint is imposed among the occurrences of decision variables in the graph, yielding ordered binary decision diagrams (OBDD). Representing all functions as OBDDs with a common variable ordering has the advantages that there is a unique, reduced representation of any function, there is a simple algorithm to reduce any OBDD to the unique form for that function, and there is an associated set of algorithms to implement a wide variety of operations on Boolean functions represented as OBDDs. Recent work in this area has focused on generalizations to represent larger classes of functions, as well as on scaling implementations to handle larger and more complex problems.