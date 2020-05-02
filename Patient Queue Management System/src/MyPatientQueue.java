/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author TODO
 */
public class MyPatientQueue{
	// instance variables
	// TODO ATTENTION: CODE NEEDED HERE
	// declare instance variables
	// -----
	Patient[] patients;
	int head;
	int tail;
	int size;
	int capacity;

	// constructor
	public MyPatientQueue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// initialize instance variables
		// -----
		patients = new Patient[7];
		head = 0;
		tail = 0;
		size = 0;
		capacity = 7;
	}

	// functions
	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return the number of patients in the queue
		return size;
		// -----
	}

	/**
	 * shrink array by 1/2
	 */
	public void downsize_array() {
		if (capacity > 7 && size <= capacity / 4.0) { // shrink array by 1/2 if length >= 7 and length <= 1/4 of
			// capacity
			Patient[] new_patients = new Patient[capacity / 2]; // downsize array by half
			for (int i = 0; i < size; i++) { // copy elements to new array
				new_patients[i] = patients[head];
				head = (head + 1) % capacity;
			}
			patients = new_patients;
			head = 0;
			tail = size;
			capacity = capacity / 2; // update capacity
		}
	}

	/**
	 * double size of array
	 */
	public void upsize_array() {
		if (size == capacity) {
			Patient[] new_patients = new Patient[2 * capacity]; // make new patient [] twice size of prev capacity
			for (int i = 0; i < size; i++) { // copy elements to new array
				new_patients[i] = patients[head];
				head = (head + 1) % capacity;
			}
			patients = new_patients;
			head = 0;
			tail = size;
			capacity = 2 * capacity; // update capacity

		}
	}

	/**
	 * add patient to end of queue.
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add patient to end of queue
		// resize array, if needed
		// -----
		upsize_array(); // check if queue is full before adding element

		patients[tail] = p;
		tail = (tail + 1) % capacity; // update tail pointer after adding element
		size++;

		//downsize_array(); // check if queue size is too small relative to capacity


	}

	/**
	 * remove and return next patient from the queue
	 * @return patient at front of queue, null if queue is empty
	 */
	public Patient dequeue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return the patient at the head of the queue
		// resize array, if needed
		if (size == 0) {
			return null;
		}

		Patient p = patients[head];
		patients[head] = null;
		head = (head + 1) % capacity; // update head pointer after removing element
		size--;

		downsize_array(); // check if queue size is too small relative to capacity
		return p;
		// -----
	}

	/**
	 * return, but do not remove, the patient at index i
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// return, but do not remove, the patient at index i
		if (i < 0 || i >= size) {
			return null;
		}
		return patients[(head + i) % capacity];
		// -----
	}

	/**
	 * add patient to front of queue
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add Patient p to front of queue
		// resize array, if needed
		// -----

		upsize_array(); // check if queue is full before adding element

		Patient[] temp = new Patient[capacity]; // temp queue to copy elements

		for (int i = 0; i < size; i++) { // copy elements from original queue to temp queue with opening in front
			temp[i + 1] = patients[head];
			head = (head + 1) % capacity;
		}

		patients = temp; // update queue

		patients[0] = p;
		size++;
		head = 0;
		tail = size;

//		head--;
//		if (head < 0) {
//			head = capacity - 1;
//		}
//		patients[head] = p;
//		size++;




	}

	/**
	 * remove and return patient at index i from queue
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return Patient at index i from queue
		// shift patients down to fill hole left by removed patient
		// resize array, if needed
		if (i < 0 || i >= size) {
			return null;
		}

		Patient p = patients[(head + i) % capacity];
		patients[(head + i) % capacity] = null;
		size--;

		Patient[] temp = new Patient[capacity];

		int j = 0;

		while (j < size) {
			if (patients[head] != null) {
				temp[j] = patients[head];
				j++;
			}
			head = (head + 1) % capacity;
		}

		patients = temp;
		head = 0;
		tail = size;

		downsize_array(); // check if array too small

		return p;
	}

//	public static void main(String[] args) {
//		MyPatientQueue Q = new MyPatientQueue();
//
//		for (int i = 1; i < 2; i++) {
//			// enqueue i patients
//			for (int j = 0; j < 8; j++) {
//				Patient p = new Patient("a" + j, j, j);
//				Q.enqueue(p);
//			}
//
////			for (int j = 50; j < 55; j++) {
////				Patient p = new Patient("a" + j, j, j);
////				Q.push(p);
////			}
////
//			for (int j = 50; j < 52; j++) {
//				Q.dequeue();
//			}
//			//System.out.println(Q.get(1).name);
//			System.out.println(Q.dequeue(10));
//			System.out.println();
////			// enqueue and dequeue a single patient many times
////			for (int j = 0; j < 3; j++) {
////				Patient p = new Patient("b" + j, j, j);
////				Q.enqueue(p);
////				Q.dequeue();
////			}
//			//Q.dequeue(5);
//
//		}
//
//		for (int i = 0; i < Q.patients.length; i++) {
//			try {
//				System.out.println(Q.patients[i].name);
//			} catch (NullPointerException e) {
//				System.out.println(Q.patients[i]);
//			}
//
//		}
//	}

}
