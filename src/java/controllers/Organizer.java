package controllers;

import hibernate.mappings.*;
import hibernate.util.HibernateUtil;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
public class Organizer implements Serializable
{

    private Sport selectedSport;

    private Sport discipline_selectedSport;
    private SportDiscipline selectedDiscipline;
    private String disciplineName;
    private int minPlayers = 1, maxPlayers = 1;
    private String disciplineType;

    private Sport comp_selectedSport;
    private SportDiscipline comp_selectedDiscipline;
    private String comp_sex;
    private Date comp_startDate, comp_finishDate;
    private String comp_location;
    private List<Team> comp_chosenTeams = new ArrayList(0);
    private List<Sportsman> comp_chosenSportsmans = new ArrayList(0);
    private User comp_delegate;

    public Organizer()
    {

    }

    public void testAjax()
    {
	System.err.println("TEST AJAX");
    }

    public void refrechSelections(AjaxBehaviorEvent e)
    {
	String id = e.getComponent().getClientId();
	comp_chosenSportsmans = new ArrayList(0);
	comp_chosenTeams = new ArrayList(0);

	if (id.contains("id_comp_sport"))
	{
	    comp_selectedDiscipline = null;
	}
    }

    public List<Sport> getAvailableSports(String query)
    {
	List<Sport> sports = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    sports = session.createQuery("select s from Sport s where s not in (select rs.sport from RioSport rs) and s.name like \"" + query + "%\"").list();
	    tx.commit();

	    return sports;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return sports;
    }

    public List<SportDiscipline> getAvailableDisciplines(String query)
    {
	if (discipline_selectedSport == null)
	{
	    return null;
	}
	List<SportDiscipline> disciplines = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    disciplines = session.createQuery("select sd from SportDiscipline sd "
		    + "where sd.sport.idsport = " + discipline_selectedSport.getIdsport() + " "
		    + "and sd.name like \"" + query + "%\""
		    + "and sd not in (select rsd.sportDiscipline from RioSportDiscipline rsd)").list();
	    tx.commit();

	    return disciplines;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return disciplines;
    }

    public String addSport()
    {
	if (selectedSport == null)
	{
	    return null;
	}
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    RioSport rioSport = new RioSport(selectedSport);
	    session.save(rioSport);
	    tx.commit();
	    selectedSport = null;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }

	    System.err.println(e.getMessage());
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

    public String addDiscipline()
    {
	if (selectedDiscipline == null)
	{
	    return null;
	}
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    RioSportDiscipline rsd = new RioSportDiscipline(selectedDiscipline);
	    session.save(rsd);
	    tx.commit();
	    selectedDiscipline = null;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }

	    System.err.println(e.getMessage());
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

    public String addCompetition()
    {
	Competition competition = new Competition(
		comp_selectedSport,
		comp_selectedDiscipline,
		comp_sex,
		comp_startDate,
		comp_finishDate,
		comp_location,
		new HashSet(comp_chosenTeams),
		new HashSet(comp_chosenSportsmans),
		comp_delegate);

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    session.save(competition);
	    tx.commit();
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }

	    System.err.println(e.getMessage());
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

    public void discSportChange()
    {
	selectedDiscipline = null;
    }

    public List<Sport> getChosenSports(String query)
    {
	List<Sport> sports = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    sports = session.createQuery("select s from Sport s, RioSport rs where s.idsport = rs.sport.idsport").list();
	    tx.commit();

	    return sports;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return sports;
    }

    public List<SportDiscipline> getChosenDisciplines(String query)
    {
	List<SportDiscipline> disciplines = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    disciplines = session.createQuery("select sd from SportDiscipline sd, RioSportDiscipline rsd where "
		    + "sd.id = rsd.sportDiscipline.id and "
		    + "sd.sport.idsport = " + comp_selectedSport.getIdsport()).list();
	    tx.commit();

	    return disciplines;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return disciplines;
    }

    public List<Team> getTeamsForCompetition()
    {
	List<Team> teams = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    teams = session.createQuery("select t from Team t "
		    + "where t.sport.id = " + comp_selectedSport.getIdsport() + " and "
		    + "t.sex = '" + comp_sex + "'").list();
	    tx.commit();

	    return teams;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return teams;
    }

    public List<Sportsman> getSportsmansForCompetition()
    {
	List<Sportsman> sportsmans = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    sportsmans = session.createQuery(""
		    + "select s from Sportsman s "
		    + "join s.sportsmanDisciplines sd "
		    + "where sd.sportDiscipline.id = " + comp_selectedDiscipline.getId() + " and "
		    + "s.sex = '" + comp_sex + "'").list();
	    tx.commit();

	    return sportsmans;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return sportsmans;
    }

