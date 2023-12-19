package eclipse_projet_bdda_chaabnia_fekihhassen_benmansour_nadarajah;

import java.io.IOException;
import java.util.ArrayList;

public class CreateTableCommand {
  private String nom_relation;
  private ArrayList<ColInfo> colInfoList;
  FileManager fileManager = FileManager.getInstance();

  public CreateTableCommand(String command) {
    try {
      String[] cmd = command.split(" ");
      if (cmd.length < 4) {
        throw new IllegalArgumentException("Commande CREATE TABLE incomplète");
      }
      this.nom_relation = cmd[2];
      this.colInfoList = new ArrayList<>();
      String[] columns = cmd[3].substring(1, cmd[3].length() - 1).split(",");
      for (String column : columns) {
        String[] colInfo = column.split(":");
        if (colInfo.length != 2) {
          throw new IllegalArgumentException("Format incorrect pour la définition des colonnes");
        }
        String colName = colInfo[0];
        String colType = colInfo[1];
        if (!colType.equalsIgnoreCase("INT") && !colType.equalsIgnoreCase("FLOAT")
            && !colType.equalsIgnoreCase("STRING") && !colType.toUpperCase().startsWith("VARSTRING")) {
          throw new IllegalArgumentException("Type de colonne non supporté : " + colType);
        }
        ColInfo col = new ColInfo(colName, colType);
        colInfoList.add(col);
      }
    } catch (Exception e) {
      System.out.println("Erreur lors du parsing de la commande : " + e.getMessage());
    }
  }

  public void execute() {
    DataBaseInfo databaseInfo = DataBaseInfo.getInstance();
    if (databaseInfo.tableExists(nom_relation)) {
      System.out.println("Relation \"" + nom_relation + "\" already exists.");
      return;
    }
    try {
      PageId headerPageId = fileManager.createNewHeaderPage();
      TableInfo tableInfo = new TableInfo(nom_relation, colInfoList, headerPageId);
      databaseInfo.addTableInfo(tableInfo);
      System.out.println("Table \"" + nom_relation + "\" has been created successfully.");
      tableInfo.printTableInfo();
    } catch (IOException | PageNotFoundException e) {
      System.out.println("Erreur lors de la création de la table : " + e.getMessage());
    }
  }
}