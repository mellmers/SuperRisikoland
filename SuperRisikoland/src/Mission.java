public class Mission {
	
	private String[] mission = new String[14];
	
	public Mission () {
		mission[0] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!";
		mission[1] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!";
		mission[2] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!";
		mission[3] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!";
		mission[4] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!";
		mission[5] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[6] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[7] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[8] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[9] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[10] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[11] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[12] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
		mission[13] = "Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl";
	}
	
	public String getMission() {
		int i = (int) (Math.random()*13+1);
		return mission[i];
	}
}
