package lk.poornima.training.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Employee {
    private static volatile Employee employee;

    /*private constructor to prevent others from instantiating this class*/
    private Employee(){
    if(employee !=null){
    throw new RuntimeException("Please use getEmployee method");
        }
    }

    public static Employee getEmployee(){
        if(employee ==null){
            synchronized (Employee.class){  /*synchronized block*/
                if(employee == null){
                    employee = new Employee(); /*creating an instance*/
                }
            }
        }
        return employee;
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection con=null;

        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/singletonDB", "root", "mysql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
