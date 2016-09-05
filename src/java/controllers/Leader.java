package controllers;

import hibernate.mappings.*;
import hibernate.util.HibernateUtil;
import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.hibernate.*;

@ManagedBean
@SessionScoped
public class Leader implements Serializable
{

    // Ekipni sportovi
    private List<Sport> teamSports;

    private String team_firstName, team_lastName;
    private String teamText = null;
    private List<Sportsman> team = new ArrayList();
    UIComponent ui_teamTextArea;
    private Sport team_sport;
    private SportDiscipline team_selectedDiscipline;
    private String team_sex;

    // Individualni sportovi
    private List<Sport> individualSports;

    private String ind_firstName, ind_lastName;
    private Sport ind_selectedSport;
    private List<SportDiscipline> ind_selectedDisciplines = new ArrayList();
    private String ind_sex;

    public void testAjax()
    {
	System.err.println("TEST AJAX");
    }

    public Leader()
    {
//	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",
//		new User(new Country("Serbia", "asd", null, null), "NemanjaLu", "Olovcica1", "Nemanja", "Lucic", "asd@asd.asd", "vodja"));

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    teamSports = session.createQuery("select s from Sport s, SportDiscipline sd "
		    + "where s = sd.sport and sd.disciplineType = 'ekipni'").list();
	    tx.commit();

	    tx = session.beginTransaction();
	    individualSports = session.createQuery("select distinct s from Sport s, SportDiscipline sd "
		    + "where s = sd.sport and sd.disciplineType = 'individualni'").list();
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
    }

