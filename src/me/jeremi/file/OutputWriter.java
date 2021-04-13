package me.jeremi.file;

import me.jeremi.carte.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Permet d'écrire la description d'une carte dans un fichier texte.
 * @author Jeremi Friggit (@KorabFusian)
 * @see CarteAuTresor
 */
public class OutputWriter {

    /**
     * Ecrit la description d'une carte dans un fichier.
     * @param carte la CarteAuTresor à écrire
     * @param pathname le chemin vers le fichier
     */
    public void writeCarteToFile(CarteAuTresor carte, String pathname) {
        if (!pathname.endsWith(".txt")) pathname += ".txt";
        try {
            File outputTxt = new File(pathname);
            boolean created = outputTxt.createNewFile();
            FileWriter writer = new FileWriter(pathname);
            writer.write(describeCarteAuTresor(carte));
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur en écrivant le fichier de sortie.");
            e.printStackTrace();
        }
    }

    /**
     * Génère la description d'une CarteAuTresor dans le bon format.
     * @param carte la CarteAuTresor à décrire
     * @return la description de la carte en tant que String.
     */
    String describeCarteAuTresor(CarteAuTresor carte) {
        StringBuilder str = new StringBuilder(
                "C - " + carte.getCarte().get(0).size() + " - " + carte.getCarte().size());

        for (Montagne montagne : carte.getMontagnes()) {
            str.append("\n").append(montagne.toString());
        }

        for (Tresor tresor : carte.getTresors()) {
            // On n'écrit plus les trésors vides
            if(tresor.getTresor() > 0)  str.append("\n").append(tresor);
        }

        for (Aventurier aventurier : carte.getAventuriers()) {
            str.append("\n").append(aventurier.toString());
        }

        return str.toString();
    }
}
