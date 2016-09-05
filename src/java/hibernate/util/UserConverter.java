/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.util;

import hibernate.mappings.User;
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
@FacesConverter("UserConverter")
public class UserConverter implements Converter
{    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
	List<User> users;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    users = session.createQuery("FROM User where iduser = " + value).list();
	    tx.commit();
	    
	    if (users.size() > 0)
		return users.get(0);
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
	return ((User)value).getIduser().toString();
    }
    
}
