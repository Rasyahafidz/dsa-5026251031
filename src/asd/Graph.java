package asd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph extends JPanel {
    private int V;                       // Jumlah vertex
    private List<Integer>[] adj;         // Daftar adjacency
    private double[] posX;               // Koordinat X tiap vertex
    private double[] posY;               // Koordinat Y tiap vertex
    private int width = 800;             // Lebar area tampilan
    private int height = 600;            // Tinggi area tampilan
    private boolean coordinatesReady = false;

    // Constructor
    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        posX = new double[V];
        posY = new double[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Tambah edge (graph tidak berarah)
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // BFS + koordinat (X,Y)
    public void BFS(int start) {
        boolean[] visited = new boolean[V];
        int[] level = new int[V];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        level[start] = 0;
        queue.offer(start);

        List<List<Integer>> levelNodes = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            levelNodes.add(new LinkedList<>());
        }

        int maxLevel = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            int currentLevel = level[node];
            maxLevel = Math.max(maxLevel, currentLevel);
            levelNodes.get(currentLevel).add(node);

            for (int neighbor : adj[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    level[neighbor] = currentLevel + 1;
                    queue.offer(neighbor);
                }
            }
        }

        // Hitung koordinat proporsional
        double yStep = (double) height / (maxLevel + 2);
        for (int lvl = 0; lvl <= maxLevel; lvl++) {
            List<Integer> nodes = levelNodes.get(lvl);
            if (nodes.isEmpty()) continue;

            double xStep = (double) width / (nodes.size() + 1);
            double yPos = (lvl + 1) * yStep;

            for (int i = 0; i < nodes.size(); i++) {
                int node = nodes.get(i);
                posX[node] = (i + 1) * xStep;
                posY[node] = yPos;
            }
        }

        coordinatesReady = true;

        // Cetak hasil BFS
        System.out.println("BFS Traversal dari node " + start + ":");
        for (int lvl = 0; lvl <= maxLevel; lvl++) {
            for (int node : levelNodes.get(lvl)) {
                System.out.print(node + " ");
            }
        }
        System.out.println("\n");
    }

    // Menampilkan grafik
    public void displayGraph() {
        if (!coordinatesReady) {
            System.out.println("Koordinat belum dihitung! Jalankan BFS() terlebih dahulu.");
            return;
        }

        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    // Menggambar graph di panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!coordinatesReady) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        // Gambar edge (garis antar vertex)
        g2.setColor(Color.GRAY);
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj[i]) {
                if (i < neighbor) { // agar tidak digambar dua kali
                    int x1 = (int) posX[i];
                    int y1 = (int) posY[i];
                    int x2 = (int) posX[neighbor];
                    int y2 = (int) posY[neighbor];
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }

        // Gambar vertex (lingkaran)
        int radius = 20;
        for (int i = 0; i < V; i++) {
            int x = (int) posX[i] - radius / 2;
            int y = (int) posY[i] - radius / 2;
            g2.setColor(Color.CYAN);
            g2.fillOval(x - radius / 2, y - radius / 2, radius * 2, radius * 2);
            g2.setColor(Color.BLACK);
            g2.drawOval(x - radius / 2, y - radius / 2, radius * 2, radius * 2);
            g2.drawString(String.valueOf(i), (int) posX[i] - 5, (int) posY[i] + 5);
        }
    }

    // Main
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);

        g.BFS(5);        // Hitung posisi vertex
        g.displayGraph(); // Tampilkan visualisasi
    }
}



