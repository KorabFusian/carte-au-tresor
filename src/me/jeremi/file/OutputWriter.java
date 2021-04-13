package me.jeremi.file;

import me.jeremi.carte.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputWriter {

    public void writeCarteToFile(CarteAuTresor carte, String pathname) {
        if (!pathname.endsWith(".txt")) pathname += ".txt";
        try {
            File outputTxt = new File(pathname);
            boolean created = outputTxt.createNewFile();
            FileWriter writer = new FileWriter(pathname);
            writer.write(getCarteDescription(carte));
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur en écrivant le fichier de sortie.");
            e.printStackTrace();
        }
    }

    String getCarteDescription(CarteAuTresor carte) {
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
