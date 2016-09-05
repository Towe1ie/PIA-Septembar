package hibernate.mappings;

public class Record
{
    private Integer id;
    private Integer year;
    private String location;
    private SportDiscipline sportDiscipline;
    private String firstName;
    private String lastName;
    private Country country;
    private String result;

    public Record()
    {
    }

    public Record(Integer year, String location, SportDiscipline sportDiscipline, String firstName, String lastName, Country country, String result)
    {
	this.year = year;
	this.location = location;
	this.sportDiscipline = sportDiscipline;
	this.firstName = firstName;
	this.lastName = lastName;
	this.country = country;
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

    public Integer getYear()
    {
	return year;
    }

    public void setYear(Integer year)
    {
	this.year = year;
    }

    public String getLocation()
    {
	return location;
    }

    public void setLocation(String location)
    {
	this.location = location;
    }

    public SportDiscipline getSportDiscipline()
    {
	return sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline)
    {
	this.sportDiscipline = sportDiscipline;
    }

    public String getFirstName()
    {
	return firstName;
    }

    public void setFirstName(String firstName)
    {
	this.firstName = firstName;
    }

    public String getLastName()
    {
	return lastName;
    }

    public void setLastName(String lastName)
    {
	this.lastName = lastName;
    }

    public Country getCountry()
    {
	return country;
    }

    public void setCountry(Country country)
    {
	this.country = country;
    }

    public String getResult()
    {
	return result;
    }

    public void setResult(String result)
    {
	this.result = result;
    }
    
    
}
