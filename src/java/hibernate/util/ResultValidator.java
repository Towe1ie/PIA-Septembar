package hibernate.util;

import hibernate.mappings.*;
import java.util.regex.*;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;

@FacesValidator("ResultValidator")
public class ResultValidator implements Validator
{

    private static final String BASKETBALL_RES_PATTERN = "[0-9]+:[0-9]+";
    private static final String RUNNING_SHORT_RES_PATTERN = "[0-9]{2},[0-9]{1,2}+";
    private static final String RUNNING_LONG_RES_PATTERN = "[0-9]{1,2}+:[0-9]{1,2}+,[0-9]{1,2}+";
    private static final String JUMP_THROWS_RES_PATTERN = "[0-9]{1,2}+,[0-9]{1,2}+";
    private static final String MARATHON_RES_PATTERN = "[0-9]{1,2}+:[0-9]{1,2}+:[0-9]{1,2}+";
    private static final String TENNIS_RES_PATTERN = "[0-4]:[0-4]";
    private static final String TABLE_TENNIS_RES_PATTERN = "[0-2]:[0-2]";

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
	Sport sport = (Sport) component.getAttributes().get("sport");
	SportDiscipline sportDiscipline = (SportDiscipline) component.getAttributes().get("sportDiscipline");

	if (sport.getName().equals("Kosarka") || sport.getName().equals("Vaterpolo") || sport.getName().equals("Odbojka"))
	{
	    pattern = Pattern.compile(BASKETBALL_RES_PATTERN);
	    matcher = pattern.matcher(value.toString());

	    if (!matcher.matches())
	    {
		FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu brpoena_TIM1:brpoena_TIM2");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }

	    if (sport.getName().equals("Odbojka"))
	    {
		String[] points = value.toString().split(":");
		int p1 = Integer.parseInt(points[0]);
		int p2 = Integer.parseInt(points[1]);

		if ((p1 != 3 && p2 != 3)
			|| (p1 == 3 && (p2 < 0 || p2 > 2))
			|| (p2 == 3 && (p1 < 0 || p1 > 2)))

		{
		    FacesMessage msg = new FacesMessage("Moguci rezultati 3:0, 3:1, 3:2 i obrnuto");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);
		}

	    }
	}
	else if (sport.getName().equals("Atletika"))
	{
	    String sdName = sportDiscipline.getName();

	    if (sdName.equals("100m trcanje") || sdName.equals("200m trcanje") || sdName.equals("400m trcanje"))
	    {
		pattern = Pattern.compile(RUNNING_SHORT_RES_PATTERN);
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches())
		{
		    FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu SS,TT");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);
		}
	    }
	    else if (sdName.equals("800m trcanje") || sdName.equals("5000m trcanje") || sdName.equals("10000m trcanje"))
	    {
		pattern = Pattern.compile(RUNNING_LONG_RES_PATTERN);
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches())
		{
		    FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu MM:SS,TT");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);
		}
	    }
	    else if (sdName.equals("Skok u vis") || sdName.equals("Skok u dalj") || sdName.equals("Troskok") || sdName.equals("Skok s motkom")
		    || sdName.equals("Bacanje kugle") || sdName.equals("Bacanje diska") || sdName.equals("Bacanje kladiva") || sdName.equals("Bacanje koplja"))
	    {
		pattern = Pattern.compile(JUMP_THROWS_RES_PATTERN);
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches())
		{
		    FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu M,CM");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);
		}
	    }
	    else if (sdName.equals("Maraton") || sdName.equals("20km brzo hodanje") || sdName.equals("50km brzo hodanje"))
	    {
		pattern = Pattern.compile(MARATHON_RES_PATTERN);
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches())
		{
		    FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu HH:MM:SS");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);
		}
	    }
	}
	else if (sport.getName().equals("Biciklizam"))
	{
	    pattern = Pattern.compile(MARATHON_RES_PATTERN);
	    matcher = pattern.matcher(value.toString());

	    if (!matcher.matches())
	    {
		FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu HH:MM:SS");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }
	}
	else if (sport.getName().equals("Plivanje"))
	{
	    pattern = Pattern.compile(RUNNING_SHORT_RES_PATTERN);
	    matcher = pattern.matcher(value.toString());

	    if (!matcher.matches())
	    {
		FacesMessage msg = new FacesMessage("Rezultat mora biti u formatu SS,TT");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }
	}
	else if (sport.getName().equals("Tenis"))
	{
	    pattern = Pattern.compile(TENNIS_RES_PATTERN);
	    matcher = pattern.matcher(value.toString());

	    String[] points = value.toString().split(":");
	    int p1 = Integer.parseInt(points[0]);
	    int p2 = Integer.parseInt(points[1]);

	    if (!matcher.matches() || (p1 != 4 && p2 != 4) || (p1 == 4 && p2 == 4))
	    {
		FacesMessage msg = new FacesMessage("Rezultat moze biti 4:3, 4:2, 4:1 ili 4:0 i obrnuto");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }
	}
	else if (sport.getName().equals("Stoni tenis"))
	{
	    pattern = Pattern.compile(TABLE_TENNIS_RES_PATTERN);
	    matcher = pattern.matcher(value.toString());

	    String[] points = value.toString().split(":");
	    int p1 = Integer.parseInt(points[0]);
	    int p2 = Integer.parseInt(points[1]);

	    if (!matcher.matches() || (p1 != 2 && p2 != 2) || (p1 == 2 && p2 == 2))
	    {
		FacesMessage msg = new FacesMessage("Rezultat moze biti 2:1 ili 2:0 i obrnuto");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }
	}
	else if (sport.getName().equals("Streljastvo"))
	{
	    try
	    {
		Integer i = Integer.parseInt(value.toString());
	    }
	    catch(Exception e)
	    {
		FacesMessage msg = new FacesMessage("Rezultat moze biti 2:1 ili 2:0 i obrnuto");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	    }
	}

    }

}
