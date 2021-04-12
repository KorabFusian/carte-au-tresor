public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
	    CarteAuTresor carte = inputParser.parseFile("exemple.txt");
	    carte.mouvementAventuriers();
	    carte.print();
    }
}
