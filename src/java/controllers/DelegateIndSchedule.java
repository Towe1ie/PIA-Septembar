package controllers;

import hibernate.mappings.*;
import hibernate.util.HibernateUtil;
import java.util.*;
import javax.faces.bean.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
@ViewScoped
public class DelegateIndSchedule
{

    private static final Random RAND = new Random();
    private Competition competition;
    private List<QualifyingGroup> currentGroups;

    private List<QualifyingGroup> getGroups()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<QualifyingGroup> groups = null;

	try
	{
	    tx = session.beginTransaction();
	    groups = session.createQuery("from QualifyingGroup g "
		    + "where g.competition.id = " + competition.getId() + " and "
		    + "g.is_elimination = true").list();
	    tx.commit();

	    if (groups.isEmpty())
	    {
		tx = session.beginTransaction();
		groups = session.createQuery("from QualifyingGroup g "
			+ "where g.competition.id = " + competition.getId() + " and "
			+ "g.is_elimination = false").list();
		tx.commit();
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

	return groups;
    }

    public void onLoad()
    {
	currentGroups = getGroups();
    }

    public void generateGroups()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    List<Sportsman> sportsmans = new ArrayList(competition.getSportsmans());

	    int groupsCnt = (sportsmans.size() + 7) / 8;
	    int sportsmansPerGroup = (sportsmans.size() + groupsCnt - 1) / groupsCnt;
	    List<QualifyingGroup> groups = new ArrayList();

	    int currSportsman = 0;
	    for (int i = 0; i < groupsCnt; i++)
	    {
		QualifyingGroup group = new QualifyingGroup(competition, null, i + "", null);

		for (int j = 0; j < sportsmansPerGroup && currSportsman < sportsmans.size(); currSportsman++, j++)
		{
		    group.getSportsman_qualifyingGroups().add(new Sportsman_QualifyingGroup(sportsmans.get(currSportsman), group, ""));
		    if (competition.getSport().getName().equals("Streljastvo"))
		    {
			group.getSportsman_qualifyingGroups().last().setResult("0");
		    }
		}

		if (groupsCnt == 1)
		{
		    group.setIs_elimination(Boolean.TRUE);
		}

		if (competition.getSport().getName().equals("Streljastvo"))
		{
		    group.setRounds(new TreeSet());
		    for (int j = 0; j < 6; j++)
		    {
			group.getRounds().add(new Round(group, false, null, j));
		    }
		}

		tx = session.beginTransaction();
		session.save(group);
		tx.commit();
	    }
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    throw e;
	}
	finally
	{
	    session.close();
	}
    }

    public void updateGroupDateTime(QualifyingGroup group)
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    tx = session.beginTransaction();
	    session.update(group);
	    tx.commit();
	}
	catch (HibernateException e)
	{
	    if (tx != null)
	    {
		tx.rollback();
	    }
	    throw e;
	}
	finally
	{
	    session.close();
	}
    }

    public void updateResults(QualifyingGroup group)
    {
	boolean do_finish = true;

	if (competition.getSport().getName().equals("Streljastvo"))
	{
	    Round currRound = null;
	    for (Round round : group.getRounds())
	    {
		if (!round.isIs_finished())
		{
		    currRound = round;
		    break;
		}
	    }

	    for (Sportsman_QualifyingGroup sg : group.getSportsman_qualifyingGroups())
	    {
		sg.setResult(Integer.parseInt(sg.getResult()) + Integer.parseInt(sg.getTmp_result()) + "");
		sg.setTmp_result("");
	    }

	    currRound.setIs_finished(true);
	    updateGroupDateTime(group);

	    if (currRound.getNum() == 5)
	    {
		group.setIs_finished(true);

	    }
	    else
	    {
		do_finish = false;
	    }
	}

	if (!group.getIs_elimination())
	{
	    if (do_finish)
	    {
		group.setIs_finished(true);
		updateGroupDateTime(group);

		boolean allfinished = true;
		List<QualifyingGroup> groups = getGroups();
		for (QualifyingGroup g : groups)
		{
		    if (!g.getIs_finished())
		    {
			allfinished = false;
			break;
		    }
		}

		if (allfinished)
		{
		    generateFinalGroup();
		    getGroups();
		}
	    }
	}
	else if (do_finish)
	{
	    group.setIs_finished(true);

	    ArrayList<Sportsman_QualifyingGroup> sportsmans = new ArrayList(group.getSportsman_qualifyingGroups());
	    Collections.sort(sportsmans);

	    sportsmans.get(0).getSportsman().getCountry().incGold();
	    sportsmans.get(1).getSportsman().getCountry().incSilver();
	    sportsmans.get(2).getSportsman().getCountry().incBronze();

	    updateGroupDateTime(group);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try
	    {
		tx = session.beginTransaction();
		session.update(sportsmans.get(0).getSportsman().getCountry());
		session.update(sportsmans.get(1).getSportsman().getCountry());
		session.update(sportsmans.get(2).getSportsman().getCountry());
		tx.commit();
	    }
	    catch (HibernateException e)
	    {
		if (tx != null)
		{
		    tx.rollback();
		}
		throw e;
	    }
	    finally
	    {
		session.close();
	    }
	}
    }

    public boolean isShooting()
    {
	return competition.getSport().getName().equals("Streljastvo");
    }

    public void generateFinalGroup()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<Sportsman_QualifyingGroup> sportmans = null;

	try
	{
	    tx = session.beginTransaction();
	    sportmans = session.createQuery("select sqg from Sportsman_QualifyingGroup sqg "
		    + "join sqg.qualifyingGroup g "
		    + "join g.competition c "
		    + "where c.id = " + competition.getId()).list();
	    tx.commit();

	    Collections.sort(sportmans, new Comparator<Sportsman_QualifyingGroup>()
	    {
		@Override
		public int compare(Sportsman_QualifyingGroup sg1, Sportsman_QualifyingGroup sg2)
		{
		    String[] time1_tokens = sg1.getResult().split(":");
		    String[] time2_tokens = sg2.getResult().split(":");
		    int time1 = Integer.parseInt(time1_tokens[0]) * 60 * 60
			    + Integer.parseInt(time1_tokens[1]) * 60
			    + Integer.parseInt(time1_tokens[2]);
		    int time2 = Integer.parseInt(time2_tokens[0]) * 60 * 60
			    + Integer.parseInt(time2_tokens[1]) * 60
			    + Integer.parseInt(time2_tokens[2]);

		    return time1 - time2;
		}
	    });

	    QualifyingGroup finalGroup = new QualifyingGroup(competition, null, "F", null);
	    for (int i = 0; i < 7; i++)
	    {
		finalGroup.getSportsman_qualifyingGroups().add(
			new Sportsman_QualifyingGroup(sportmans.get(i).getSportsman(), finalGroup, null));
	    }
	    finalGroup.setIs_elimination(true);

	    if (competition.getSport().getName().equals("Streljastvo"))
	    {
		TreeSet<Round> rounds = new TreeSet();
		for (int j = 0; j < 5; j++)
		{
		    rounds.add(new Round(finalGroup, false, null, 0));
		}
	    }

	    tx = session.beginTransaction();
	    session.save(finalGroup);
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

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public List<QualifyingGroup> getCurrentGroups()
    {
	return currentGroups;
    }

    public void setCurrentGroups(List<QualifyingGroup> currentGroups)
    {
	this.currentGroups = currentGroups;
    }

    public Competition getCompetition()
    {
	return competition;
    }

    public void setCompetition(Competition competition)
    {
	this.competition = competition;
    }

    // </editor-fold>
}
