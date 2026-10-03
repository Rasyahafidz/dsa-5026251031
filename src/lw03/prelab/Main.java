package lw03.prelab;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("===== Problem 1 =====");

        List<String> playlist = new ArrayList<>();
        Scanner input1 = new Scanner(Main.class.getResourceAsStream("playlist.txt"));

        while (input1.hasNextLine()) {
            String line = input1.nextLine();

            if (line.startsWith("ADD ")) {
                String song = line.substring(4);

                playlist.add(song);
            }

            else if (line.startsWith("INSERT ")) {
                String[] data = line.split(" ", 3);

                int index = Integer.parseInt(data[1]);
                String song = data[2];

                playlist.add(index, song);
            }

            else if (line.startsWith("REMOVE ")) {
                String song = line.substring(7);

                playlist.remove(song);
            }
        }

        input1.close();

        System.out.println("Total songs: " + playlist.size());

        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        System.out.println();
        
        System.out.println("===== Problem 2 =====");

        Set<String> participants = new LinkedHashSet<>();

        int duplicate = 0;

        Scanner input2 = new Scanner(Main.class.getResourceAsStream("participants.txt"));

        while (input2.hasNextLine()) {
            String name = input2.nextLine();

            if (participants.contains(name)) {
                duplicate++;
            } else {
                participants.add(name);
            }
        }

        input2.close();

        System.out.println("Unique participants: " + participants.size());

        int nomor = 1;

        for (String name : participants) {
            System.out.println(nomor + ". " + name);
            nomor++;
        }

        System.out.println("Duplicate registrations: " + duplicate);

        System.out.println();

        System.out.println("===== Problem 3 =====");

        Map<String, Integer> inventory = new LinkedHashMap<>();

        int failedSales = 0;

        Scanner input3 = new Scanner(Main.class.getResourceAsStream("inventory.txt"));

        while (input3.hasNextLine()) {
            String line = input3.nextLine();

            String[] data = line.split(" ");

            String type = data[0];
            String product = data[1];
            int quantity = Integer.parseInt(data[2]);

            if (type.equals("ADD")) {

                if (inventory.containsKey(product)) {

                    int stockLama = inventory.get(product);
                    int stockBaru = stockLama + quantity;

                    inventory.put(product, stockBaru);

                } else {

                    inventory.put(product, quantity);
                }

            }

            else if (type.equals("SELL")) {

                if (inventory.containsKey(product)) {

                    int stock = inventory.get(product);

                    if (stock >= quantity) {

                        int stockBaru = stock - quantity;

                        inventory.put(product, stockBaru);

                    } else {

                        failedSales++;
                    }

                } else {

                    failedSales++;
                }
            }
        }

        input3.close();

        for (String product : inventory.keySet()) {
            System.out.println(product + ": " + inventory.get(product));
        }

        System.out.println("Failed sales: " + failedSales);
    }
}
