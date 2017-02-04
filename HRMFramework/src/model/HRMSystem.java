package model;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To keep track of the employees within a company, we are going to develop a HRM application.
 * The system records the name, salary and a unique number for each employee. Additionally,
 * the system keeps track of the competences of the employees. An employee can master a
 * competence on three different levels: 0 means that the person is very bad at it, 1 means
 * that the person is reasonably well at it, and 2 means that the person is very good at it.
 * An example competence could be ‘Programming’. Within the company, there are ‘regular’
 * employees and managers. A manager is also an employee, but (s)he manages one or more
 * employees.
 */
public class HRMSystem {

	ArrayList<Employee> employees = new ArrayList<>();
	
	/**
	 * Add a manager to the system. The name must be unique. If not, an
	 * IllegalArgumentException is thrown.
	 * @param name the name of the manager
	 * @param salary the salary of the manager
	 * @return the (automatically generated) unique number assigned to this manager
	 * @throws IllegalArgumentException if an employee with the given name already exists
	 */
	public int addManager(String name, int salary) throws IllegalArgumentException {

		if (employeeExists(name)) {
			throw new IllegalArgumentException("Name already exists");
		}

		Manager manager = new Manager(name, salary);
		employees.add(manager);
		return employees.size();
	}

	/**
	 * Check to see if a certain employee is a manger. If no such employee exists,
	 * an IllegalArgumentException is thrown.
	 * @param name the name of the employee
	 * @return a boolean indicating whether the employee with the given name is a manager
	 * @throws IllegalArgumentException if no such employee exists
	 */
	private boolean isManager(String name) throws IllegalArgumentException{
		Employee emp = getEmployee(name);
		if (emp == null) {
			throw new IllegalArgumentException(name + " is not a manager");
		}
		return emp instanceof Manager;
	}
	
	/**
	 * Assigns the manager with the name managerName to be the manager of the employee
	 * with name employeeName.
	 *
	 * @param managerName name of the manager
	 * @param employeeName name of the employee
	 * @throws IllegalArgumentException if a name does not exist in the system or if managerName is
	 * not a manager
	 */
	public void assignManager(String managerName, String employeeName) throws IllegalArgumentException {

		if (!isManager(managerName)) {
			throw new IllegalArgumentException(managerName + " no such manager");
		}
		Manager mngr = (Manager) getEmployee(managerName);

		Employee emp = getEmployee(employeeName);
		if (emp == null) {
			throw new IllegalArgumentException("Employee does not exist");
		}
		mngr.addEmployeeToList(emp);

	}
	
	/**
	 * Add an employee to the system.
	 * The name must be unique.
	 *
	 * @param name the name of the employee
	 * @param salary the salary of the employee
	 * @return the (automatically generated) unique number assigned to this employee
	 * @throws IllegalArgumentException if an employee with the given name already exists
	 */
	public int addEmployee(String name, int salary) throws IllegalArgumentException {
		if (employeeExists(name)) {
			throw new IllegalArgumentException(name + " is already exist");
		}
		Employee employee = new Employee(name, salary);
		employees.add(employee);
		return employees.size();
	}
	
