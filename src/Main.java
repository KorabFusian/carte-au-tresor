public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
    	inputParser.parseFile("exemple.txt");
	    CarteAuTresor carte = new CarteAuTresor(3, 4);
	    carte.add("M", 1, 0);
	    carte.add("M", 2, 1);
	    carte.add("T", 0, 3);
		carte.add("T", 1, 3);
		carte.add("A", 1, 1);
	    // carte.print();
    }
}
