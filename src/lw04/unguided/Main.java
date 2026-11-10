package lw04.unguided;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(Main.class.getResourceAsStream("parcels.txt"));

        int n = input.nextInt();
        int[] parcels = new int[n];

        for (int i = 0; i < n; i++) {
            parcels[i] = input.nextInt();
        }

        int m = input.nextInt();
        int[] targets = new int[m];

        for (int i = 0; i < m; i++) {
            targets[i] = input.nextInt();
        }

        int[] afterTwoPasses = new int[n];

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - 1 - i; j++) {
                if (parcels[j] < parcels[j + 1]) {
                    int temp = parcels[j];
                    parcels[j] = parcels[j + 1];
                    parcels[j + 1] = temp;
                }
            }

            if (i == 1) {
                for (int j = 0; j < n; j++) {
                    afterTwoPasses[j] = parcels[j];
                }
            }
        }

        System.out.println("==== After 2 Passes ====");

        for (int i = 0; i < n; i++) {
            System.out.print(afterTwoPasses[i]);

            if (i < n - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();
        System.out.println();

        System.out.println("==== Fully Sorted ====");

        for (int i = 0; i < n; i++) {
            System.out.print(parcels[i]);

            if (i < n - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();
        System.out.println();

        System.out.println("==== Search Results ====");

        for (int i = 0; i < m; i++) {
            int low = 0;
            int high = n - 1;
            int index = -1;
            int steps = 0;

            while (low <= high) {
                int mid = (low + high) / 2;
                steps++;

                if (parcels[mid] == targets[i]) {
                    index = mid;
                    break;
                } else if (targets[i] > parcels[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            if (index != -1) {
                System.out.println(targets[i] + ": FOUND at index " + index + " (steps: " + steps + ")");
            } else {
                System.out.println(targets[i] + ": NOT FOUND (steps: " + steps + ")");
            }
        }

        input.close();
    }
}

