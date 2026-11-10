package asd;

import java.util.*;

public class DirectedGraphCycle {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public void addEdge(int u, int v) {
        // Memeriksa apakah key u sudah ada dalam map graph.
        // Jika belum ada maka tetangga u adalah ArrayList kosong, 
        // lalu tambahkan v sebagai tetangga.
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);

        // Memeriksa apakah key v sudah ada dalam map graph.
        // Jika belum ada maka tetangga v adalah ArrayList kosong.
        graph.computeIfAbsent(v, k -> new ArrayList<>());
    }

    public boolean hasCycle() {
        Map<Integer, Integer> state = new HashMap<>();

        for (int vertex : graph.keySet()) {
            state.put(vertex, 0);
        }

        for (int vertex : graph.keySet()) {
            if (state.get(vertex) == 0) {
                if (dfs(vertex, state)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(int vertex, Map<Integer, Integer> state) {
        // State 1: Node sedang dikunjungi (in recursion stack / Gray)
        state.put(vertex, 1);

        for (int neighbor : graph.get(vertex)) {
            int neighborState = state.get(neighbor);

            // Jika tetangga memiliki state 1, berarti ada back edge -> siklus terdeteksi!
            if (neighborState == 1) {
                return true;
            }

            // Jika belum dikunjungi (state 0), lanjutkan DFS
            if (neighborState == 0) {
                if (dfs(neighbor, state)) {
                    return true;
                }
            }
        }

        // State 2: Node selesai diproses (completely visited / Black)
        state.put(vertex, 2);
        return false;
    }

    public static void main(String[] args) {
        DirectedGraphCycle g1 = new DirectedGraphCycle();
        DirectedGraphCycle g2 = new DirectedGraphCycle();
        DirectedGraphCycle g3 = new DirectedGraphCycle();

        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        g1.addEdge(3, 1);
        System.out.println("Cyclic g1: " + g1.hasCycle()); // Output: true

        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 3);
        g2.addEdge(1, 4);
        g2.addEdge(2, 5);   
        System.out.println("Cyclic g2: " + g2.hasCycle()); // Output: false

        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 5);
        g3.addEdge(5, 4);
        g3.addEdge(4, 2); // Membuat siklus: 2 -> 3 -> 5 -> 4 -> 2
        g3.addEdge(5, 6);
        System.out.println("Cyclic g3: " + g3.hasCycle()); // Output: true
    }
}