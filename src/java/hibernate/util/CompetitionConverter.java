/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.util;

import hibernate.mappings.Competition;
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
@FacesConverter("CompetitionConverter")
public class CompetitionConverter implements Converter
{    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
	List<Competition> competitions;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    competitions = session.createQuery("FROM Competition where id = " + value).list();
	    tx.commit();
	    
	    if (competitions.size() > 0)
		return competitions.get(0);
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
	if (value == null)
	    return null;
	return ((Competition)value).getId().toString();
    }
    
}
