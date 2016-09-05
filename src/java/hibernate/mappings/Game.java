package hibernate.mappings;

import java.util.Date;

public class Game implements Comparable<Game>
{

    private Integer id;
    private Team team1 = null;
    private Team team2 = null;
    private Sportsman sportsman1 = null;
    private Sportsman sportsman2 = null;
    private QualifyingGroup qualifyingGroup;
    private Date date_and_time;
    private String location;
    private String result;

    public Game()
    {
    }

    public Game(Team team1, Team team2, QualifyingGroup qualifyingGroup)
    {
	this.team1 = team1;
	this.team2 = team2;
	this.qualifyingGroup = qualifyingGroup;
    }

    public Game(Sportsman sportsman1, Sportsman sportsman2, QualifyingGroup qualifyingGroup)
    {
	this.sportsman1 = sportsman1;
	this.sportsman2 = sportsman2;
	this.qualifyingGroup = qualifyingGroup;
    }
    
    public Team getWinnerTeam()
    {
	int[] points = getPoints();
	return points[0] > points[1] ? team1 : team2;
    }
    
    public Sportsman getWinnerSportsmans()
    {
	int[] points = getPoints();
	return points[0] > points[1] ? sportsman1 : sportsman2;
    }
    
    public Team getLooserTeam()
    {
	int[] points = getPoints();
	return points[0] < points[1] ? team1 : team2;
    }
    
    public Sportsman getLooserSportsmans()
    {
	int[] points = getPoints();
	return points[0] < points[1] ? sportsman1 : sportsman2;
    }
    
    public int[] getPoints()
    {
	String[] points = result.split(":");
	int[] ret = new int[2];
	ret[0] = Integer.parseInt(points[0]);
	ret[1] = Integer.parseInt(points[1]);
	
	return ret;
    }

    @Override
    public int compareTo(Game t)
    {
	if (this.id == null || t.id == null)
	{
	    return 1;
	}
	return this.getId() - t.getId();
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Team getTeam1()
    {
	return team1;
    }

    public void setTeam1(Team team1)
    {
	this.team1 = team1;
    }

    public Team getTeam2()
    {
	return team2;
    }

    public void setTeam2(Team team2)
    {
	this.team2 = team2;
    }

    public QualifyingGroup getQualifyingGroup()
    {
	return qualifyingGroup;
    }

    public void setQualifyingGroup(QualifyingGroup qualyfingGroup)
    {
	this.qualifyingGroup = qualyfingGroup;
    }

    public Date getDate_and_time()
    {
	return date_and_time;
    }

    public void setDate_and_time(Date date_and_time)
    {
	this.date_and_time = date_and_time;
    }

    public String getResult()
    {
	return result;
    }

    public void setResult(String result)
    {
	this.result = result;
    }

    public Sportsman getSportsman1()
    {
	return sportsman1;
    }

    public void setSportsman1(Sportsman sportsman1)
    {
	this.sportsman1 = sportsman1;
    }

    public Sportsman getSportsman2()
    {
	return sportsman2;
    }

    public void setSportsman2(Sportsman sportsman2)
    {
	this.sportsman2 = sportsman2;
    }

    public String getLocation()
    {
	return location;
    }

    public void setLocation(String location)
    {
	this.location = location;
    }

    
}
