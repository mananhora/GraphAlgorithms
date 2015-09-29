public class Vertex {
	int v;
	String name;
	boolean explored;
	Vertex leader;
	int finishingtime;

	public Vertex(int v, String name, boolean explored) {
		this.name = name;
		this.v = v;
		this.explored = explored;
	}
}