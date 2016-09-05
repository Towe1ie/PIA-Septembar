package hibernate.mappings;

public class EliminationRound
{
    private int id;
    private Competition competition;
    private Game quarter_game, semi_game, third_game, finals_game;

    public EliminationRound()
    {
    }

    public EliminationRound(Competition competition)
    {
	this.competition = competition;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public Competition getCompetition()
    {
	return competition;
    }

    public void setCompetition(Competition competition)
    {
	this.competition = competition;
    }

    public Game getQuarter_game()
    {
	return quarter_game;
    }

    public void setQuarter_game(Game quarter_game)
    {
	this.quarter_game = quarter_game;
    }

    public Game getSemi_game()
    {
	return semi_game;
    }

    public void setSemi_game(Game semi_game)
    {
	this.semi_game = semi_game;
    }

    public Game getThird_game()
    {
	return third_game;
    }

    public void setThird_game(Game third_game)
    {
	this.third_game = third_game;
    }

    public Game getFinals_game()
    {
	return finals_game;
    }

    public void setFinals_game(Game finals_game)
    {
	this.finals_game = finals_game;
    }
    
    
}
