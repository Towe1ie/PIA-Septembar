/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.util;

import hibernate.mappings.Country;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Nemanja
 */
@FacesConverter("CountryConverter")
public class CountryConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
	List<Country> countries;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    countries = session.createQuery("FROM Country where id = " + value).list();
	    tx.commit();
	    
	    if (countries.size() > 0)
		return countries.get(0);
	    else
		return null;
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

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
	return ((Country)value).getId().toString();
    }
    
}