    public List<SportDiscipline> getSportDisciplines(boolean individual)
    {
	if (team_sport == null && !individual || ind_selectedSport == null && individual)
	{
	    return null;
	}
	List<SportDiscipline> disciplines = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    if (!individual)
	    {
		disciplines = session.createQuery("select sd from SportDiscipline sd "
			+ "where sd.sport.idsport = " + team_sport.getIdsport() + " "
			+ "and sd.disciplineType = 'ekipni'").list();
	    }
	    else
	    {
		disciplines = session.createQuery("select sd from SportDiscipline sd "
			+ "where sd.sport.idsport = " + ind_selectedSport.getIdsport() + " "
			+ "and sd.disciplineType = 'individualni'").list();
	    }
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

    // Ekipni sportovi
    public void addToTeam()
    {
	User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

	Sportsman s = new Sportsman(user.getCountry(), team_sport, team_firstName, team_lastName, null, null, team_sex);

	team.add(s);
	if (teamText == null)
	{
	    teamText = team_firstName + " " + team_lastName + "\n";
	}
	else
	{
	    teamText += team_firstName + " " + team_lastName + "\n";
	}
	team_firstName = null;
	team_lastName = null;
    }

    public String registerTeam()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

//	if (team.size() < team_selectedDiscipline.getMinPlayers()
//		|| team.size() > team_selectedDiscipline.getMaxPlayers())
//	{
//	    FacesContext.getCurrentInstance().addMessage(
//		    ui_teamTextArea.getClientId(),
//		    new FacesMessage("Minimalno " + team_selectedDiscipline.getMinPlayers() + ", "
//			    + "maksimalno " + team_selectedDiscipline.getMaxPlayers() + " igraca"));
//	    return null;
//	}
	try
	{
	    Team t = new Team(getCurrentUser().getCountry(), team_sport, null, team_sex);
	    
	    tx = session.beginTransaction();
	    for (Sportsman s : team)
	    {
		SportsmanDiscipline sd = new SportsmanDiscipline(team_selectedDiscipline, s);

		if (s.getSportsmanDisciplines() == null)
		{
		    s.setSportsmanDisciplines(new HashSet());
		}
		s.getSportsmanDisciplines().add(sd);

		if (team_selectedDiscipline.getSportsmanDisciplines() == null)
		{
		    team_selectedDiscipline.setSportsmanDisciplines(new HashSet());
		}
		team_selectedDiscipline.getSportsmanDisciplines().add(sd);

		User user = getCurrentUser();
		Country country = user.getCountry();
		Set<Sportsman> sportsmans = country.getSportsmans();
		sportsmans.add(s);

		s.setSex(team_sex);
		s.setTeam(t);

		
		session.save(s);
		
	    }
	    
	    tx.commit();

	    team_firstName = null;
	    team_lastName = null;
	    team.clear();
	    team_sport = null;
	    teamText = null;
	    team_selectedDiscipline = null;
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

    // Individualni sportovi
    public String registerIndividualSportsman()
    {
	if (ind_selectedDisciplines.isEmpty())
	{
	    FacesContext.getCurrentInstance().addMessage(
		    "id_ind_form:id_ind_sport_discp",
		    new FacesMessage("Morate selektovati barem jednu disciplinu!"));
	    return null;
	}
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
	Sportsman s = new Sportsman(user.getCountry(), ind_selectedSport, ind_firstName, ind_lastName, null, null, ind_sex);

	try
	{
	    if (s.getSportsmanDisciplines() == null)
	    {
		s.setSportsmanDisciplines(new HashSet());
	    }

	    for (SportDiscipline discipline : ind_selectedDisciplines)
	    {
		SportsmanDiscipline sd = new SportsmanDiscipline(discipline, s);
		s.getSportsmanDisciplines().add(sd);

		if (discipline.getSportsmanDisciplines() == null)
		{
		    discipline.setSportsmanDisciplines(new HashSet());
		}
		discipline.getSportsmanDisciplines().add(sd);
	    }

	    user.getCountry().getSportsmans().add(s);

	    tx = session.beginTransaction();
	    session.save(s);
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

    // Pretraga
    public Set<Sportsman> getSportsmans()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	Set<Sportsman> sportsmans = null;

	User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

	try
	{
	    tx = session.beginTransaction();
	    sportsmans = user.getCountry().getSportsmans();
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

	return sportsmans;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public String getInd_sex()
    {
	return ind_sex;
    }

    public void setInd_sex(String ind_sex)
    {
	this.ind_sex = ind_sex;
    }

    public String getTeam_sex()
    {
	return team_sex;
    }

    public void setTeam_sex(String team_sex)
    {
	this.team_sex = team_sex;
    }

    public List<Sport> getTeamSports()
    {
	return teamSports;
    }

    public User getCurrentUser()
    {
	return (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public void setTeamSports(List<Sport> teamSports)
    {
	this.teamSports = teamSports;
    }

    public String getTeam_firstName()
    {
	return team_firstName;
    }

    public void setTeam_firstName(String team_firstName)
    {
	this.team_firstName = team_firstName;
    }

    public String getTeam_lastName()
    {
	return team_lastName;
    }

    public void setTeam_lastName(String team_lastName)
    {
	this.team_lastName = team_lastName;
    }

    public String getTeamText()
    {
	return teamText;
    }

    public void setTeamText(String teamText)
    {
	this.teamText = teamText;
    }

    public Sport getTeam_sport()
    {
	return team_sport;
    }

    public void setTeam_sport(Sport team_sport)
    {
	this.team_sport = team_sport;
    }

    public SportDiscipline getTeam_selectedDiscipline()
    {
	return team_selectedDiscipline;
    }

    public void setTeam_selectedDiscipline(SportDiscipline team_selectedDiscipline)
    {
	this.team_selectedDiscipline = team_selectedDiscipline;
    }

    public UIComponent getUi_teamTextArea()
    {
	return ui_teamTextArea;
    }

    public void setUi_teamTextArea(UIComponent ui_teamTextArea)
    {
	this.ui_teamTextArea = ui_teamTextArea;
    }

    public String getInd_firstName()
    {
	return ind_firstName;
    }

    public void setInd_firstName(String ind_firstName)
    {
	this.ind_firstName = ind_firstName;
    }

    public String getInd_lastName()
    {
	return ind_lastName;
    }

    public void setInd_lastName(String ind_lastName)
    {
	this.ind_lastName = ind_lastName;
    }

    public Sport getInd_selectedSport()
    {
	return ind_selectedSport;
    }

    public void setInd_selectedSport(Sport ind_selectedSport)
    {
	this.ind_selectedSport = ind_selectedSport;
    }

    public List<SportDiscipline> getInd_selectedDisciplines()
    {
	return ind_selectedDisciplines;
    }

    public void setInd_selectedDisciplines(List<SportDiscipline> ind_selectedDisciplines)
    {
	this.ind_selectedDisciplines = ind_selectedDisciplines;
    }

    public List<Sport> getIndividualSports()
    {
	return individualSports;
    }

    public void setIndividualSports(List<Sport> individualSports)
    {
	this.individualSports = individualSports;
    }

    // </editor-fold>
}
