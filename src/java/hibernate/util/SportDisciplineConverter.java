/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.util;

import hibernate.mappings.Sport;
import hibernate.mappings.SportDiscipline;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Nemanja
 */
@FacesConverter("SportDisciplineConverter")
public class SportDisciplineConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
	List<SportDiscipline> disciplines;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    
	    if (component instanceof UIInput)
	    {
		UIInput i = (UIInput)component;
		Object o = i.getValue();
		
		if (o instanceof SportDiscipline)
		{
		    System.out.println("YES");
		}
	    }
	    Map<String, Object> m = component.getAttributes();
	    Object o = component.getAttributes().get("value");
	    
	    disciplines = session.createQuery("FROM SportDiscipline where id = " + value).list();
	    tx.commit();

	    if (disciplines.size() > 0)
	    {
		return disciplines.get(0);
	    }
	    else
	    {
		return null;
	    }
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
	return ((SportDiscipline)value).getId().toString();
    }

}
