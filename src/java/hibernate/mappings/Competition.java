/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.mappings;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nemanja
 */
public class Competition
{

    private Integer id;
    private Sport sport;
    private SportDiscipline sportDiscipline;
    private String sex;
    private Date startDate;
    private Date finishDate;
    private String location;
    private Set<Team> teams = new HashSet<>(0);
    private Set<Sportsman> sportsmans = new HashSet<>(0);
    private User delegate;

    public Competition()
    {
    }

    public Competition(Sport sport, SportDiscipline sportDiscipline, String sex, Date startDate, Date finishDate, String location, Set<Team> teams, Set<Sportsman> sportsmans, User delegate)
    {
	this.sport = sport;
	this.sportDiscipline = sportDiscipline;
	this.sex = sex;
	this.startDate = startDate;
	this.finishDate = finishDate;
	this.location = location;
	this.teams = teams;
	this.sportsmans = sportsmans;
	this.delegate = delegate;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Sport getSport()
    {
	return sport;
    }

    public void setSport(Sport sport)
    {
	this.sport = sport;
    }

    public SportDiscipline getSportDiscipline()
    {
	return sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline)
    {
	this.sportDiscipline = sportDiscipline;
    }

    public String getSex()
    {
	return sex;
    }

    public void setSex(String sex)
    {
	this.sex = sex;
    }

    public Date getStartDate()
    {
	return startDate;
    }

    public void setStartDate(Date startDate)
    {
	this.startDate = startDate;
    }

    public Date getFinishDate()
    {
	return finishDate;
    }

    public void setFinishDate(Date finishDate)
    {
	this.finishDate = finishDate;
    }

    public String getLocation()
    {
	return location;
    }

    public void setLocation(String location)
    {
	this.location = location;
    }

    public Set<Team> getTeams()
    {
	return teams;
    }

    public void setTeams(Set<Team> teams)
    {
	this.teams = teams;
    }

    public Set<Sportsman> getSportsmans()
    {
	return sportsmans;
    }

    public void setSportsmans(Set<Sportsman> sportsmans)
    {
	this.sportsmans = sportsmans;
    }

    public User getDelegate()
    {
	return delegate;
    }

    public void setDelegate(User delegate)
    {
	this.delegate = delegate;
    }

}
