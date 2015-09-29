import java.util.ArrayList;
public class Graph {
	ArrayList<Vertex> verticesList = new ArrayList<Vertex>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();

	public Graph(ArrayList<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	public void setVerticesList(ArrayList<Vertex> verticesList) {
		this.verticesList = verticesList;
	}
}