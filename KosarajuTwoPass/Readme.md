1)The Algorithm
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
		Key Lemma:
        Consider two SCCs C1 and C2, and there is a path from C1 to C2 (but not the other way round, as obvious from the aforementioned claim)
        The maximum post number among all the vertices of C1 must be more than the maximum post number of all the vertices of C2.
        This can be restated as saying that the strongly connected components can be lin- earized by arranging them in decreasing order of their highest post numbers.

        Based on the above lemma, we can say that the vertex that has the highest post number must lie in a source strongly connected component. 

        But what we need is a sink SCC. (Why? because if we run dfs from a vertex in the sink SCC, it will explore all the vertices in the SCC but not go any further since it's a sink)
        In order to do so, we reverse the Graph G, to get G-rev, which has the exact same SCCs as G. So if we do a depth first search on Grev, the vertex with the highest post number will come from vertex in a source SCC of G-rev, which actually is the sink SCC of G. 

        Now, we have found the first vertex from which we will run dfs in G to discover the first SCC. After the first SCC has been found, how do we find the next vertex to run dfs from to get the remaining SCCs?
        We delete the SCC we have found from the graph. The node with the *highest post number among those remaining will belong to a sink strongly connected component of whatever remains of G. Therefore we can keep using the post numbering from our initial depth-first search on G-rev to successively output the second strongly connected component, the third strongly connected component, and so on.

        *found from dfs on G-rev. 

        The resulting Algorithm is this:
        1. Run depth-first search on G-rev
        2. Run depth-first search on G, processing the vertices in decreasing order of their post-numbers from Step 1. For each vertex being processed, there is an SCC. Add all the vertices being explored via dfs from a vertex to an SCC. Once you're done with dfs from the vertex, you've found an SCC. In the next dfs, add the vertices to the  next SCC and so on...
		

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
		-post(i) = t

		*We need 2 slightly different DFS-loops when coding in java: One for the first pass of the algorithm on G-rev, which orders the vertices in decreasing order of post numbers, and the other for the second pass of the algorithm on G. 
		For the first pass, we have to keep track of the variable t (post number)
		For the second pass, we have to keep track of the leaders of the vertices.
		An SCC is a collection of vertices with the same leader.


2)The Files
Graph.java- The Graph Class
Scc.java - For creating an object for representing the Strongly Connected Components (Sccs) of a graph 
Vertex.java- The Vertex Class, representing the vertices of a graph
Edge.java - The Edge Class, representing the edges of a graph
KosarajuTwoPass.java - Contains code for the implementation of the Algorithm


