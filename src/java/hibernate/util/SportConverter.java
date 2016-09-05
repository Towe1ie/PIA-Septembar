package hibernate.util;

import hibernate.mappings.Sport;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@FacesConverter("SportConverter")
public class SportConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
	List<Sport> sports;

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    sports = session.createQuery("FROM Sport where idsport = " + value).list();
	    tx.commit();
	    
	    if (sports.size() > 0)
		return sports.get(0);
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
	return ((Sport)value).getIdsport().toString();
    }
    
}
