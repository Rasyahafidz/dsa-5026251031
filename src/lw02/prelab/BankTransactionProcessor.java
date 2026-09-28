package lw02.prelab;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class BankTransactionProcessor {
    public static void main(String[] args) {
        LinkedList<String[]> transactionList = new LinkedList<>();

        try {
            Scanner fileScanner = new Scanner(BankTransactionProcessor.class.getResourceAsStream("transactions.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] data = line.split("\\s+");
                    if (data.length == 3) {
                        transactionList.add(data);
                    }
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("File transactions.txt tidak ditemukan!");
            return;
        }

        LinkedList<String[]> customerList = new LinkedList<>();

        for (int i = 0; i < transactionList.size(); i++) {
            String[] tx = transactionList.get(i);
            String nama = tx[0];

            boolean sudahAda = false;
            for (int j = 0; j < customerList.size(); j++) {
                if (customerList.get(j)[0].equals(nama)) {
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                customerList.add(new String[]{nama, "0"});
            }
        }

        Queue<String[]> transactionQueue = new LinkedList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            transactionQueue.add(transactionList.get(i));
        }

        Stack<String[]> failedStack = new Stack<>();

        while (!transactionQueue.isEmpty()) {
            String[] tx = transactionQueue.poll();
            String namaCust = tx[0];
            String tipe = tx[1];
            int jumlah = Integer.parseInt(tx[2]);

            String[] targetCustomer = null;
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i)[0].equals(namaCust)) {
                    targetCustomer = customerList.get(i);
                    break;
                }
            }

            if (targetCustomer != null) {
                int saldoSekarang = Integer.parseInt(targetCustomer[1]);

                if (tipe.equals("DEPOSIT")) {
                    saldoSekarang += jumlah;
                    targetCustomer[1] = String.valueOf(saldoSekarang);
                } else if (tipe.equals("WITHDRAW")) {
                    if (jumlah > saldoSekarang) {
                        failedStack.push(tx);
                    } else {
                        saldoSekarang -= jumlah;
                        targetCustomer[1] = String.valueOf(saldoSekarang);
                    }
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (int i = 0; i < customerList.size(); i++) {
            String[] cust = customerList.get(i);
            System.out.println(cust[0] + " : " + cust[1]);
        }

        System.out.println();

        System.out.println("=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] failedTx = failedStack.pop();
            System.out.println(failedTx[0] + " " + failedTx[1] + " " + failedTx[2]);
        }
    }
}