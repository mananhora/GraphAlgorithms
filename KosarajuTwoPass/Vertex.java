import java.util.ArrayList;

public class Vertex {
	int v;
	String name;
	boolean explored;
	Vertex leader;
	int post;
	ArrayList<Vertex> neighbours = new ArrayList<Vertex>();

	public void setNeighbours(ArrayList<Vertex> neighbours) {
		this.neighbours = neighbours;
	}

	public Vertex(int v, String name) {
		this.name = name;
		this.v = v;
	}
}