package hibernate.mappings;
// Generated Aug 26, 2016 4:43:40 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Sport generated by hbm2java
 */
public class Sport implements java.io.Serializable
{

    private Integer idsport;
    private String name;
    private Set<RioSport> rioSports = new HashSet<RioSport>(0);
    private Set<SportDiscipline> sportDisciplines = new HashSet<SportDiscipline>(0);
    private Set<Sportsman> sportsmans = new HashSet<Sportsman>(0);

    public Sport()
    {
    }

    public Sport(String name, Set<RioSport> rioSports, Set<SportDiscipline> sportDisciplines, Set<Sportsman> sportsmans)
    {
	this.name = name;
	this.rioSports = rioSports;
	this.sportDisciplines = sportDisciplines;
	this.sportsmans = sportsmans;
    }
    
    @Override
    public boolean equals(Object o)
    {
	return ((Sport)o).idsport.intValue() == idsport.intValue();
    }

    @Override
    public int hashCode()
    {
	int hash = 7;
	hash = 97 * hash + Objects.hashCode(this.idsport);
	return hash;
    }

    public Integer getIdsport()
    {
	return this.idsport;
    }

    public void setIdsport(Integer idsport)
    {
	this.idsport = idsport;
    }

    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public Set<RioSport> getRioSports()
    {
	return this.rioSports;
    }

    public void setRioSports(Set<RioSport> rioSports)
    {
	this.rioSports = rioSports;
    }

    public Set<SportDiscipline> getSportDisciplines()
    {
	return this.sportDisciplines;
    }

    public void setSportDisciplines(Set<SportDiscipline> sportDisciplines)
    {
	this.sportDisciplines = sportDisciplines;
    }

    public Set<Sportsman> getSportsmans()
    {
	return this.sportsmans;
    }

    public void setSportsmans(Set<Sportsman> sportsmans)
    {
	this.sportsmans = sportsmans;
    }

}
