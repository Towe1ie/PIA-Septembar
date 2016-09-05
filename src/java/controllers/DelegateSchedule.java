package controllers;

import hibernate.mappings.*;
import hibernate.util.HibernateUtil;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;
import org.hibernate.*;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class DelegateSchedule implements Serializable
{

    private static final Random RAND = new Random();
    private Competition competition;
    private List<Round> currentRounds;
    private boolean competitionFinished = false;

    public void testAjax(SelectEvent event)
    {
	System.err.println("TEST AJAX");
    }

    public boolean isIndividual()
    {
	return competition.getSportDiscipline().getDisciplineType().equals("individualni");
    }

    public boolean isDisabled(Round round)
    {
	Round last_round = null;

	Iterator<Round> it = round.getQualifyingGroup().getRounds().iterator();

	while (it.hasNext())
	{
	    Round currRound = it.next();

	    if (currRound.equals(round))
	    {
		if (it.hasNext())
		{
		    Round nextRound = it.next();
		    return !nextRound.isIs_finished();
		}
		else
		{
		    return false;
		}
	    }
	}

	return true;
    }

    public void closeRound(Round round)
    {

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    if (!round.getQualifyingGroup().getIs_elimination())
	    {
		round.setIs_finished(true);
		Sport sport = round.getQualifyingGroup().getCompetition().getSport();
		if (sport.getName().equals("Kosarka") || sport.getName().equals("Odbojka") || sport.getName().equals("Vaterpolo"))
		{
		    for (Game game : round.getGames())
		    {
			Team winner = game.getWinnerTeam();
			Team looser = game.getLooserTeam();

			for (Team_QualifyingGroup tg : round.getQualifyingGroup().getTeam_qualifyingGroups())
			{
			    if (tg.getTeam().equals(winner))
			    {
				tg.setPoints(tg.getPoints() + 2);
			    }
			    if (tg.getTeam().equals(looser))
			    {
				tg.setPoints(tg.getPoints() + 1);
			    }
			}
		    }
		}

		if (round.getQualifyingGroup().getRounds().size() == round.getNum() + 1)
		{
		    round.getQualifyingGroup().setIs_finished(true);
		}

		tx = session.beginTransaction();
		session.update(round.getQualifyingGroup());
		tx.commit();
		session.close();

		List<QualifyingGroup> groups;
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		groups = session.createQuery("from QualifyingGroup g "
			+ "where g.competition.id = " + competition.getId() + " and "
			+ "g.is_elimination = false").list();
		tx.commit();
		session.close();

		boolean allfinished = true;
		for (QualifyingGroup group : groups)
		{
		    if (!group.getIs_finished() && !group.getIs_elimination())
		    {
			allfinished = false;
			break;
		    }
		}

		if (allfinished)
		{
		    generateEliminationGroup();
		}
	    }
	    else
	    {
		ArrayList<Round> rounds = new ArrayList(round.getQualifyingGroup().getRounds());
		round.setIs_finished(true);

		if (round.getNum() == 0)
		{
		    Game goldGame = round.getGames().first();
		    Game bronzeGame = round.getGames().last();

		    if (!isIndividual())
		    {
			goldGame.getWinnerTeam().getCountry().incGold();
			goldGame.getLooserTeam().getCountry().incSilver();

			bronzeGame.getWinnerTeam().getCountry().incBronze();

			tx = session.beginTransaction();
			session.saveOrUpdate(round);
			session.update(goldGame.getTeam1().getCountry());
			session.update(goldGame.getTeam2().getCountry());
			session.update(bronzeGame.getTeam1().getCountry());
			session.update(bronzeGame.getTeam2().getCountry());
			tx.commit();
		    }
		    else
		    {
			goldGame.getWinnerSportsmans().getCountry().incGold();
			goldGame.getLooserSportsmans().getCountry().incSilver();

			bronzeGame.getWinnerSportsmans().getCountry().incBronze();

			tx = session.beginTransaction();
			session.saveOrUpdate(round);
			session.update(goldGame.getSportsman1().getCountry());
			session.update(goldGame.getSportsman2().getCountry());
			session.update(bronzeGame.getSportsman1().getCountry());
			session.update(bronzeGame.getSportsman2().getCountry());
			tx.commit();
		    }
		}
		else
		{
		    ArrayList<Game> games = new ArrayList(round.getGames());
		    Round nextRound = rounds.get(round.getNum() - 1);
		    Iterator<Game> nextGameIt = nextRound.getGames().iterator();
		    for (int i = 0; i < round.getGames().size(); i += 2)
		    {
			Game game1 = games.get(i);
			Game game2 = games.get(i + 1);

			if (!isIndividual())
			{
			    Team winner1 = game1.getWinnerTeam();
			    Team winner2 = game2.getWinnerTeam();

			    Game game = nextGameIt.next();
			    game.setTeam1(winner1);
			    game.setTeam2(winner2);

			    if (round.getNum() == 1) // generisanje meca za bronzu
			    {
				game = nextGameIt.next();
				game.setTeam1(game1.getLooserTeam());
				game.setTeam2(game2.getLooserTeam());
			    }
			}
			else
			{
			    Sportsman winner1 = game1.getWinnerSportsmans();
			    Sportsman winner2 = game2.getWinnerSportsmans();

			    Game game = nextGameIt.next();
			    game.setSportsman1(winner1);
			    game.setSportsman2(winner2);

			    if (round.getNum() == 1 && nextGameIt.hasNext()) // generisanje meca za bronzu
			    {
				game = nextGameIt.next();
				game.setSportsman1(game1.getLooserSportsmans());
				game.setSportsman2(game2.getLooserSportsmans());
			    }
			}
		    }

		    tx = session.beginTransaction();
		    session.saveOrUpdate(round);
		    session.saveOrUpdate(nextRound);
		    tx.commit();
		}
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
	    if (session.isOpen())
	    {
		session.close();
	    }
	}
    }

    private void generateEliminationGroup()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	List<QualifyingGroup> groups = null;
	QualifyingGroup eliminationGroup = null;
	try
	{
	    tx = session.beginTransaction();
	    groups = session.createQuery("from QualifyingGroup g "
		    + "where g.competition.id = " + competition.getId() + " and "
		    + "g.is_elimination = false "
		    + "order by g.symbol").list();
	    eliminationGroup = (QualifyingGroup) session.createQuery("from QualifyingGroup g "
		    + "where g.competition.id = " + competition.getId() + " and "
		    + "g.is_elimination = true").list().get(0);

	    ArrayList<Round> elimRounds = new ArrayList(eliminationGroup.getRounds());
	    ArrayList<Team_QualifyingGroup> teamsA = new ArrayList(groups.get(0).getTeam_qualifyingGroups());
	    ArrayList<Team_QualifyingGroup> teamsB = new ArrayList(groups.get(1).getTeam_qualifyingGroups());

	    Iterator<Game> gameIt = elimRounds.get(2).getGames().iterator();
	    Game game = null;

	    game = gameIt.next();
	    game.setTeam1(teamsA.get(0).getTeam());
	    game.setTeam2(teamsB.get(3).getTeam());

	    game = gameIt.next();
	    game.setTeam1(teamsB.get(1).getTeam());
	    game.setTeam2(teamsA.get(2).getTeam());

	    game = gameIt.next();
	    game.setTeam1(teamsB.get(0).getTeam());
	    game.setTeam2(teamsA.get(3).getTeam());

	    game = gameIt.next();
	    game.setTeam1(teamsA.get(1).getTeam());
	    game.setTeam2(teamsB.get(2).getTeam());

	    session.save(eliminationGroup);
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

    public void updateRounds()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	try
	{
	    for (Round r : currentRounds)
	    {
		tx = session.beginTransaction();
		session.update(r);
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
    }

    public void generateGroups()
    {
	if (competition.getSport().getName().equals("Tenis")
		|| competition.getSport().getName().equals("Stoni tenis"))
	{
	    generateTennisGroup();
	    return;
	}

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;

	SortedSet<Team_QualifyingGroup> groupA = new TreeSet<>();
	SortedSet<Team_QualifyingGroup> groupB = new TreeSet<>();

	QualifyingGroup qgA = new QualifyingGroup(competition, null, "A", null);
	QualifyingGroup qgB = new QualifyingGroup(competition, null, "B", null);

	try
	{
	    for (Team t : competition.getTeams())
	    {
		if (RAND.nextBoolean())
		{
		    if (groupA.size() < competition.getTeams().size() / 2)
		    {
			groupA.add(new Team_QualifyingGroup(t, qgA, 0));
		    }
		    else
		    {
			groupB.add(new Team_QualifyingGroup(t, qgB, 0));
		    }
		}
		else if (groupB.size() < competition.getTeams().size() / 2)
		{
		    groupB.add(new Team_QualifyingGroup(t, qgB, 0));
		}
		else
		{
		    groupA.add(new Team_QualifyingGroup(t, qgA, 0));
		}
	    }

	    qgA.setTeam_qualifyingGroups(groupA);
	    qgB.setTeam_qualifyingGroups(groupB);

	    generateMatches(qgA);
	    tx = session.beginTransaction();
	    session.save(qgA);
	    tx.commit();

	    generateMatches(qgB);
	    tx = session.beginTransaction();
	    session.save(qgB);
	    tx.commit();

	    // Eliminaciona grupa
	    QualifyingGroup eliminationGroup = new QualifyingGroup(competition, null, "F", new TreeSet());
	    eliminationGroup.setIs_elimination(true);

	    Round quarterRound = new Round(eliminationGroup, false, new TreeSet(), 2);
	    Team nullTeam = null;
	    for (int i = 0; i < 4; i++)
	    {
		quarterRound.getGames().add(new Game(nullTeam, nullTeam, eliminationGroup));
	    }
	    eliminationGroup.getRounds().add(quarterRound);

	    Round semi = new Round(eliminationGroup, false, new TreeSet(), 1);
	    for (int i = 0; i < 2; i++)
	    {
		semi.getGames().add(new Game(nullTeam, nullTeam, eliminationGroup));
	    }
	    eliminationGroup.getRounds().add(semi);

	    Round finals = new Round(eliminationGroup, false, new TreeSet(), 0);
	    for (int i = 0; i < 2; i++)
	    {
		finals.getGames().add(new Game(nullTeam, nullTeam, eliminationGroup));
	    }
	    eliminationGroup.getRounds().add(finals);

	    tx = session.beginTransaction();
	    session.save(eliminationGroup);
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

    @SuppressWarnings("empty-statement")
    private void generateMatches(QualifyingGroup group)
    {
	int roundsCnt = group.getTeam_qualifyingGroups().size() - 1;
	int gamesCnt = group.getTeam_qualifyingGroups().size() * (group.getTeam_qualifyingGroups().size() - 1) / 2;
	int gamesPerRound = (gamesCnt + roundsCnt - 1) / roundsCnt;

	SortedSet<Round> rounds = new TreeSet<>();
	SortedSet<Game> games = null;
	List<Team_QualifyingGroup> teams = new ArrayList(group.getTeam_qualifyingGroups());
	Map<Team_QualifyingGroup, List<Team_QualifyingGroup>> globalCups = new HashMap();
	List<List<Team_QualifyingGroup>> workingCups = new ArrayList();
	List<Team_QualifyingGroup> usedCup = new ArrayList();
	for (int i = 0; i < teams.size(); i++)
	{
	    Team_QualifyingGroup t = teams.get(i);
	    globalCups.put(t, new ArrayList());
	    for (int j = 0; j < teams.size(); j++)
	    {
		if (j != i)
		{
		    globalCups.get(t).add(teams.get(j));
		}
	    }
	}

	for (int r = 0; r < roundsCnt; r++)
	{
	    for (int i = 0; i < globalCups.size(); i++)
	    {
		Team_QualifyingGroup t = teams.get(i);
		workingCups.add(i, new ArrayList(globalCups.get(t)));
	    }
	    int i = 0;
	    games = new TreeSet();

	    while (i < teams.size())
	    {
		Team_QualifyingGroup t1 = teams.get(i);
		int rand = RAND.nextInt(workingCups.get(i).size());
		Team_QualifyingGroup t2 = workingCups.get(i).remove(rand);

		globalCups.get(t1).remove(t2);
		globalCups.get(t2).remove(t1);

		for (int j = 0; j < workingCups.size(); j++)
		{
		    workingCups.get(j).remove(t1);
		    workingCups.get(j).remove(t2);
		}

		usedCup.add(t1);
		usedCup.add(t2);

		Game game = new Game(t1.getTeam(), t2.getTeam(), group);
		games.add(game);

		while (usedCup.contains(teams.get(i)))
		{
		    i++;
		    if (!(i < teams.size()))
		    {
			break;
		    }
		}
	    }

	    usedCup.clear();
	    workingCups.clear();
	    rounds.add(new Round(group, false, games, r));
	}

	group.setRounds(rounds);
    }

    public void generateTennisGroup()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	boolean isIndividual = competition.getSportDiscipline().getDisciplineType().equals("individualni");

	try
	{
	    List<Sportsman> sportsmans = new ArrayList(competition.getSportsmans());
	    List<Team> teams = new ArrayList(competition.getTeams());

	    if (isIndividual && (sportsmans.size() == 4 || sportsmans.size() == 8
		    || sportsmans.size() == 16 || sportsmans.size() == 32)
		    || !isIndividual && (teams.size() == 4 || teams.size() == 8
		    || teams.size() == 16 || teams.size() == 32))
	    {
		int gamesCnt = isIndividual ? sportsmans.size() : teams.size();

		int roundsCnt = (int) (Math.log(gamesCnt) / Math.log(2));
		QualifyingGroup eliminationGroup = new QualifyingGroup(competition, null, "F", new TreeSet());
		eliminationGroup.setIs_elimination(Boolean.TRUE);

		int gamesInRound = gamesCnt / 2;
		for (int i = 0; i < roundsCnt; i++)
		{
		    Round round = new Round(eliminationGroup, false, new TreeSet(), roundsCnt - i - 1);

		    for (int j = 0; j < gamesInRound; j++)
		    {
			if (isIndividual)
			{
			    Sportsman s1 = null;
			    Sportsman s2 = null;

			    if (i == 0)
			    {
				s1 = sportsmans.remove(RAND.nextInt(sportsmans.size()));
				s2 = sportsmans.remove(RAND.nextInt(sportsmans.size()));
			    }

			    Game game = new Game(s1, s2, eliminationGroup);

			    round.getGames().add(game);
			}
			else
			{
			    Team t1 = null;
			    Team t2 = null;

			    if (i == 0)
			    {
				t1 = teams.remove(RAND.nextInt(teams.size()));
				t2 = teams.remove(RAND.nextInt(teams.size()));
			    }

			    Game game = new Game(t1, t2, eliminationGroup);

			    round.getGames().add(game);
			}

		    }

		    eliminationGroup.getRounds().add(round);
		    gamesInRound /= 2;
		}

		tx = session.beginTransaction();
		session.save(eliminationGroup);
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

    public void fetchRoundsForGroup(QualifyingGroup group)
    {
	currentRounds = new ArrayList(group.getRounds());
    }

    public List<QualifyingGroup> getGroups()
    {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction tx = null;
	List<QualifyingGroup> groups = null;

	try
	{
	    tx = session.beginTransaction();
	    groups = session.createQuery("from QualifyingGroup g where g.competition.id = " + competition.getId()).list();
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

	return groups;
    }

    public Competition getCompetition()
    {
	return competition;
    }

    public void setCompetition(Competition competition)
    {
	this.competition = competition;
    }

    public List<Round> getCurrentRounds()
    {
	return currentRounds;
    }

    public void setCurrentRounds(List<Round> currentRounds)
    {
	this.currentRounds = currentRounds;
    }

    public boolean isCompetitionFinished()
    {
	return competitionFinished;
    }

    public void setCompetitionFinished(boolean competitionFinished)
    {
	this.competitionFinished = competitionFinished;
    }

}
