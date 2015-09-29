1)The Algorithm
//Describe Kosaraju's two pass algorithm..
Kosaraju's Two Pass Algorithm is an algorithm for finding all the strongly connected components of a directed graph. 

Principle:
This algorithm uses the depth-first-search subroutine to traverse graph and find the strongly connected components. 

The Basic Idea:
 Pick a vertex and explore every vertex you can starting from the source vertex. Whenever you reach a point where you can't explore any more vertices without changing the source, you stop, and the vertices traversed so far starting from the source are part of one SCC. Then you pick another vertex and do the same.

		The problem of choosing vertices:
		We cannot choose any random vertex to begin with. If we choose any random vertex, it is likely that we may end up traversing into multiple SCCs.

	Solving This Problem:
	We compute a 'magical ordering' of the vertices, and traverse the graph in that order to discover the SCCs one by one. This ordering is such that in one DFS, you can only explore vertices of a particular SCC and not reach any vertex of another SCC.

		Computing The 'Magical Ordering':
		We create another graph, gRev, which is same as the original graph g, except with all the edges reversed. And then we run DFS-Loop (described below) on gRev, this gives us ordering in which we then traverse g to get the SCCs.

		DFS-Loop
		Global varibles: 
		a)t = 0 (# of vertices processed so far)
		b)s = null (current 'source' vertex in dfs) 

		Assume all vertices are labelled 1 to n:
		going down from n to 1, if a vertex is not explored, set it as s and call the dfs subroutine (described below) on it. 


		DFS-Subroutine(Graph g, Vertex i)
		-Mark i as explored
		-Set leader(i) = vertex s
		-for each edge(i, j) belonging to g:
			if j not yet explored:
				call DFS-Subroutine(g, j)
		-t++
		-finishing time of i = t

		*We need 2 slightly different DFS-loops when coding in java: One for the first pass of the algorithm on gRev, which orders the vertices in decreasing order of 'finishing time', and the other for the second pass of the algorithm on g. 
		For the first pass, we have to keep track of the variable t (finishing time)
		For the second pass, we have to keep track of the leaders of the vertices.
		An SCC is a collection of vertices with the same leader.



Claim: The SCCs of a directed graph induce an acyclic "meta-graph"
Why acyclic? a cycle of SCCs would collapse into one..

Key Lemma:
Consider two SCCs C1 and C2, and there is a path from C1 to C2 (but not the other way round, as obvious from the aforementioned claim)
The maximum finishing time among all the vertices of C1 must be less than the maximum finishing time of all the vertices of C2.

Corollary: maximum finishing time value must lie in a "sink SCC"
By the Corollary, the second pass of the DFS-Loop begins somewhere in a sink SCC. 





2)The Files
Graph.java- The Graph Class
Scc.java - For creating an object for representing the Strongly Connected Components (Sccs) of a graph 
Vertex.java- The Vertex Class, representing the vertices of a graph
Edge.java - The Edge Class, representing the edges of a graph
KosarajuTwoPass.java - Contains code for the implementation of the Algorithm


