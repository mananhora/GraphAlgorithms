import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class KosarajuTwoPass {
	/*
	g-graph from file..
	gRev- graph with all edges of g reversed
	*/
	static int t = 0;
	static Vertex s = null;
	static ArrayList<Vertex> leaders = new ArrayList<Vertex>();//list to keep track of leaders
	static Graph gRev = null;


	public static ArrayList<Scc> computeScc(Graph g) {
		System.out.println("start computeScc");
		dfsLoop1(gRev);//reorder the verticeslist to the order in which they are to be explored
		g.verticesList = gRev.verticesList;//set order of vertices of g to order of vertices of gRev..

		//mark all vertices of g as not explored:
		for (int i = 0; i < g.verticesList.size(); i++) {
			g.verticesList.get(i).explored = false;
		}

		dfsLoop2(g);
		ArrayList<Scc> listofScc = new ArrayList<Scc>();

		for (int i = 0; i < leaders.size(); i++ ) {
			ArrayList<Vertex> sameLeader = new ArrayList<Vertex>();

			for (int j = 0; j < g.verticesList.size(); j++) {
				if (g.verticesList.get(j).leader == leaders.get(i)) {
					sameLeader.add(g.verticesList.get(j));
				}
			}
			Scc scc = new Scc(sameLeader);
			listofScc.add(scc);
		}
		System.out.println("end computeScc");
		return listofScc;
	}


	//DFS
	//dfS subroutine for gRev
	public static void dfS1(Graph g, Vertex i) {
		i.explored = true;
		i.leader = s; //global variable s (vertex)
		for (int k = 0; k < g.listofEdges.size(); k++) {
			if (g.listofEdges.get(k).v == i) {
				if (g.listofEdges.get(k).w.explored == false) {
					dfS1(g, g.listofEdges.get(k).w);
				}
			}
		}
		t++;
		i.finishingtime = t;
	}

	//dfS subroutine for g
	public static void dfS2(Graph g, Vertex i) {
		i.explored = true;
		i.leader = s; //global variable s (vertex)
		if (!leaders.contains(s)) {
			leaders.add(s);
		}
		for (int k = 0; k < g.listofEdges.size(); k++) {
			if (g.listofEdges.get(k).v == i) {
				if (g.listofEdges.get(k).w.explored == false) {
					dfS2(g, g.listofEdges.get(k).w);
				}
			}
		}
		t++;
		i.finishingtime = t;
	}


	//Sort vertices list by finishing time of vertices
	public static ArrayList<Vertex> orderVertices(ArrayList<Vertex> list) {
		return quickSort(list, 0, list.size());//using the quickSort subroutine..
	}


	//DFS LOOP
	//dfsLoop for gRev
	public static void dfsLoop1(Graph g) {
		for (int i = g.verticesList.size() - 1; i >= 0; i--) {
			if (g.verticesList.get(i).explored == false) {
				s = g.verticesList.get(i);
				dfS1(g, g.verticesList.get(i));
			}
		}
		g.verticesList = orderVertices(g.verticesList);
	}

	//dfsLoop for g
	public static void dfsLoop2(Graph g) {
		for (int i = 0; i < g.verticesList.size(); i++) {
			if (g.verticesList.get(i).explored == false) {
				s = g.verticesList.get(i);
				dfS2(g, g.verticesList.get(i));
			}
		}
	}





	//check if vertex with name == vname exists in list
	public static boolean checkifExists(ArrayList<Vertex> list, String vname) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).name.equals(vname)) {
				return true;
			}
		}
		return false;
	}

	//extract vertex with name==vname from list
	public static Vertex getVertex(ArrayList<Vertex> list, String vname) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).name.equals(vname)) {
				Vertex v = list.get(i);
				return v;
			}
		}
		return null;
	}

	public static Graph createGraph(String file) {
		System.out.println("Creating graph");
		//first vertex is v first column , second is w second column,
		//directed from first to second
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new File(file));
		} catch (IOException e) {
			System.out.println("Does not exist");
		}
		//Initialize g and gRev
		ArrayList<Edge> glistofEdges = new ArrayList<Edge>();
		Graph g = new Graph(glistofEdges);
		ArrayList<Edge> gRevlistofEdges = new ArrayList<Edge>();
		gRev = new Graph(gRevlistofEdges);

		while (fileIn.hasNextLine()) {
			String line = fileIn.nextLine();
			String data[] = line.split(" ");

			String vname = data[0];//first vertex
			String wname = data[1];//second vertex

			Vertex v = null;//initialize first vertex

			boolean vexists = checkifExists(g.verticesList, vname);//check if vertex exists
			if (vexists == true) { //if exists, get that vertex and set it to v
				v = getVertex(g.verticesList, vname);
			} else { //else create a new vertex
				v = new Vertex(Integer.parseInt(vname), vname, false);
			}

			Vertex w = null;//initialize second vertex
			boolean wexists = checkifExists(g.verticesList, wname);
			if (wexists == true) {
				w = getVertex(g.verticesList, wname);

			} else {
				w = new Vertex(Integer.parseInt(wname), wname, false);
			}

			Edge e1 = new Edge(v, w);//Edge from first to second vertex
			Edge e2 = new Edge(w, v);//reverse edge of e1
			g.insert(e1);
			gRev.insert(e2);

		}
		System.out.println("created graph");
		return g;
	}

	//MAIN
	public static void main(String args[]) {
		Graph g = createGraph("SCC.txt");//create graph from file..

		ArrayList<Scc> sccList = computeScc(g);

		System.out.println("SCCs:  -");
		for (int i = 0; i < sccList.size(); i++) {
			for (int j = 0; j < sccList.get(i).listofVertices.size(); j++) {
				System.out.print(sccList.get(i).listofVertices.get(j).name + ", ");
			}
			System.out.println();

		}
	}


	static int n = 0;//global variable required for quickSort subroutine

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
			if (array.get(j).finishingtime > pivot.finishingtime) {
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

}