    public List<User> queryDelegates()
    {
	List<User> delegates = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    delegates = session.createQuery("from User where type = 'delegat'").list();
	    tx.commit();

	    return delegates;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	}
	catch (Exception e)
	{
	    System.err.println(e.getMessage());
	}
	finally
	{
	    session.close();
	}

	return delegates;
    }

    public List<Record> getRecords()
    {
	List<Record> records = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    records = session.createQuery("from Record").list();
	    tx.commit();

	    return records;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	    throw e;
	}
	finally
	{
	    session.close();
	}
    }

    public List<User> getUserRequests()
    {
	List<User> users = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    users = session.createQuery("from User where approved = false").list();
	    tx.commit();

	    return users;
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	    throw e;
	}
	finally
	{
	    session.close();
	}
    }
    
    public void approveUser(User user)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    user.setApproved(true);
	    tx = session.beginTransaction();
	    session.update(user);
	    tx.commit();
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    System.err.println(e.getMessage());
	    throw e;
	}
	finally
	{
	    session.close();
	}
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public User getComp_delegate()
    {
	return comp_delegate;
    }

    public void setComp_delegate(User comp_delegate)
    {
	this.comp_delegate = comp_delegate;
    }

    public List<Sportsman> getComp_chosenSportsmans()
    {
	return comp_chosenSportsmans;
    }

    public void setComp_chosenSportsmans(List<Sportsman> comp_chosenSportsmans)
    {
	this.comp_chosenSportsmans = comp_chosenSportsmans;
    }

    public List<Team> getComp_chosenTeams()
    {
	return comp_chosenTeams;
    }

    public void setComp_chosenTeams(List<Team> comp_chosenTeams)
    {
	this.comp_chosenTeams = comp_chosenTeams;
    }

    public String getComp_location()
    {
	return comp_location;
    }

    public void setComp_location(String comp_location)
    {
	this.comp_location = comp_location;
    }

    public Date getComp_startDate()
    {
	return comp_startDate;
    }

    public void setComp_startDate(Date comp_startDate)
    {
	this.comp_startDate = comp_startDate;
    }

    public Date getComp_finishDate()
    {
	return comp_finishDate;
    }

    public void setComp_finishDate(Date comp_finishDate)
    {
	this.comp_finishDate = comp_finishDate;
    }

    public String getComp_sex()
    {
	return comp_sex;
    }

    public void setComp_sex(String comp_sex)
    {
	this.comp_sex = comp_sex;
    }

    public Sport getComp_selectedSport()
    {
	return comp_selectedSport;
    }

    public void setComp_selectedSport(Sport comp_selectedSport)
    {
	this.comp_selectedSport = comp_selectedSport;
    }

    public SportDiscipline getComp_selectedDiscipline()
    {
	return comp_selectedDiscipline;
    }

    public void setComp_selectedDiscipline(SportDiscipline comp_selectedDiscipline)
    {
	this.comp_selectedDiscipline = comp_selectedDiscipline;
    }

    public Sport getSelectedSport()
    {
	return selectedSport;
    }

    public void setSelectedSport(Sport selectedSport)
    {
	this.selectedSport = selectedSport;
    }

    public Sport getDiscipline_selectedSport()
    {
	return discipline_selectedSport;
    }

    public void setDiscipline_selectedSport(Sport discipline_selectedSport)
    {
	this.discipline_selectedSport = discipline_selectedSport;
    }

    public SportDiscipline getSelectedDiscipline()
    {
	return selectedDiscipline;
    }

    public void setSelectedDiscipline(SportDiscipline selectedDiscipline)
    {
	this.selectedDiscipline = selectedDiscipline;
    }

    public String getDisciplineName()
    {
	return disciplineName;
    }

    public void setDisciplineName(String disciplineName)
    {
	this.disciplineName = disciplineName;
    }

    public int getMinPlayers()
    {
	return minPlayers;
    }

    public void setMinPlayers(int minPlayers)
    {
	this.minPlayers = minPlayers;
    }

    public int getMaxPlayers()
    {
	return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
	this.maxPlayers = maxPlayers;
    }

    public String getDisciplineType()
    {
	return disciplineType;
    }

    public void setDisciplineType(String disciplineType)
    {
	this.disciplineType = disciplineType;
    }

    // </editor-fold>
}
