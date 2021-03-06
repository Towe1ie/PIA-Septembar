package hibernate.mappings;
// Generated Aug 26, 2016 4:43:40 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Country generated by hbm2java
 */
public class Country implements java.io.Serializable, Comparable<Country>
{

    private Integer id;
    private String name;
    private String flagRef;
    private Set<User> users = new HashSet<User>(0);
    private Set<Sportsman> sportsmans = new HashSet<Sportsman>(0);
    private Integer bronze = 0;
    private Integer silver = 0;
    private Integer gold = 0;

    public Country()
    {
    }

    public Country(String name, String flagRef, Set<User> users, Set<Sportsman> sportsmans)
    {
	this.name = name;
	this.flagRef = flagRef;
	this.users = users;
	this.sportsmans = sportsmans;
    }

    public void incGold()
    {
	gold++;
    }

    public void incSilver()
    {
	silver++;
    }

    public void incBronze()
    {
	bronze++;
    }

    @Override
    public int compareTo(Country t)
    {
	if (t.gold - gold != 0)
	    return t.gold - gold;
	if (t.silver - silver != 0)
	    return t.silver - silver;
	if (t.bronze - bronze != 0)
	    return t.bronze - bronze;
	return 1;
    }

    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getFlagRef()
    {
	return this.flagRef;
    }

    public void setFlagRef(String flagRef)
    {
	this.flagRef = flagRef;
    }

    public Set<User> getUsers()
    {
	return this.users;
    }

    public void setUsers(Set<User> users)
    {
	this.users = users;
    }

    public Set<Sportsman> getSportsmans()
    {
	return this.sportsmans;
    }

    public void setSportsmans(Set<Sportsman> sportsmans)
    {
	this.sportsmans = sportsmans;
    }

    public Integer getBronze()
    {
	return bronze;
    }

    public void setBronze(Integer bronze)
    {
	this.bronze = bronze;
    }

    public Integer getSilver()
    {
	return silver;
    }

    public void setSilver(Integer silver)
    {
	this.silver = silver;
    }

    public Integer getGold()
    {
	return gold;
    }

    public void setGold(Integer gold)
    {
	this.gold = gold;
    }

}
