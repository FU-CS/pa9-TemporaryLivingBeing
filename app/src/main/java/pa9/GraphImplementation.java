package pa9;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GraphImplementation implements Graph {
    private final int vertices;
    private final List<Edge> edges;

    public GraphImplementation(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    @Override
    public void addWeightedEdge(int v, int w, int weight) {
        edges.add(new Edge(v, w, weight));
    }

    @Override
    public boolean hasNegativeCycle() {
        int[] distance = new int[vertices];
        for (int i = 0; i < vertices; i++) distance[i] = Integer.MAX_VALUE;
        distance[0] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : edges) {
                if (distance[edge.v] != Integer.MAX_VALUE &&
                        distance[edge.v] + edge.weight < distance[edge.w]) {
                    distance[edge.w] = distance[edge.v] + edge.weight;
                }
            }
        }

        for (Edge edge : edges) {
            if (distance[edge.v] != Integer.MAX_VALUE &&
                    distance[edge.v] + edge.weight < distance[edge.w]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int[] shortestPath(int v) {
        int[] distance = new int[vertices];
        for (int i = 0; i < vertices; i++) distance[i] = Integer.MAX_VALUE;
        distance[v] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : edges) {
                if (distance[edge.v] != Integer.MAX_VALUE &&
                        distance[edge.v] + edge.weight < distance[edge.w]) {
                    distance[edge.w] = distance[edge.v] + edge.weight;
                }
            }
        }

        return distance;
    }

    @Override
    public int[] minimumSpanningTree() {
        sortEdgesByWeight();
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) parent[i] = i;

        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int root1 = find(parent, edge.v);
            int root2 = find(parent, edge.w);

            if (root1 != root2) {
                mst.add(edge);
                parent[root1] = root2;
            }
        }

        int[] result = new int[mst.size()];
        for (int i = 0; i < mst.size(); i++) {
            result[i] = mst.get(i).weight;
        }
        return result;
    }

    @Override
    public int[] minimumSpanningTreePrim() {
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        boolean[] visited = new boolean[vertices];
        List<Edge> mst = new ArrayList<>();
        addEdges(0, pq, visited);

        while (!pq.isEmpty() && mst.size() < vertices - 1) {
            Edge edge = pq.poll();
            if (visited[edge.w]) continue;

            mst.add(edge);
            addEdges(edge.w, pq, visited);
        }

        int[] result = new int[mst.size()];
        for (int i = 0; i < mst.size(); i++) {
            result[i] = mst.get(i).weight;
        }
        return result;
    }

    private void addEdges(int v, PriorityQueue<Edge> pq, boolean[] visited) {
        visited[v] = true;
        for (Edge edge : edges) {
            if (edge.v == v && !visited[edge.w]) {
                pq.add(edge);
            }
        }
    }

    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    private void sortEdgesByWeight() {
        for (int i = 0; i < edges.size() - 1; i++) {
            for (int j = 0; j < edges.size() - i - 1; j++) {
                if (edges.get(j).weight > edges.get(j + 1).weight) {
                    Edge temp = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temp);
                }
            }
        }
    }

    private static class Edge {
        int v, w, weight;

        Edge(int v, int w, int weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }
    }
}
