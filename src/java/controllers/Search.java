package controllers;

import hibernate.mappings.*;
import hibernate.util.HibernateUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Collection;

@ManagedBean
@ViewScoped
public class Search implements Serializable
{

    private List<Country> countries = null;
    private List<Sportsman> sportsmans = null;

    private String firstName;
    private String lastName;
    private Country country;
    private Sport sport;
    private SportDiscipline sportDiscipline;
    private String sex;
    private boolean medalist;

    public Search()
    {
	countries = queryCountries("");
    }

    public List<Country> queryCountries(String query)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<Country> countries = null;

	try
	{
	    tx = session.beginTransaction();
	    countries = session.createQuery("FROM Country where name like '" + query + "%'").list();
	    tx.commit();
	    
	    Collections.sort(countries);
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

	return countries;
    }

    public List<Sport> querySports(String query)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<Sport> sports = null;

	try
	{
	    tx = session.beginTransaction();
	    sports = session.createQuery("FROM Sport where name like '" + query + "%'").list();
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

	return sports;
    }

    public List<SportDiscipline> queryDisciplines(String query)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<SportDiscipline> disciplines = null;

	if (sport == null)
	{
	    return null;
	}

	try
	{
	    tx = session.beginTransaction();
	    disciplines = session.createQuery("FROM SportDiscipline sd "
		    + "where sd.name like '" + query + "%' and "
		    + "sd.sport.idsport = " + sport.getIdsport()).list();
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

	return disciplines;
    }

    public List<Country> getCountries()
    {
	return countries;
    }

    public String sarchSportsmans()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    String sportWhere = sport != null ? " and s.sport.idsport = " + sport.getIdsport() + " " : " ";
	    String sportDisciplineJoin = sportDiscipline != null ? " join s.sportsmanDisciplines sd " : " ";
	    String sportDisciplineWhere = sportDiscipline != null ? " and sd.sportDiscipline.id = " + sportDiscipline.getId() + " " : " ";
	    String sexWhere = sex != null ? " and s.sex like '%" + sex + "%' " : "";
	    String countryWhere = country != null ? " and s.country.id = " + country.getId() + " " : "";

	    tx = session.beginTransaction();
	    sportsmans = session.createQuery("select s FROM Sportsman s "
		    + " " + sportDisciplineJoin + " "
		    + " where "
		    + " s.firstName like '" + firstName + "%' and "
		    + " s.lastName like '" + lastName + "%' "
		    + " " + countryWhere + " "
		    + " " + sportWhere + " "
		    + " " + sexWhere + " "
		    + " " + sportDisciplineWhere + " "
		    + " order by s.firstName").list();
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

    public Country getCountry()
    {
	return country;
    }

    public void setCountry(Country country)
    {
	this.country = country;
    }

    public Sport getSport()
    {
	return sport;
    }

    public void setSport(Sport sport)
    {
	this.sport = sport;
    }

    public String getFirstName()
    {
	return firstName;
    }

    public void setFirstName(String firstName)
    {
	this.firstName = firstName;
    }

    public String getLastName()
    {
	return lastName;
    }

    public void setLastName(String lastName)
    {
	this.lastName = lastName;
    }

    public SportDiscipline getSportDiscipline()
    {
	return sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline)
    {
	this.sportDiscipline = sportDiscipline;
    }

    public String getSex()
    {
	return sex;
    }

    public void setSex(String sex)
    {
	this.sex = sex;
    }

    public boolean isMedalist()
    {
	return medalist;
    }

    public void setMedalist(boolean medalist)
    {
	this.medalist = medalist;
    }

    public List<Sportsman> getSportsmans()
    {
	return sportsmans;
    }

    public void setSportsmans(List<Sportsman> sportsmans)
    {
	this.sportsmans = sportsmans;
    }

}
