import java.util.ArrayList;


public class Test {

	public static void main(String args[]) {
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		Vertex a = new Vertex (1, "a");
		Vertex b = new Vertex (2, "b");
		Vertex c = new Vertex (3, "c");
		Vertex d = new Vertex (4, "d");
		Vertex e = new Vertex (5, "e");
		Vertex f = new Vertex (6, "f");
		Vertex g = new Vertex (7, "g");
		Vertex h = new Vertex (8, "h");
		Vertex i = new Vertex (9, "i");

		Edge ab = new Edge(a, b);
		Edge bc = new Edge(b, c);
		Edge ca = new Edge(c, a);
		Edge cd = new Edge(c, d);
		Edge de = new Edge(d, e);
		Edge ef = new Edge(e, f);
		Edge fd = new Edge(f, d);
		Edge fg = new Edge(f, g);
		Edge gh = new Edge(g, h);
		Edge hi = new Edge(h, i);
		Edge ig = new Edge(i, g);

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


		Graph graph = new Graph(edgeList);

		KosarajuTwoPass k = new KosarajuTwoPass();

		k.computeScc(graph);
	}
}