package se.coredev.pattern;

public abstract class User
{
	private String username;
	private String firstName;
	private String lastName;

	private User(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	private void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	private void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public static UserBuilder builder(String username)
	{
		return new User.UserBuilder(username);
	}
	
	
	public static final class UserBuilder
	{
		private final User user;
		
		private UserBuilder(String username)
		{
			this.user = new User(username){};
		}
		
		public UserBuilder firstName(String firstName)
		{
			user.setFirstName(firstName);
			return this;
		}
		
		public UserBuilder lastName(String lastName)
		{
			user.setLastName(lastName);
			return this;
		}		
		
		public User build()
		{
			return user;
		}
	}
	
}

