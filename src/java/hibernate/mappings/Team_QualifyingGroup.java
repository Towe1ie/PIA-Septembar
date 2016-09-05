package hibernate.mappings;

import java.util.Objects;

public class Team_QualifyingGroup implements Comparable<Team_QualifyingGroup>
{

    private Integer id;
    private Team team;
    private QualifyingGroup qualifyingGroup;
    private Integer points;

    public Team_QualifyingGroup()
    {
    }

    public Team_QualifyingGroup(Team team, QualifyingGroup qualifyingGroup, Integer points)
    {
	this.team = team;
	this.qualifyingGroup = qualifyingGroup;
	this.points = points;
    }

//    @Override
//    public boolean equals(Object o)
//    {
//	if (!(o instanceof Team_QualifyingGroup))
//	    return false;
//	
//	Team_QualifyingGroup tg = (Team_QualifyingGroup)o;
//	if (tg.id == null || this.id == null)
//	{
//	    return false;
//	}
//	
//	return this.id.intValue() == tg.id.intValue();
//    }
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
	final Team_QualifyingGroup other = (Team_QualifyingGroup) obj;
	if (this.id == null || other.id == null)
	{
	    return false;
	}
	if (!Objects.equals(this.id, other.id))
	{
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode()
    {
	int hash = 3;
	hash = 73 * hash + Objects.hashCode(this.id);
	return hash;
    }

    @Override
    public int compareTo(Team_QualifyingGroup t)
    {
	if (t == null || t.points == null || this.points == null)
	{
	    return 1;
	}

	if (t.points.intValue() == this.points.intValue())
	{
	    return 1;
	}
	return t.points - this.points;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Team getTeam()
    {
	return team;
    }

    public void setTeam(Team team)
    {
	this.team = team;
    }

    public QualifyingGroup getQualifyingGroup()
    {
	return qualifyingGroup;
    }

    public void setQualifyingGroup(QualifyingGroup qualifyingGroup)
    {
	this.qualifyingGroup = qualifyingGroup;
    }

    public Integer getPoints()
    {
	return points;
    }

    public void setPoints(Integer points)
    {
	this.points = points;
    }

}
