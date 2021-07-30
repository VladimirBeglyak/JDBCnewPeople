package DAO;

import model.Person;

import java.util.Scanner;

public class Console {
    public static Person getDataFromConsole(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first_name");
        String firstName = scanner.nextLine();
        System.out.println("Enter the last_name");
        String lastName = scanner.nextLine();
        System.out.println("Enter the age");
        int age = scanner.nextInt();
        return new Person(firstName,lastName,age);
    }
}
