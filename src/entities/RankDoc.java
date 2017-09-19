package entities;

public class RankDoc {
	private String queryId;
	private String parahId;
	private Integer rank;
	private Float score;
	private String teamMethodName;

	public RankDoc() {

	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getParahId() {
		return parahId;
	}

	public void setParahId(String parahId) {
		this.parahId = parahId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getTeamMethodName() {
		return teamMethodName;
	}

	public void setTeamMethodName(String teamMethodName) {
		this.teamMethodName = teamMethodName;
	}

	public String toString() {
		return "";
	}

}
