package eclipse_projet_bdda_chaabnia_fekihhassen_benmansour_nadarajah;

import java.io.*;
import java.util.ArrayList;

public class DataBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L; // Ajout de la version de sérialisation
	private static DataBaseInfo instance; // Instance unique

	private ArrayList<TableInfo> tab;
	private int compteur;

	private DataBaseInfo(int compteur) {
		// Taille à vérifier
		tab = new ArrayList<>();
		// Initialisation requise ou initialiser à 0;
		this.setCompteur(compteur);
	}

	// Méthode pour obtenir l'instance unique de DataBaseInfo
	public static DataBaseInfo getInstance() {
		if (instance == null) {
			instance = new DataBaseInfo(0);
		}
		return instance;
	}

	public ArrayList<TableInfo> getTab() {
		return tab;
	}

	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	public void AddTableInfo(TableInfo t) {
		tab.add(t);
		compteur++;
	}

	public TableInfo GetTableInfo(String nom_table) {
		for (TableInfo table : tab) {
			if (table.getNom_relation().equals(nom_table)) {
				return table;
			}
		}
		return null;
	}

	public void Init() {
		File file = new File("DB/DBInfo.save");

		if (file.exists()) {
			try (FileInputStream fic = new FileInputStream(file);
					ObjectInputStream obj = new ObjectInputStream(fic)) {
				DataBaseInfo dbInfo = (DataBaseInfo) obj.readObject();
				this.tab = dbInfo.getTab();
				this.compteur = dbInfo.getCompteur();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			// Fichier DBInfo.save n'existe pas, rien à remplir
		}
	}

	public void Finish() {
		try {
			File file = new File("DB/DBInfo.save");
			try (FileOutputStream fileOutputStream = new FileOutputStream(file);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
				objectOutputStream.writeObject(this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// A TESTER
}