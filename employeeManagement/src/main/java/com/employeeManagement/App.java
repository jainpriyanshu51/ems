package com.employeeManagement;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        
        String noOfEmpInDept = "SELECT d.departmentName, COUNT(e.department) " +
                "FROM Employee e " +
                "JOIN e.department d " +
                "GROUP BY d.departmentName";

        TypedQuery<Object[]> deptQuery = entityManager.createQuery(noOfEmpInDept, Object[].class);
        List<Object[]> deptResults = deptQuery.getResultList();

        for (Object[] result : deptResults) {
        	String departmentName = (String) result[0];
        	Long employeeCount = (Long) result[1];
        	System.out.println("Department Name: " + departmentName + ", Employee Count: " + employeeCount);
        }
        
        String noOfProjectsAssigned = "SELECT e.empId,e.empName, COUNT(p) " +
                "FROM Employee e " +
                "JOIN e.projects p " +
                "GROUP BY e.empId, e.empName "+
                "HAVING COUNT(p) > 1";
        
        TypedQuery<Object[]> query = entityManager.createQuery(noOfProjectsAssigned, Object[].class);
        List<Object[]> results = query.getResultList();
        for (Object[] row : results) {
        	Long empId = (Long) row[0];
            String empName = (String) row[1];
            Long projectCount = (Long) row[2];
            System.out.println("Id: " + empId + ", Employee Name: " + empName + ", Project Count: " + projectCount);
        }
        
       
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
