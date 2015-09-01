/**
 *  Jim Gurgone
 *  CSC 403-510
 *  4.2.28
 */


public class MyEuler {
    private Stack<Integer> cycle = new Stack<Integer>();
    private boolean isEulerian = true;
    private Stack<Integer> TheStack = new Stack<Integer>();
    private Queue<Integer>[] adj;

    public MyEuler(Digraph G) {

        // create local copy of adjacency lists
        adj = (Queue<Integer>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new Queue<Integer>();
            for (int w : G.adj(v))
                adj[v].enqueue(w);
        }

        // find Eulerian tour
        TheStack.push(0);
        while (!TheStack.isEmpty()) {
            int s = TheStack.pop();
            cycle.push(s);
            int v = s;
            while (!adj[v].isEmpty()) {
                TheStack.push(v);
                v = adj[v].dequeue();
            }
            if (v != s) isEulerian = false;
        }

        // check if all edges have been used
        for (int v = 0; v < G.V(); v++)
            if (!adj[v].isEmpty()) isEulerian = false;
    }

    // return Eulerian tour, or null if no such tour
    public Iterable<Integer> tour() {
        if (!isEulerian) return null;
        return cycle;
    }

    // does the digraph have an Eulerian tour?
    public boolean isEulerian() {
        return isEulerian;
    }



    public static void main(String[] args) {
        
    	StdOut.println("Enter the number of Verticies: ");
    	int V = StdIn.readInt();
    	StdOut.println("Enter the number of Edges: ");
        int E = StdIn.readInt();

        // random graph of V vertices and approximately E edges
        // with indegree[v] = outdegree[v] for all vertices
        Digraph G = new Digraph(V);
        int[] indegree  = new int[V];
        int[] outdegree = new int[V];
        int deficit = 0;
        for (int i = 0; i < E - deficit/2; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) { i--; continue; }
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }

        while (deficit > 0) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) continue;
            if (outdegree[v] >= indegree[v]) continue;
            if (indegree[w]  >= outdegree[w]) continue;
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }

        StdOut.println(G);
        MyEuler euler = new MyEuler(G);
        if (euler.isEulerian()) {
            StdOut.print("The Elurian cycle is ");
        	for (int v : euler.tour()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No eulerian cycle exists.");
        }
    }

}

