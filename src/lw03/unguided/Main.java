package lw03.unguided;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    
        Set<String> registeredStudents = new HashSet<>();
        
        Set<String> checkedInStudents = new HashSet<>();
        
        int rejectedAttempts = 0;

        Scanner input1 = new Scanner(Main.class.getResourceAsStream("registrations.txt"));
        
        while (input1.hasNextLine()) {
            String studentId = input1.nextLine().trim();
            if (!studentId.isEmpty()) {
                registeredStudents.add(studentId); 
            }
        }
        input1.close();

        System.out.println("===== Event Check-In Results =====");
        
        Scanner input2 = new Scanner(Main.class.getResourceAsStream("checkins.txt"));
      
        while (input2.hasNextLine()) {
            String studentId = input2.nextLine().trim();
            
            if (studentId.isEmpty()) {
                continue;
            }

            if (!registeredStudents.contains(studentId)) {
                System.out.println(studentId + ": Rejected (not registered)");
                rejectedAttempts++;
            } else if (checkedInStudents.contains(studentId)) {
                System.out.println(studentId + ": Rejected (already checked in)");
                rejectedAttempts++;
            } else {
                System.out.println(studentId + ": Checked in");
                checkedInStudents.add(studentId);
            }
        }
        input2.close();

        int totalRegistered = registeredStudents.size();
        int totalCheckedIn = checkedInStudents.size();
        int absentStudents = totalRegistered - totalCheckedIn;

        System.out.println();

        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + totalRegistered);
        System.out.println("Successful check-ins: " + totalCheckedIn);
        System.out.println("Absent students: " + absentStudents);
        System.out.println("Rejected attempts: " + rejectedAttempts);
    }
}