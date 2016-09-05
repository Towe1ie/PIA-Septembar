package hibernate.mappings;

import java.util.*;

public class QualifyingGroup
{

    private Integer id;
    private Competition competition;
    //private Set<Team> teams;
    private SortedSet<Team_QualifyingGroup> team_qualifyingGroups;
    private SortedSet<Sportsman_QualifyingGroup> sportsman_qualifyingGroups = new TreeSet();
    private String symbol;
    private SortedSet<Round> rounds;
    private Boolean is_elimination = false;
    private Boolean is_finished = false;
    private Date date_and_time;

    public QualifyingGroup()
    {
    }

    public QualifyingGroup(Competition competition, SortedSet<Team_QualifyingGroup> team_qualifyingGroups, String symbol, SortedSet<Round> rounds)
    {
	this.competition = competition;
	this.team_qualifyingGroups = team_qualifyingGroups;
	this.symbol = symbol;
	this.rounds = rounds;
    }

    @Override
    public int hashCode()
    {
	int hash = 5;
	hash = 79 * hash + Objects.hashCode(this.id);
	return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	{
	    return true;
	}
	if (obj == null)
	{
	    return false;
	}
	if (getClass() != obj.getClass())
	{
	    return false;
	}
	final QualifyingGroup other = (QualifyingGroup) obj;
	if (!Objects.equals(this.id, other.id))
	{
	    return false;
	}
	return true;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Competition getCompetition()
    {
	return competition;
    }

    public void setCompetition(Competition competition)
    {
	this.competition = competition;
    }

    public SortedSet<Team_QualifyingGroup> getTeam_qualifyingGroups()
    {
	return team_qualifyingGroups;
    }

    public void setTeam_qualifyingGroups(SortedSet<Team_QualifyingGroup> team_qualifyingGroups)
    {
	this.team_qualifyingGroups = team_qualifyingGroups;
    }

    public String getSymbol()
    {
	return symbol;
    }

    public void setSymbol(String symbol)
    {
	this.symbol = symbol;
    }

    public SortedSet<Round> getRounds()
    {
	return rounds;
    }

    public void setRounds(SortedSet<Round> rounds)
    {
	this.rounds = rounds;
    }

    public Boolean getIs_elimination()
    {
	return is_elimination;
    }

    public void setIs_elimination(Boolean is_elimination)
    {
	this.is_elimination = is_elimination;
    }

    public Boolean getIs_finished()
    {
	return is_finished;
    }

    public void setIs_finished(Boolean is_finished)
    {
	this.is_finished = is_finished;
    }

    public SortedSet<Sportsman_QualifyingGroup> getSportsman_qualifyingGroups()
    {
	return sportsman_qualifyingGroups;
    }

    public void setSportsman_qualifyingGroups(SortedSet<Sportsman_QualifyingGroup> sportsman_qualifyingGroups)
    {
	this.sportsman_qualifyingGroups = sportsman_qualifyingGroups;
    }

    public Date getDate_and_time()
    {
	return date_and_time;
    }

    public void setDate_and_time(Date date_and_time)
    {
	if (date_and_time != null)
	{
	    System.err.println(date_and_time.toString());
	}
	this.date_and_time = date_and_time;
    }

}
