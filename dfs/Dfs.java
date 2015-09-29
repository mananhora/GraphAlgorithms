import java.util.ArrayList;
public class Dfs {

	static int clock = 0;
	//Subroutine- explore
	public static void explore(Vertex s) {
		s.pre = clock;
		clock++;
		s.explored = true;//mark s as explored
		for (Vertex u : s.neighbours) {//explore neighbours of s
			if (!u.explored)
				explore(u);
		}
		s.post = clock;
		clock++;
	}

	//main algorithm
	public static void depthFirstSearch(Graph g) {
		clock = 0;
		for (Vertex u : g.verticesList)//mark all vertices as not explored
			u.explored = false;

		for (Vertex v : g.verticesList) {
			if (!v.explored)
				explore(v);
		}
	}

	//compute neighbours of s
	public static void computeNeighbours(Vertex s, Graph g) {
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		for (Edge edge : g.edgeList) {
			if (edge.firstVertex == s)
				neighbours.add(edge.secondVertex);
		}
		s.setNeighbours(neighbours);
	}

	//set verticesList
	public static void computeVerticesList(Graph g) {
		ArrayList<Vertex> verticesList = new ArrayList<Vertex>();
		for (Edge e : g.edgeList) {
			Vertex v = e.firstVertex;
			if (!verticesList.contains(v)) {
				verticesList.add(v);
			}

			Vertex u = e.secondVertex;
			if (!verticesList.contains(u)) {
				verticesList.add(u);
			}
		}
		g.setVerticesList(verticesList);
	}


	//helper method
	public static void dfs(Graph g) {
		computeVerticesList(g);

		for (Vertex u : g.verticesList) {
			computeNeighbours(u, g);
		}
		depthFirstSearch(g);
		for (Vertex v : g.verticesList) {
			System.out.println(v.number + " -post  " + v.post);
			System.out.println(v.number + " -pre " + v.pre);

		}
	}
}


