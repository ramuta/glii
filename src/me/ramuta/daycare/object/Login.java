package me.ramuta.daycare.object;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {

	public static String Email;
	public static String Password;

	public Login()
	{
		
	}
	
	public Login( String email, String password)
	{
		this.Email = email;
		this.Password = password;
	}
}
