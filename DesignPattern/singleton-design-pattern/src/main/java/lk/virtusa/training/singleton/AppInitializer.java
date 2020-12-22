package lk.virtusa.training.singleton;

import java.sql.Connection;

public class AppInitializer {
    public static void main(String[] args) {

        long start;
        long end;

        Employee employee = Employee.getEmployee(); //1st instance
        System.out.println(employee);

        start=System.currentTimeMillis();
        try {
            Connection connection = employee.getConnection(); //create a connection variable
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);

        Employee employee1 = Employee.getEmployee(); //2nd instance

        start=System.currentTimeMillis();

        try {
            Connection connection1 = employee1.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();

        System.out.println(end-start);
    }
}
