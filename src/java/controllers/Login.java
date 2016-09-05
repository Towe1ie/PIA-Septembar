package controllers;

import hibernate.mappings.Country;
import hibernate.mappings.Sportsman;
import hibernate.mappings.SportsmanDiscipline;
import hibernate.util.HibernateUtil;
import hibernate.mappings.User;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable
{

    public User user = new User();
    public User currUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().getOrDefault("user", null);

    private boolean AJAXemail = true, AJAXpassword = true, AJAXpasswordMatch = true;
    private String passwordReg3;
    UIComponent usernameReg, passwordReg1, passwordReg2;
    UIComponent username, password, email, type;

    private static final String EMAIL_PATTERN
	    = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private String delegat = "delegat";
    private String vodja = "vodja";

    private Session session;

    @PostConstruct
    public void destroy()
    {
	if (session != null && session.isOpen())
	{
	    session.close();
	}
    }
    
    public void emailAJAX()
    {
	matcher = pattern.matcher(user.getEmail());
	AJAXemail = true;
	if (!(matcher.matches()))
	{
	    AJAXemail = false;
	    user.setEmail("");
	    FacesContext.getCurrentInstance().addMessage(email.getClientId(), new FacesMessage("Los format mail-a!")); //AJAX!!
	}
    }

    public void typeAJAX()
    {
	if (!delegat.equals(user.getType()) && !vodja.equals(user.getType()))
	{
	    user.setType("");
	    FacesContext.getCurrentInstance().addMessage(type.getClientId(), new FacesMessage("Los format mail-a!")); //AJAX!!
	}
    }

    public void passwordAJAX(String pass, UIComponent comp)
    {

	String newPassword = pass;
	FacesContext context = FacesContext.getCurrentInstance();

	AJAXpassword = true;
	String errorPassword = "";
	boolean hasUpper = false;
	boolean hasLower = false;
	boolean hasDigit = false;

	if (newPassword.length() < 6 || newPassword.length() > 12)
	{
	    errorPassword += "6 < pass < 12!   ";
	    AJAXpassword = false;
	}

	if (!Character.isLetter(newPassword.charAt(0)))
	{
	    errorPassword += "First char must be letter!   ";
	    AJAXpassword = false;
	}

	for (Character c : newPassword.toCharArray())
	{
	    if (Character.isUpperCase(c))
	    {
		hasUpper = true;
	    }
	    else if (Character.isLowerCase(c))
	    {
		hasLower = true;
	    }
	    else if (Character.isDigit(c))
	    {
		hasDigit = true;
	    }
	}

	if (!hasDigit || !hasUpper || !hasLower)
	{
	    errorPassword += "1 small letter,1 big letter,1 one digit!   ";
	    AJAXpassword = false;
	}

	for (int i = 0; i < newPassword.length() - 2; ++i)
	{
	    if (newPassword.charAt(i) == newPassword.charAt(i + 1)
		    && newPassword.charAt(i) == newPassword.charAt(i + 2))
	    {
		errorPassword += "3 same succesive chars!";
		AJAXpassword = false;
	    }
	}
	if (AJAXpassword == false)
	{
	    FacesContext.getCurrentInstance().addMessage(comp.getClientId(), new FacesMessage(errorPassword)); //AJAX!!
	    newPassword = "";
	}
    }

    public void passwordMatchAJAX(String pass1, String pass2, UIComponent comp)
    {
	AJAXpasswordMatch = true;
	if (!pass1.equals(pass2))
	{
	    AJAXpasswordMatch = false;
	    pass2 = "";
	    FacesContext.getCurrentInstance().addMessage(comp.getClientId(), new FacesMessage("Ponovljeni password se ne poklapa!")); //AJAX!!
	}
    }
    
    public List<Country> getAllCountries(String query)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<Country> countries = null;
	
	try
	{
	    tx = session.beginTransaction();
	    countries = session.createQuery("from Country where name like \"" + query + "%\"").list();
	    tx.commit();
	}
	catch(HibernateException e)
	{
	    if (tx != null)
	    {
		tx.commit();
	    }
	    
	    System.err.println(e.getMessage());
	}
	catch(Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}
	
	return countries;
    }

    public String register()
    {
	if (!(AJAXemail && AJAXpassword && AJAXpasswordMatch))
	{
	    return null;
	}
	try
	{
	    session = HibernateUtil.getSessionFactory().openSession();
	    FacesContext context = FacesContext.getCurrentInstance();

	    // Provera da li postoji username
	    session.beginTransaction();
	    Query q = session.createQuery("FROM User u WHERE u.username='" + user.getUsername() + "'");
	    List<User> results = q.list();
	    session.getTransaction().commit();

	    if (results.size() > 0)
	    {
		context.addMessage(usernameReg.getClientId(), new FacesMessage("Username vec postoji!")); //AJAX!!
		session.close();
		user.setUsername("");
		return "";
	    }
	    if (!user.getPassword().equals(passwordReg3))
	    {
		context.addMessage(passwordReg2.getClientId(), new FacesMessage("Pass nije isti!")); //AJAX!!
		passwordReg3 = "";
		session.close();
		return "";
	    }

	    session.beginTransaction();
	    Query qu = session.createQuery("FROM User u WHERE u.email='" + user.getEmail() + "'");
	    List<User> results2 = qu.list();
	    session.getTransaction().commit();
	    if (results2.size() > 0)
	    {
		User novi = results2.get(0);
		if (novi.getUsername() != null)
		{
		    context.addMessage(email.getClientId(), new FacesMessage("Email vec postoji!")); //AJAX!!
		    user.setEmail("");
		    session.close();
		    return "";
		}
	    }

	    session.beginTransaction();
	    session.save(user);
	    session.getTransaction().commit();
	    context.getExternalContext().getSessionMap().remove("user");
	    context.getExternalContext().getSessionMap().put("user", currUser);

	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}
	return null;
    }

    public String logout()
    {
	FacesContext context = FacesContext.getCurrentInstance();
	context.getExternalContext().getSessionMap().remove("user");
	((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
	currUser = null;
	if (session != null && session.isOpen())
	{
	    session.close();
	}
	return "index";
    }

    public String login()
    {
	if (user.getUsername().equals("admin") && user.getPassword().equals("admin"))
	{
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
	    currUser = user;
	    return "organizer";
	}
	session = HibernateUtil.getSessionFactory().openSession();
	session.beginTransaction();
	FacesContext context = FacesContext.getCurrentInstance();

	Query query1 = session.createQuery("FROM User u WHERE u.username='" + user.getUsername()
		+ "' AND password='" + user.getPassword() + "'");
	List<User> results = query1.list();

	if ((results.size() > 0) && (results.get(0).getPassword().equals(user.getPassword())))
	{
	    user = results.get(0);
	    //odobren zahtev za registraciju?
	    /*Query query3 = session.createQuery("FROM Request r WHERE r.iduser='"+user.getIduser()+"'");
                List<Request> results1 = query3.list();
                if(results1.size() > 0) {
                    context.addMessage(password.getClientId(), new FacesMessage("Zahtev jos uvek neodobren!"));
                    user = new User();
                    currUser = null;
                    session.getTransaction().commit();
                    session.close(); 
                    return "";
                }*/
	    context.getExternalContext().getSessionMap().put("user", user);
	    //Hibernate.initialize(user.getCountry().getSportsmans());
//	    for (Sportsman s : user.getCountry().getSportsmans())
//	    {
//		Hibernate.initialize(s.getSportsmanDisciplines());
//		for (SportsmanDiscipline sd : s.getSportsmanDisciplines())
//		{
//		    Hibernate.initialize(sd.getSportDiscipline());
//		}
//	    }
	    session.getTransaction().commit();
	    //session.close();
	    currUser = user;

	    if (user.getType().equals("vodja"))
	    {
		return "leader";
	    }
	    else if (user.getType().equals("delegat"))
	    {
		return "delegate";
	    }
	    else
	    {
		return "index";
	    }
	}
	else if ((results.size() > 0) && (!results.get(0).getPassword().equals(user.getPassword())))
	{
	    context.addMessage(password.getClientId(), new FacesMessage("Pogresna lozinka!"));
	    user = new User();
	    currUser = null;
	}
	else
	{
	    Query query2 = session.createQuery("FROM User u WHERE u.username='" + user.getUsername() + "'");
	    List<User> result = query2.list();
	    if (result.size() > 0)
	    {
		context.addMessage(password.getClientId(), new FacesMessage("Pogresna lozinka!"));
	    }
	    else
	    {
		context.addMessage(username.getClientId(), new FacesMessage("Nepostojeci username!"));
	    }
	    user = new User();
	    currUser = null;
	}
	session.getTransaction().commit();
	session.close();
	return "";
    }

    public String goToHome()
    {
	if (currUser == null)
	{
	    return "index";
	}
	else if (currUser.getUsername().equals("admin"))
	{
	    return "organizer";
	}
	else if (currUser.getType().equals("vodja"))
	{
	    return "leader";
	}
	else if (currUser.getType().equals("delegat"))
	{
	    return "delegate";
	}
	else if (currUser.getType().equals("delegat"))
	{
	    return "index";
	}
	
	return "index";
    }

    public boolean isAJAXemail()
    {
	return AJAXemail;
    }

    public void setAJAXemail(boolean AJAXemail)
    {
	this.AJAXemail = AJAXemail;
    }

    public boolean isAJAXpassword()
    {
	return AJAXpassword;
    }

    public void setAJAXpassword(boolean AJAXpassword)
    {
	this.AJAXpassword = AJAXpassword;
    }

    public boolean isAJAXpasswordMatch()
    {
	return AJAXpasswordMatch;
    }

    public void setAJAXpasswordMatch(boolean AJAXpasswordMatch)
    {
	this.AJAXpasswordMatch = AJAXpasswordMatch;
    }

    public User getCurrUser()
    {
	return currUser;
    }

    public void setCurrUser(User currUser)
    {
	this.currUser = currUser;
    }

    public UIComponent getUsername()
    {
	return username;
    }

    public void setUsername(UIComponent username)
    {
	this.username = username;
    }

    public UIComponent getPassword()
    {
	return password;
    }

    public void setPassword(UIComponent password)
    {
	this.password = password;
    }

    public Session getSession()
    {
	return session;
    }

    public void setSession(Session session)
    {
	this.session = session;
    }

    public User getUser()
    {
	return user;
    }

    public void setUser(User user)
    {
	this.user = user;
    }

    public String getPasswordReg3()
    {
	return passwordReg3;
    }

    public void setPasswordReg3(String passwordReg3)
    {
	this.passwordReg3 = passwordReg3;
    }

    public UIComponent getUsernameReg()
    {
	return usernameReg;
    }

    public void setUsernameReg(UIComponent usernameReg)
    {
	this.usernameReg = usernameReg;
    }

    public UIComponent getPasswordReg1()
    {
	return passwordReg1;
    }

    public void setPasswordReg1(UIComponent passwordReg1)
    {
	this.passwordReg1 = passwordReg1;
    }

    public UIComponent getPasswordReg2()
    {
	return passwordReg2;
    }

    public void setPasswordReg2(UIComponent passwordReg2)
    {
	this.passwordReg2 = passwordReg2;
    }

    public UIComponent getEmail()
    {
	return email;
    }

    public void setEmail(UIComponent email)
    {
	this.email = email;
    }

    public UIComponent getType()
    {
	return type;
    }

    public void setType(UIComponent type)
    {
	this.type = type;
    }

    public Pattern getPattern()
    {
	return pattern;
    }

    public void setPattern(Pattern pattern)
    {
	this.pattern = pattern;
    }

    public Matcher getMatcher()
    {
	return matcher;
    }

    public void setMatcher(Matcher matcher)
    {
	this.matcher = matcher;
    }

    public String getDelegat()
    {
	return delegat;
    }

    public void setDelegat(String delegat)
    {
	this.delegat = delegat;
    }

    public String getVodja()
    {
	return vodja;
    }

    public void setVodja(String vodja)
    {
	this.vodja = vodja;
    }

}
