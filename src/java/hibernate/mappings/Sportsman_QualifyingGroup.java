package hibernate.mappings;

public class Sportsman_QualifyingGroup implements Comparable<Sportsman_QualifyingGroup>
{

    private Integer id;
    private Sportsman sportsman;
    private QualifyingGroup qualifyingGroup;
    private String result;
    private String tmp_result;

    public Sportsman_QualifyingGroup()
    {
    }

    public Sportsman_QualifyingGroup(Sportsman sportsman, QualifyingGroup qualifyingGroup, String result)
    {
	this.sportsman = sportsman;
	this.qualifyingGroup = qualifyingGroup;
	this.result = result;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Sportsman getSportsman()
    {
	return sportsman;
    }

    public void setSportsman(Sportsman sportsman)
    {
	this.sportsman = sportsman;
    }

    public QualifyingGroup getQualifyingGroup()
    {
	return qualifyingGroup;
    }

    public void setQualifyingGroup(QualifyingGroup qualifyingGroup)
    {
	this.qualifyingGroup = qualifyingGroup;
    }

    public String getResult()
    {
	return result;
    }

    public void setResult(String result)
    {
	this.result = result;
    }

    public String getTmp_result()
    {
	return tmp_result;
    }

    public void setTmp_result(String tmp_result)
    {
	this.tmp_result = tmp_result;
    }

    @Override
    public int compareTo(Sportsman_QualifyingGroup t)
    {
	try
	{
	    String[] tokens1 = this.getResult().split(":|,");
	    String[] tokens2 = t.getResult().split(":|,");

	    Sport sport = this.qualifyingGroup.getCompetition().getSport();
	    String sportName = sport.getName();

	    SportDiscipline sd = this.qualifyingGroup.getCompetition().getSportDiscipline();
	    String sdName = sd.getName();

	    if (sdName.equals("Maraton") || sdName.equals("20km brzo hodanje") || sdName.equals("50km brzo hodanje")
		    || sdName.equals("800m trcanje") || sdName.equals("5000m trcanje") || sdName.equals("10000m trcanje")
		    || sdName.equals("Drumska trka 225km"))
	    {

		int time1 = Integer.parseInt(tokens1[0]) * 60 * 60
			+ Integer.parseInt(tokens1[1]) * 60
			+ Integer.parseInt(tokens1[2]);
		int time2 = Integer.parseInt(tokens2[0]) * 60 * 60
			+ Integer.parseInt(tokens2[1]) * 60
			+ Integer.parseInt(tokens2[2]);

		return time1 - time2;
	    }
	    else if (sdName.equals("100m trcanje") || sdName.equals("200m trcanje") || sdName.equals("400m trcanje")
		    || sdName.equals("100m leptir") || sdName.equals("200m slobodno"))
	    {
		int time1 = Integer.parseInt(tokens1[0]) * 100
			+ Integer.parseInt(tokens1[1]);
		int time2 = Integer.parseInt(tokens2[0]) * 100
			+ Integer.parseInt(tokens2[1]);

		return time1 - time2;
	    }
	    else if (sportName.equals("Streljastvo"))
	    {
		int points1 = Integer.parseInt(tokens1[0]);
		int points2 = Integer.parseInt(tokens2[0]);
		if (points1 == points2)
		{
		    return 1;
		}
		return points2 - points1;
	    }

	}
	catch (Exception e)
	{
	    return 1;
	}

	return 1;
    }

}
