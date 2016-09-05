package hibernate.mappings;

import java.util.*;

public class Round implements Comparable<Round>
{

    private Integer id;
    private QualifyingGroup qualifyingGroup;
    private boolean is_finished;
    private SortedSet<Game> games = null;
    private Integer num;

    public Round()
    {
    }

    public Round(QualifyingGroup qualifyingGroup, boolean is_finished, SortedSet<Game> games, Integer num)
    {
	this.qualifyingGroup = qualifyingGroup;
	this.is_finished = is_finished;
	this.games = games;
	this.num = num;
    }

    @Override
    public boolean equals(Object o)
    {
	Round r = (Round)o;
	if (o == null || r.getId() == null || this.getId() == null)
	    return false;
	return ((Round) o).getId().intValue() == this.getId().intValue();
    }

    @Override
    public int hashCode()
    {
	int hash = 7;
	hash = 73 * hash + Objects.hashCode(this.id);
	return hash;
    }

    @Override
    public int compareTo(Round t)
    {
	if (this.getNum() == null || t == null || t.getNum() == null)
	{
	    return 1;
	}
	return this.getNum() - t.getNum();
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public QualifyingGroup getQualifyingGroup()
    {
	return qualifyingGroup;
    }

    public void setQualifyingGroup(QualifyingGroup qualifyingGroup)
    {
	this.qualifyingGroup = qualifyingGroup;
    }

    public boolean isIs_finished()
    {
	return is_finished;
    }

    public void setIs_finished(boolean is_finished)
    {
	this.is_finished = is_finished;
    }

    public SortedSet<Game> getGames()
    {
	return games;
    }

    public void setGames(SortedSet<Game> games)
    {
	this.games = games;
    }

    public Integer getNum()
    {
	return num;
    }

    public void setNum(Integer num)
    {
	this.num = num;
    }

}
