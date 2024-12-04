package pa9;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class GraphTest {

    @Test
    public void testBellmanFord() {
        Graph g = new GraphImplementation(5);
        g.addWeightedEdge(0, 1, 6);
        g.addWeightedEdge(0, 2, 7);
        g.addWeightedEdge(1, 2, 8);
        g.addWeightedEdge(1, 3, -4);
        g.addWeightedEdge(1, 4, 5);
        g.addWeightedEdge(2, 3, 9);
        g.addWeightedEdge(2, 4, -3);
        g.addWeightedEdge(3, 4, 7);
        g.addWeightedEdge(3, 0, 2);
        g.addWeightedEdge(4, 1, -2);

        int[] dist = g.shortestPath(0);
        assertEquals(0, dist[0]);
        assertEquals(2, dist[1]);
        assertEquals(7, dist[2]);
        assertEquals(-2, dist[3]);
        assertEquals(4, dist[4]);
    }

    @Test
    public void testNegativeCycle() {
        Graph g1 = new GraphImplementation(4);
        g1.addWeightedEdge(0, 1, 1);
        g1.addWeightedEdge(1, 2, -1);
        g1.addWeightedEdge(2, 3, -1);
        g1.addWeightedEdge(3, 0, -1);
        assertTrue(g1.hasNegativeCycle());

        Graph g2 = new GraphImplementation(4);
        g2.addWeightedEdge(0, 1, 1);
        g2.addWeightedEdge(1, 2, 2);
        g2.addWeightedEdge(2, 3, 3);
        assertFalse(g2.hasNegativeCycle());
    }

    @Test
    public void testMST() {
        Graph g = new GraphImplementation(5);
        g.addWeightedEdge(0, 1, 2);
        g.addWeightedEdge(0, 3, 6);
        g.addWeightedEdge(1, 2, 3);
        g.addWeightedEdge(1, 3, 8);
        g.addWeightedEdge(1, 4, 5);
        g.addWeightedEdge(2, 4, 7);
        g.addWeightedEdge(3, 4, 9);

        int[] kruskalMSTWeights = g.minimumSpanningTree();
        int expectedKruskalSum = 2 + 3 + 5 + 6; // Sum of the MST edges' weights
        assertEquals(expectedKruskalSum, Arrays.stream(kruskalMSTWeights).sum());

        int[] primMSTWeights = g.minimumSpanningTreePrim();
        assertEquals(expectedKruskalSum, Arrays.stream(primMSTWeights).sum());
    }
}