	/**
	 * Helper function to check if an employee exists in the system.
	 * 
	 * @param name the name to check
	 * @return true if the employee exists, false otherwise
	 */
	private boolean employeeExists(String name) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper function to retrieve an Employee object based on the name
	 * of an employee.
	 * 
	 * @param name the name of the employee
	 * @return the Employee object, or null if there is no employee with the given name.
	 */
	private Employee getEmployee(String name) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getName().equals(name)) {
				return employees.get(i);
			}
		}
		return null;
	}

	/**
	 * Retrieve the total number of employees (including managers).
	 * 
	 * @return the total number of employees
	 */
	public int numberOfEmployees() {
		return employees.size();
	}

	/**
	 * Retrieve the total number of managers
	 * 
	 * @return the total number of managers
	 * @see HRMSystem#numberOfEmployees()
	 */
	public int numberOfManagers() {
		int count = 0;
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i) instanceof Manager) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Retrieve the number of employees that are managed by a certain manager.
	 * 
	 * @param 	managerName the name of the manager
	 * @return 	the number of employees managed by this manager
	 * @throws 	IllegalArgumentException if there is no employee with the name managerName
	 * or the employee is not a manager
	 */
	public int numberOfEmployeesManagedByManager(String managerName) throws IllegalArgumentException {
		if (!isManager(managerName)) {
			throw new  IllegalArgumentException();
		}
		Manager mngr = (Manager) getEmployee(managerName);
		return mngr.getNumOfEmployees();
	}

	/**
	 * Read employees from a text file. Lines in the file need to meet several constraints:
	 * Fields are separated using commas. Lines starting with a hash symbol (#) and empty
	 * lines need to be skipped. Valid lines consist of a name and a salary. If someone is
	 * a manager, the line continues with the word manager, followed by the names of the
	 * employees the person manages.
	 *
	 * @param filename name of the file that needs to be read
	 * @return the number of persons that have been read (and added to the system) successfully,
	 * or -1 if the file could not be read.
	 */
	public int readEmployeesFromFile(String filename) {
		File f = new File(filename);
		Scanner in;
		try {
			in = new Scanner(f);
		} catch (FileNotFoundException fe) {
			return -1;
		}
		int count = 0;
		int lines = 0;
		while (in.hasNextLine()) {
			lines++;
			String line = in.nextLine();
			if (line.isEmpty()) {
				continue;
			}
			if ((line.charAt(0) == '#')) {
				continue;
			}
			try {
				employees.add(processLine(line));
				count++;

			} catch (LineException le) {
				System.out.println("ERROR on line " + lines + ": " + le.getMessage());
			}
		}
		return count;
	}
	
	/**
	 * Helper method to read a line from the file.
	 * 
	 * @param line the contents of the line that was read from the file
	 * @return the employee that was created based on the line
	 * @throws LineException if the line does not meet the constraints
	 * @see HRMSystem#readEmployeesFromFile(String)
	 */
	private Employee processLine(String line) throws LineException {

		Scanner sc = new Scanner(line);
		sc.useDelimiter(",");
		if (!sc.hasNext()) {
			throw new LineException("No name");
		}
		String name = sc.next();

		if (!sc.hasNext()) {
			throw new LineException("Salary is missing");
		}
		if (!sc.hasNextInt()) {
			throw new LineException("Salary must be a number");
		}
		int salary = sc.nextInt();

		if (!sc.hasNext()) {
			Employee employee = new Employee(name, salary);
			return employee;
		}
		String name2 = sc.next();

		if (!name2.equals("manager")) {
			throw new LineException("Wrong name");
		}

		Manager manager = new Manager(name, salary);
		while (sc.hasNext()) {
			String name3 = sc.next();
			if (employeeExists(name3)) {
				manager.addEmployeeToList(getEmployee(name3));
			} else {
				throw new LineException("No such employee");
			}
		}

		return manager;
	}



	/**
	 * Adds a competence to an employee with the given name. If the employee does not exist,
	 * an IllegalArgumentException is thrown. If the competence level is less than 0, the value 0
	 * is used. If the competence level is higher than 2, the value 2 is used.
	 *
	 * @param employeeName name of the employee
	 * @param competenceName name of the competence
	 * @param competenceLevel competence level (0, 1, or 2)
	 * @throws IllegalArgumentException if there is no such employee
	 */
	public void addCompetence(String employeeName, String competenceName, int competenceLevel) throws IllegalArgumentException {

		Employee emp = getEmployee(employeeName);
		if (emp == null) {
			throw  new IllegalArgumentException("No such employee");
		}

		Competence competence = new Competence(competenceLevel, competenceName);

		if (competenceLevel < 0) {
			competenceLevel = 0;
		}
		if (competenceLevel > 2) {
			competenceLevel = 2;
		}
		emp.addCompitanceToList(competence);
	}

	/**
	 * Writes a report that shows all employees and their competences. The file must be structured as follows
	 * <pre>
	 * {@code
	 * ### HRM System Summary ###
	 * Evert (1) has competences: Programming (2), Cooperating (0)
	 * Peter (2) has competences: Research (2), Programming (2)
	 * Ruud (3) has no competences
	 * }
	 * </pre>
	 * @param fileName the name of the file to write to
	 * @throws FileNotFoundException if the output file could not be created
	 */
	public void writeReportToFile(String fileName) throws FileNotFoundException {
		PrintWriter f = new PrintWriter(fileName);
		f.println("### HRM System Summary ###");
		for (int i = 0; i < employees.size(); i++) {
			f.println(employees.get(i).competenceToString(i + 1));
		}
		f.close();
	}

	public String toString() {
		String rc = "Summary (" + employees.size() + " employee(s))\n\n";
		for (int i = 0; i < employees.size(); i++) {
			rc += employees.get(i).employeeToString(i + 1);
			rc += "\n";
		}
		return rc;
	}
}
