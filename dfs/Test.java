import java.util.ArrayList;
public class Test {
	public static void main(String args[]) {
		Vertex one = new Vertex(1);
		Vertex two = new Vertex(2);
		Vertex three = new Vertex(3);
		Vertex four = new Vertex(4);
		Vertex five = new Vertex(5);
		Vertex six = new Vertex(6);

		Edge a = new Edge(one, two);
		Edge b = new Edge(one, three);
		Edge c = new Edge(three, two);
		Edge d = new Edge(two, six);
		Edge e = new Edge(three, four);
		Edge f = new Edge(four, five);

		ArrayList<Edge> list = new ArrayList<Edge>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		list.add(f);

		Graph g = new Graph(list);

		Dfs df = new Dfs();
		df.dfs(g);
	}
}