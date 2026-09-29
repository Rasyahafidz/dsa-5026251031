package lw02.unguided;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    
        LinkedList<String[]> orderList = new LinkedList<>();

        java.io.InputStream ordersStream = Main.class.getResourceAsStream("orders.txt");
        if (ordersStream == null) {
            System.out.println("File orders.txt tidak ditemukan.");
            return;
        }

        try (Scanner scanner = new Scanner(ordersStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    orderList.add(parts);
                }
            }
        }

        LinkedList<String[]> foodStock = new LinkedList<>();
        foodStock.add(new String[]{"Bakso", "2"});
        foodStock.add(new String[]{"Sate", "1"});
        foodStock.add(new String[]{"Soto", "2"});

        LinkedList<String[]> drinkStock = new LinkedList<>();
        drinkStock.add(new String[]{"EsTeh", "4"});
        drinkStock.add(new String[]{"EsJeruk", "2"});

        Queue<String[]> orderQueue = new LinkedList<>();
        while (!orderList.isEmpty()) {
            orderQueue.add(orderList.poll());
        }

        LinkedList<String[]> successfulOrders = new LinkedList<>();
        Stack<String[]> failedOrders = new Stack<>();

        while (!orderQueue.isEmpty()) {
            String[] order = orderQueue.poll();
            String name = order[0];
            String food = order[1];
            String drink = order[2];
            String table = order[3];

            boolean foodAvailable = true;
            boolean drinkAvailable = true;

            if (!food.equals("-")) {
                foodAvailable = false;
                for (String[] f : foodStock) {
                    if (f[0].equals(food) && Integer.parseInt(f[1]) > 0) {
                        foodAvailable = true;
                        break;
                    }
                }
            }

            if (!drink.equals("-")) {
                drinkAvailable = false;
                for (String[] d : drinkStock) {
                    if (d[0].equals(drink) && Integer.parseInt(d[1]) > 0) {
                        drinkAvailable = true;
                        break;
                    }
                }
            }

            if (foodAvailable && drinkAvailable) {

                if (!food.equals("-")) {
                    for (String[] f : foodStock) {
                        if (f[0].equals(food)) {
                            int currentStock = Integer.parseInt(f[1]);
                            f[1] = String.valueOf(currentStock - 1);
                            break;
                        }
                    }
                }
                // Kurangi stok minuman
                if (!drink.equals("-")) {
                    for (String[] d : drinkStock) {
                        if (d[0].equals(drink)) {
                            int currentStock = Integer.parseInt(d[1]);
                            d[1] = String.valueOf(currentStock - 1);
                            break;
                        }
                    }
                }
                successfulOrders.add(order);
            } else {
                failedOrders.push(order);
            }
        }

        System.out.println("=== Successfully Processed Orders ===");
        for (String[] order : successfulOrders) {
            System.out.println(order[0] + " " + order[1] + " " + order[2] + " " + order[3]);
        }

        System.out.println();

        System.out.println("=== Remaining Food Stock ===");
        for (String[] f : foodStock) {
            String formatName = f[0].equals("Sate") ? "Sate " : f[0];
            System.out.println(formatName + " : " + f[1]);
        }

        System.out.println();

        System.out.println("=== Remaining Drink Stock ===");
        for (String[] d : drinkStock) {
            String formatName = d[0].equals("EsJeruk") ? "EsJeruk " : d[0];
            System.out.println(formatName + " : " + d[1]);
        }

        System.out.println(); 

        System.out.println("=== Failed Orders ===");
        while (!failedOrders.isEmpty()) {
            String[] order = failedOrders.pop();
            System.out.println(order[0] + " " + order[1] + " " + order[2] + " " + order[3]);
        }
    }
}

