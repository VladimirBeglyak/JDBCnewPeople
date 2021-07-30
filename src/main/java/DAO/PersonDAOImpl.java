package DAO;

import connection.MyConnect;
import model.Job;
import model.Person;
import model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDAOImpl implements BaseDao<Person> {

    public static final String INSERT_VALUES_INTO_PEOPLE = "INSERT INTO people (firstName,lastName,age) VALUES (?,?,?)";
    public static final String UPDATE_PEOPLE_ROLE_AND_JOB = "UPDATE people SET role_id=?, job_id=? WHERE id=?";
    public static final String SELECT_FROM_PEOPLE_WHERE_ID = "SELECT * FROM people WHERE id=?";
    private static final String SELECT_FROM_PEOPLE = "SELECT p.id,p.firstName,p.lastName,p.age,r.id,r.name,j.id,j.position,j.salary FROM people p " +
            "JOIN role r ON r.id=p.role_id " +
            "JOIN job j ON j.id=p.job_id WHERE p.id=?";


    @Override
    public boolean add(Person person) {
        int rows = 0;
        try (Connection connect = MyConnect.getConnect()) {
            PreparedStatement preparedStatement = connect.prepareStatement(INSERT_VALUES_INTO_PEOPLE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getAge());
            rows = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                int keysInt = generatedKeys.getInt(1);
                System.out.println("Added person with id: " + keysInt);
            }

            preparedStatement.close();
            generatedKeys.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    @Override
    public boolean delete(Person person) {
        return false;
    }

    @Override
    public Person findById(int id) {

        try (Connection connect = MyConnect.getConnect()) {

            PreparedStatement preparedStatement = connect.prepareStatement(SELECT_FROM_PEOPLE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");

                int roleId = resultSet.getInt("r.id");
                String nameOfRole = resultSet.getString("name");

                int jobId = resultSet.getInt("j.id");
                String nameOfJob = resultSet.getString("position");
                int salary = resultSet.getInt("salary");

                return new Person(id, firstName, lastName, age, new Role(roleId,nameOfRole), new Job(jobId,nameOfJob, salary));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people=new ArrayList<>();
        try (Connection connect = MyConnect.getConnect()) {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM people");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                int role_id = resultSet.getInt("role_id");
                int job_id = resultSet.getInt("job_id");

                people.add(new Person(id,firstName,lastName,age));
            }


            preparedStatement.close();
            resultSet.close();

            return people;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Person person, int roleId, int jobId) {
        int rows = 0;
        try (Connection connect = MyConnect.getConnect()) {
            PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_PEOPLE_ROLE_AND_JOB);
            preparedStatement.setInt(1, roleId);
            preparedStatement.setInt(2, jobId);
            preparedStatement.setInt(3, person.getId());
            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }
}
