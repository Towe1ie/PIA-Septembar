package controllers;

import hibernate.mappings.*;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PreDestroy;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class Delegate implements Serializable
{

    private Competition chosenCompetition;
    private boolean showDisciplines = false;

    public List<Competition> getCompetitions()
    {
	User user = getCurrentUser();

	return new ArrayList(user.getCompetitions());
    }

    public String chooseCompetition(Competition competition)
    {
	if (competition.getSportDiscipline().getDisciplineType().equals("ekipni")
		|| competition.getSport().getName().equals("Tenis")
		|| competition.getSport().getName().equals("Stoni tenis"))
	{
	    return "delegate_schedule";
	}
	else if (competition.getSportDiscipline().getDisciplineType().equals("individualni"))
	{
	    if (true)
	    {
		return "delegate_ind_schedule";
	    }
	    else
	    {
		return null;
	    }
	}
	else
	{
	    return null;
	}
    }

    @PreDestroy
    public void destroy()
    {

    }

    public User getCurrentUser()
    {
	return (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Competition getChosenCompetition()
    {
	return chosenCompetition;
    }

    public void setChosenCompetition(Competition chosenCompetition)
    {
	this.chosenCompetition = chosenCompetition;
    }

    public boolean isShowDisciplines()
    {
	return showDisciplines;
    }

    public void setShowDisciplines(boolean showDisciplines)
    {
	this.showDisciplines = showDisciplines;
    }

}
