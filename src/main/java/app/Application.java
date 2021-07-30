package app;

import DAO.PersonDAOImpl;
import connection.MyConnect;
import model.Person;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        PersonDAOImpl personDAO = new PersonDAOImpl();

        //добавление данных в people
        //Person vitalik = new Person("Vitalik","Svirida",27);
        //Person ivan = new Person("Ivan","Semenov",25);
        //System.out.println(personDAO.add(ivan));

        //обновление данных people по работе и роли
        /*Person person = personDAO.findById(13);
        System.out.println(person);
        System.out.println(personDAO.update(person,4,4));*/

        /*Person person = personDAO.findById(2);
        System.out.println(person);*/

        personDAO.findAll().stream()
                .forEach(System.out::println);



    }
}
