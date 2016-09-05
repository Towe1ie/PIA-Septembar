package hibernate.mappings;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

public class Team
{

    private Integer id;
    private Country country;
    private Sport sport;
    private Set<Competition> competitions;
    private String sex;
    private SortedSet<Team_QualifyingGroup> team_qualifyingGroups;

    public Team()
    {
    }

    public Team(Country country, Sport sport, Set<Competition> competitions, String sex)
    {
	this.country = country;
	this.sport = sport;
	this.competitions = competitions;
	this.sex = sex;
    }

    @Override
    public boolean equals(Object o)
    {
	if (!(o instanceof Team))
	{
	    return false;
	}
	if (o == null || ((Team) o).id == null || this.id == null)
	{
	    return false;
	}
	return ((Team) o).getId().intValue() == id.intValue();
    }

    @Override
    public int hashCode()
    {
	int hash = 3;
	hash = 37 * hash + Objects.hashCode(this.id);
	return hash;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
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

    public Set<Competition> getCompetitions()
    {
	return competitions;
    }

    public void setCompetitions(Set<Competition> competitions)
    {
	this.competitions = competitions;
    }

    public String getSex()
    {
	return sex;
    }

    public void setSex(String sex)
    {
	this.sex = sex;
    }

    public SortedSet<Team_QualifyingGroup> getTeam_qualifyingGroups()
    {
	return team_qualifyingGroups;
    }

    public void setTeam_qualifyingGroups(SortedSet<Team_QualifyingGroup> team_qualifyingGroups)
    {
	this.team_qualifyingGroups = team_qualifyingGroups;
    }

}
