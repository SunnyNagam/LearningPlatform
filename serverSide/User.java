package serverSide;

import format.Communicate;

class User {
		int userType;
		String firstName, lastName, email;
		int id;
		/**
		 * initialized fields to params
		 * @param type
		 * @param first
		 * @param last
		 * @param email
		 * @param id
		 */
		public User(int type, String first, String last, String email, int id) {
			this.userType = type;
			this.firstName = first;
			this.lastName = last;
			this.email = email;
			this.id = id;
		}
		/**
		 * @return userType
		 */
		public int getUserType() {
			return userType;
		}
		/**
		 * @return firstName
		 */
		public String getFirstName() {
			return firstName;
		}
		/**
		 * @return lastName
		 */
		public String getLastName() {
			return lastName;
		}
		/**
		 * @return email
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * @return id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @return the string representation of the object
		 */
		@Override
		public String toString() {
			return String.format("%s %s %s %s %s", id, firstName, lastName, email, (userType==Communicate.PROFESSOR)?"PROF":"STUD");
		}
}
