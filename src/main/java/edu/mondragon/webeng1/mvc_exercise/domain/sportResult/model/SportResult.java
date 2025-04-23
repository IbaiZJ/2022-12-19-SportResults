package edu.mondragon.webeng1.mvc_exercise.domain.sportResult.model;

public class SportResult implements java.io.Serializable {
    private static final long serialVersionUID = 3834633933831160740L;

    private Integer sportResultId;
    private String team1Name;
    private Integer team1Result;
    private String team2Name;
    private Integer team2Result;

    public SportResult() {
        sportResultId = 0;
    }

    public Integer getSportResultId() {
        return sportResultId;
    }

    public void setSportResultId(Integer sportResultId) {
        this.sportResultId = sportResultId;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public Integer getTeam1Result() {
        return team1Result;
    }

    public void setTeam1Result(Integer team1Result) {
        this.team1Result = team1Result;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public Integer getTeam2Result() {
        return team2Result;
    }

    public void setTeam2Result(Integer team2Result) {
        this.team2Result = team2Result;
    }

}
