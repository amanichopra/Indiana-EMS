/**
 * A class representing a patient.
 * 
 * @author TODO
 */
public class Patient {
	// instance variables
	// TODO ATTENTION: CODE NEEDED HERE
	// declare instance variables
	// -----
	String name;
	int arrival_time;
	int urgency;
	
	// constructor
	public Patient(String name, int arrival_time, int urgency) {
		// TODO ATTENTION: CODE NEEDED HERE
		// initialize instance variables
		// -----
		this.name = name;
		this.arrival_time = arrival_time;
		this.urgency = urgency;
	}
	
	// functions
	/**
	 * @return this patient's arrival time
	 */
	public int arrival_time() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return this patient's arrival time
		return arrival_time;
		// -----
	}
	
	/**
	 * @return this patient's urgency
	 */
	public int urgency() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return this patient's urgency
		return urgency;
		//-----
	}
	
	/**
	 * @param time - current simulation time
	 * @return wait time of this patient
	 */
	public int wait_time(int time){
		// TODO ATTENTION: CODE NEEDED HERE
		// return this patient's wait time
		return time - arrival_time;
		// -----
	}
}
