package controllers;

import hibernate.mappings.Sport;
import hibernate.mappings.SportDiscipline;
import hibernate.mappings.Sportsman;
import hibernate.mappings.SportsmanDiscipline;
import hibernate.mappings.User;
import hibernate.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
@ViewScoped
public class LeaderSearch implements Serializable
{
    // Pretraga i breadcrumb

    private MenuModel menuModel;
    private int searchType = 0; // 0 sportovi, 1 discipline, 2 sportisti
    private Sport search_chosenSport;
    private SportDiscipline search_chosenDiscipline;
    
    public LeaderSearch()
    {
	menuModel = new DefaultMenuModel();
	
	DefaultMenuItem sportsItem = new DefaultMenuItem("Sportovi");
	sportsItem.setCommand("#{leaderSearch.breadCrumbAction(0)}");
	menuModel.addElement(sportsItem);
    }
    
    public void breadCrumbAction(int searchType)
    {
	int size = menuModel.getElements().size();
	
	for (int i = searchType; i < size - 1; i++)
	{
	    menuModel.getElements().remove(searchType + 1);
	}
	this.searchType = searchType;
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("id_form");
    }
    
    public List<Object[]> queryRegisteredSports()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
	
	List<Object[]> sports = null;
	
	try
	{
	    tx = session.beginTransaction();
	    sports = session.createQuery("select sp, count(*) "
		    + "from User u "
		    + "join u.country c "
		    + "join c.sportsmans s "
		    + "join s.sport sp "
		    + "where u.iduser = " + user.getIduser() + " "
		    + "group by sp.id").list();
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
	
	return sports;
    }
    
    public void goToDisciplines(Sport sport)
    {
	if (menuModel.getElements().size() < 2)
	{
	    search_chosenSport = sport;
	    searchType = 1;
	    
	    DefaultMenuItem disciplinesItem = new DefaultMenuItem("Discipline");
	    disciplinesItem.setCommand("#{leaderSearch.breadCrumbAction(1)}");
	    disciplinesItem.setId("1");
	    menuModel.addElement(disciplinesItem);

	    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("id_form");
	}
    }
    
    public void goToSportsmans(SportDiscipline sportDiscipline)
    {
	if (menuModel.getElements().size() < 3)
	{
	    search_chosenDiscipline = sportDiscipline;
	    searchType = 2;
	    
	    DefaultMenuItem sportsmansItem = new DefaultMenuItem("Sportisti");
	    sportsmansItem.setCommand("#{leaderSearch.breadCrumbAction(2)}");
	    sportsmansItem.setId("2");
	    
	    menuModel.addElement(sportsmansItem);
	}
    }
    
    public Set<SportDiscipline> getDisciplinesForSport()
    {
	Set<Sportsman> sportsmans = getCurrentUser().getCountry().getSportsmans();
	Set<SportDiscipline> ret = new HashSet();
	
	for (Sportsman s : sportsmans)
	{
	    for (SportsmanDiscipline sd : s.getSportsmanDisciplines())
	    {
		if (sd.getSportDiscipline().getSport().getIdsport().intValue() == search_chosenSport.getIdsport().intValue())
		{
		    ret.add(sd.getSportDiscipline());
		}
	    }
	}
	
	return ret;
    }
    
    public List<Sportsman> getSportsmansForDiscipline()
    {
	List<Sportsman> sportsmans = null;
	
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	try
	{
	    tx = session.beginTransaction();
	    sportsmans = session.createQuery("select sd.sportsman from SportsmanDiscipline sd "
		    + "where sd.sportsman.country.id = " + getCurrentUser().getCountry().getId() + " and "
		    + "sd.sportDiscipline = " + search_chosenDiscipline.getId()).list();
	    tx.commit();
	}
	catch(HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
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
	
	return sportsmans;
    }
    
    public User getCurrentUser()
    {
	return (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public MenuModel getMenuModel()
    {
	return menuModel;
    }
    
    public void setMenuModel(MenuModel menuModel)
    {
	this.menuModel = menuModel;
    }
    
    public int getSearchType()
    {
	return searchType;
    }
    
    public void setSearchType(int searchType)
    {
	this.searchType = searchType;
    }
    
    public Sport getSearch_chosenSport()
    {
	return search_chosenSport;
    }
    
    public void setSearch_chosenSport(Sport search_chosenSport)
    {
	this.search_chosenSport = search_chosenSport;
    }
    
    public SportDiscipline getSearch_chosenDiscipline()
    {
	return search_chosenDiscipline;
    }
    
    public void setSearch_chosenDiscipline(SportDiscipline search_chosenDiscipline)
    {
	this.search_chosenDiscipline = search_chosenDiscipline;
    }

    // </editor-fold>
}
