package lw01.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (
            Main.class.getResourceAsStream("washes.txt")
        );

            if (!scanner.hasNextInt()) {
                scanner.close();
                return;
            }

            int n = scanner.nextInt();
            WashService[] washes = new WashService[n];

            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();

                WashService service;
                
                if (type.equals("MOTORCYCLE")) {
                    service = new MotorcycleWash(id, days);
                } else if (type.equals("CAR")) {
                    service = new CarWash(id, days);
                } else {
                    throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }

                
                int totalUnits = units;
                WashService baseService = service;

                washes[i] = new WashService(id, days) {
                    @Override
                    public int calculateCharge() {
                        return baseService.calculateCharge(totalUnits);
                    }

                    @Override
                    public String label() {
                        return baseService.label();
                    }
                };
            }

            scanner.close();

            for (WashService wash : washes) {
                System.out.println(wash.summary());
            }

        } 
    }

