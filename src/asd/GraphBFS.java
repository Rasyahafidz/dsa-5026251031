package asd;

import java.util.*;

public class GraphBFS {

    private Map<Integer, List<Integer>> graph = new LinkedHashMap<>();

    // Menambahkan edge pada undirected graph
    public void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
    }

    // BFS untuk menampilkan isi node
    public void bfs(int start) {

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        // Tandai node awal dan masukkan ke queue
        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {

            // Mengambil node dari depan queue
            int vertex = queue.poll();

            // Menampilkan isi node
            System.out.print(vertex + " ");

            // Mengunjungi seluruh tetangga
            for (int neighbor : graph.get(vertex)) {

                if (!visited.contains(neighbor)) {

                    // Tandai sebelum dimasukkan ke queue
                    visited.add(neighbor);

                    queue.offer(neighbor);
                }
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {

        GraphBFS g = new GraphBFS();

        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);

        System.out.print("BFS: ");
        g.bfs(3);
    }
}

