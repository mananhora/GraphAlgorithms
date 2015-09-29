import java.util.ArrayList;

public class Graph {
	int vertices;//200
	ArrayList<Edge> listofEdges = new ArrayList<Edge>();
	ArrayList<Vertex> verticesList = new ArrayList<Vertex>();
	static int n = 0;

	public Graph(ArrayList<Edge> listofEdges) {
		this.listofEdges = listofEdges;
		for (Edge e : listofEdges) {
			if (!verticesList.contains(e.v)) {
				verticesList.add(e.v);
			}
			if (!verticesList.contains(e.w)) {
				verticesList.add(e.w);
			}
		}
		verticesList = quickSort(verticesList, 0, verticesList.size());


	}

	public void insert(Edge e) {
		listofEdges.add(e);
		for (Edge f : listofEdges) {
			if (!verticesList.contains(f.v)) {
				verticesList.add(f.v);
			}
			if (!verticesList.contains(f.w)) {
				verticesList.add(f.w);
			}
		}
		verticesList = quickSort(verticesList, 0, verticesList.size());

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
			if (array.get(j).v < pivot.v) {
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