package serverSide;

class User {
		int userType;
		String firstName, lastName, email;
		int id;
		
		public User(int type, String first, String last, String email, int id) {
			this.userType = type;
			this.firstName = first;
			this.lastName = last;
			this.email = email;
			this.id = id;
		}
		public int getUserType() {
			return userType;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getEmail() {
			return email;
		}
		public int getId() {
			return id;
		}
}
