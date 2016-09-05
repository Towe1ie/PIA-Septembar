package hibernate.mappings;
// Generated Aug 26, 2016 4:43:40 AM by Hibernate Tools 4.3.1

import java.util.Objects;

/**
 * SportsmanDiscipline generated by hbm2java
 */
public class SportsmanDiscipline implements java.io.Serializable
{

    private Integer id;
    private SportDiscipline sportDiscipline;
    private Sportsman sportsman;

    public SportsmanDiscipline()
    {
    }

    public SportsmanDiscipline(SportDiscipline sportDiscipline, Sportsman sportsman)
    {
	this.sportDiscipline = sportDiscipline;
	this.sportsman = sportsman;
    }

    @Override
    public int hashCode()
    {
	int hash = 7;
	hash = 89 * hash + Objects.hashCode(this.id);
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
	final SportsmanDiscipline other = (SportsmanDiscipline) obj;
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

    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public SportDiscipline getSportDiscipline()
    {
	return this.sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline)
    {
	this.sportDiscipline = sportDiscipline;
    }

    public Sportsman getSportsman()
    {
	return this.sportsman;
    }

    public void setSportsman(Sportsman sportsman)
    {
	this.sportsman = sportsman;
    }

}
