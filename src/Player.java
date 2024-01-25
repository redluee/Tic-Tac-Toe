
public class Player {
	private String name;
	private int totalWins;
	private String sign;
	
	public Player(String name, String sign) {
		this.name = name;
		this.sign = sign;
		totalWins = 0;
	}
	
	public String getName() {
		return name;
	}

	public String getSign() {
		return sign;
	}
	
	public int getTotalWins() {
		return totalWins;
	}

	public void addWin() {
		this.totalWins ++;
	}

}
