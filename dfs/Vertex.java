import java.util.ArrayList;

public class Vertex {
	int number;
	ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
	boolean explored;
	int pre;
	int post;

	public Vertex(int number) {
		this.number = number;
	}

	public void setNeighbours(ArrayList<Vertex> neighbour) {
		this.neighbours = neighbour;
	}
}