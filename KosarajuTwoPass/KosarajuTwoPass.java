import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class KosarajuTwoPass {
	/*
	g-graph from file..
	gRev- graph with all edges of g reversed
	*/
	static Graph gRev = null;
	static int clock = 0;
	static ArrayList<Vertex> postNumberOrder = new ArrayList<Vertex>();
	static int n = 0;

	public static ArrayList<Scc> computeScc(Graph g) {
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		Vertex a = new Vertex (1, "a");
		Vertex b = new Vertex (2, "b");
		Vertex c = new Vertex (3, "c");
		Vertex d = new Vertex (4, "d");
		Vertex e = new Vertex (5, "e");
		Vertex f = new Vertex (6, "f");
		Vertex go = new Vertex (7, "g");
		Vertex h = new Vertex (8, "h");
		Vertex ik = new Vertex (9, "i");

		Edge ab = new Edge(b, a);
		Edge bc = new Edge(c, b);
		Edge ca = new Edge(a, c);
		Edge cd = new Edge(d, c);
		Edge de = new Edge(e, d);
		Edge ef = new Edge(f, e);
		Edge fd = new Edge(d, f);
		Edge fg = new Edge(go, f);
		Edge gh = new Edge(h, go);
		Edge hi = new Edge(ik, h);
		Edge ig = new Edge(go, ik);

		edgeList.add(ab);
		edgeList.add(bc);
		edgeList.add(cd);
		edgeList.add(ca);
		edgeList.add(de);
		edgeList.add(ef);
		edgeList.add(fd);
		edgeList.add(fg);
		edgeList.add(gh);
		edgeList.add(hi);
		edgeList.add(ig);


		gRev = new Graph(edgeList);
		computeVerticesList(g);
		computeVerticesList(gRev);


		for (Vertex u : g.verticesList) { //computes the neighbours for each vertex
			computeNeighbours(u, g);
		}
		for (Vertex u : gRev.verticesList) { //computes the neighbours for each vertex
			computeNeighbours(u, gRev);
		}
		gRev.verticesList = dfsLoop1(gRev);/*gets the vertices in
		  								decreasing order of their post numbers*/



		for (int i = 0; i < postNumberOrder.size(); i++) {
			for (int j = 0; j < postNumberOrder.size(); j++) {
				if (g.verticesList.get(i).name == postNumberOrder.get(j).name)
					g.verticesList.get(i).post = postNumberOrder.get(j).post;
			}

		}


		g.verticesList = orderVertices(g.verticesList);
		for (Vertex u : g.verticesList) {
			System.out.println("vertex  " + u.name + " " + u.post);
		}



		//mark all vertices of g as not explored:
		for (int i = g.verticesList.size() - 1; i >= 0; i--) {
			g.verticesList.get(i).explored = false;
		}

		/*run dfs on each unexplored vertex one by one..
		for each dfs, add the starting vertex and the vertices being
		explored from that vertex to an SCC*/

		clock = 0;
		ArrayList<Scc> listofScc = new ArrayList<Scc>();

		for (int i = 0; i < g.verticesList.size(); i++) {
			Vertex v = g.verticesList.get(i);
			ArrayList<Vertex> scc = null;
			if (v.explored == false) {
				System.out.println("exploring " + v.name);
				scc = new ArrayList<Vertex>();
				System.out.println("Adding " + v.name + " to scc");
				scc.add(v);
				explore(v, scc);
			}
			if (scc != null) {
				listofScc.add(new Scc(scc));
			}
		}
		// for (int i = 0; i < listofScc.size(); i++) {
		// 	for (int j = 0; j < listofScc.get(i).listofVertices.size(); j++) {
		// 		System.out.print(listofScc.get(i).listofVertices.get(j).name);
		// 	}
		// 	System.out.println("\n\n");
		// }

		Scc scc2 = listofScc.get(2);
		System.out.println(scc2.listofVertices.size());
		ArrayList<Vertex> list = scc2.listofVertices;
		for (Vertex u : list) {
			System.out.print(u.name);
		}

		return listofScc;
	}


	//Subroutine- explore
	public static void explore(Vertex s, ArrayList<Vertex> scc) {
		s.explored = true;//mark s as explored
		for (Vertex u : s.neighbours) {//explore neighbours of s
			System.out.println("neighbour of " + s.name + " - " + u.name);
			if (!u.explored) {
				System.out.println("Adding " + u.name + " to scc");
				scc.add(u);
				explore(u, scc);
			}
		}
	}


	public static ArrayList<Vertex> dfsLoop1(Graph g) {
		clock = 0;
		for (Vertex u : g.verticesList)//mark all vertices as not explored
			u.explored = false;

		for (Vertex v : g.verticesList) {
			if (!v.explored) {
				explore2(v);
			}
		}
		return postNumberOrder;

	}

	public static void explore2(Vertex s) {
		s.explored = true;//mark s as explored
		for (Vertex u : s.neighbours) {//explore neighbours of s
			if (!u.explored) {
				explore2(u);
			}
		}
		s.post = clock;
		postNumberOrder.add(s);
		clock++;
	}

	//compute neighbours of s
	public static void computeNeighbours(Vertex s, Graph g) {
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		for (Edge edge : g.listofEdges) {
			if (edge.firstVertex == s) {
				neighbours.add(edge.secondVertex);
			}
		}
		s.setNeighbours(neighbours);
	}

	//set verticesList
	public static void computeVerticesList(Graph g) {
		ArrayList<Vertex> verticesList = new ArrayList<Vertex>();
		for (Edge e : g.listofEdges) {
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


	public static ArrayList<Vertex> orderVertices(ArrayList<Vertex> list) {
		return quickSort(list, 0, list.size());//using the quickSort subroutine..
	}

	public static ArrayList<Vertex> quickSort(ArrayList<Vertex> array, int l, int r) {
		if (array.size() <= 1) {
			return array;
		}

		int pivotloc = pivotSort(array, l, r);
		if (pivotloc + 1 < r)
			array = quickSort(array, pivotloc + 1, r);

		if (l < pivotloc)
			array = quickSort(array, l, pivotloc);
		return array;
	}

	public static int pivotSort(ArrayList<Vertex> array, int l, int r) {
		Vertex pivot = array.get(l);
		int i = l;
		for (int j = l + 1; j < r; j++) {
			if (array.get(j).post > pivot.post) {
				n++;
				i = i + 1;
				Vertex temp = array.get(i);
				array.set(i, array.get(j));
				array.set(j, temp);
			}

		}
		array.set(l, array.get(i));
		array.set(i, pivot);
		return i;
	}
	// //create the graph from text file
	// public static Graph createGraph(String file) {
	// 	//first vertex is v first column , second is w second column,
	// 	//directed from first to second
	// 	Scanner fileIn = null;
	// 	try {
	// 		fileIn = new Scanner(new File(file));
	// 	} catch (IOException e) {
	// 		System.out.println("Does not exist");
	// 	}
	// 	//Initialize g and gRev
	// 	ArrayList<Edge> glistofEdges = new ArrayList<Edge>();
	// 	Graph g = new Graph(glistofEdges);
	// 	ArrayList<Edge> gRevlistofEdges = new ArrayList<Edge>();
	// 	gRev = new Graph(gRevlistofEdges);

	// 	while (fileIn.hasNextLine()) {
	// 		String line = fileIn.nextLine();
	// 		String data[] = line.split(" ");

	// 		String vname = data[0];//first vertex
	// 		String wname = data[1];//second vertex

	// 		Vertex v = null;//initialize first vertex

	// 		boolean vexists = checkifExists(g.verticesList, vname);//check if vertex exists
	// 		if (vexists == true) { //if exists, get that vertex and set it to v
	// 			v = getVertex(g.verticesList, vname);
	// 		} else { //else create a new vertex
	// 			v = new Vertex(Integer.parseInt(vname), vname, false);
	// 		}

	// 		Vertex w = null;//initialize second vertex
	// 		boolean wexists = checkifExists(g.verticesList, wname);
	// 		if (wexists == true) {
	// 			w = getVertex(g.verticesList, wname);

	// 		} else {
	// 			w = new Vertex(Integer.parseInt(wname), wname, false);
	// 		}

	// 		Edge e1 = new Edge(v, w);//Edge from first to second vertex
	// 		Edge e2 = new Edge(w, v);//reverse edge of e1
	// 		g.insert(e1);
	// 		gRev.insert(e2);

	// 	}
	// 	System.out.println("created graph");
	// 	return g;
	// }


}