package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet d'exporter nos r�sultats dans un fichier csv
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class ExportCSV {
	/**
	 * Permet de mettre sous forme csv la quantit� de ressources utilis�es pour chaque machine
	 * @param instance l'instance de base g�n�r�e depuis un fichier
	 * @param path le chemin vers lequel le fichier csv est cr��
	 * @throws IOException Probl�me avec le fichier
	 */
	public void exportToCSVGraphMachine(Instance instance, String path) throws IOException {
		BufferedWriter sourceFile = new BufferedWriter(new FileWriter(path));
		
		//Pour chaque case du tableau de ressources utilis�es
		for(int k = 0; k < 1440; k++) {
			String line = "";
			//Pour chaque machine
			for(int i = 0; i < instance.getNbMachine(); i++) {
				//Pour chaque ressources
				for(int j = 0; j < instance.getNbResource(); j++) {
					//on r�cup�re la valeur
					line += String.valueOf(instance.getListMachines().get(i).getListResourcesUsed().get(j)[k]) + ";";
				}
				line += ";";
			}
			sourceFile.write(line);
			sourceFile.newLine();
			sourceFile.flush();
		}
		sourceFile.close();
	}
	
	/**
	 * Permet d'exporter sous forme d'un fichier .csv les r�sultat de l'algorith g�n�tique
	 * @param pathData chemin des instances
	 * @param pathExport chemin pour les r�sultats
	 * @param nbRep le nombre de fois on lancer notre algorithm g�n�tique
	 * @param maxIt le nombre de repetition dans l'algorithm g�n�tique
	 * @param sizePopulation le taille de la population
	 * @param mutationRate le pourcentage de mutation
	 * @param cloneRate le pourcentage de clones acceptable dans la population
	 * @throws IOException Probl�me avec le fichier
	 */
	public void exportToCSVResultInstance(String pathData, String pathExport, int nbRep, int maxIt, int sizePopulation, 
			double mutationRate, double cloneRate) throws IOException {
		List<String> listFile = new ArrayList<>();
		GeneticAlgorithm geneticAlgo = new GeneticAlgorithm();
		BufferedWriter sourceFile = new BufferedWriter(new FileWriter(pathExport));
		String line = "";
		
		int[] allResult = new int[nbRep];
		double[] allExecTime = new double[nbRep];
		
		//r�cup�ration de tous les fichiers dans le dossier de data
		listRepertory(new File(pathData), listFile);
//		System.out.println("Nb : " + String.valueOf(listFile.size()));
//		for(String pathFile : listFile) {
//			System.out.println(pathFile);
//		}
		
		line = "Instance;Nb jobs rejet�s;Temps d'exec";
		sourceFile.write(line);
		sourceFile.newLine();
		sourceFile.flush();
		for(String file : listFile) {
//		for(int j = 0; j < 1; j++) {
//			Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
			Instance instance = new Instance(file);
			for(int i = 0; i < nbRep; i++) {
				double start = System.currentTimeMillis();
				allResult[i] = geneticAlgo.algoTestFinal(instance, maxIt, sizePopulation, mutationRate, cloneRate);
				instance.cleanInstance();
				double time = ((System.currentTimeMillis() - start)) / 1000;
				allExecTime[i] = time;
			}
			double result = 0;
			double timeExec = 0;
			
			for(int oneResult : allResult) {
				result += oneResult;
//				System.out.println(oneResult);
			}
			result = result /nbRep;
			for(double oneTimeExec : allExecTime) {
				timeExec += oneTimeExec;
			}
			timeExec = timeExec/nbRep;
			
			line = file + ";" + String.valueOf((int)result) + ";" + String.valueOf(timeExec);
			sourceFile.write(line);
			sourceFile.newLine();
			sourceFile.flush();
			
		}
		sourceFile.close();
	}
	
	/**
	 * /**
	 * Permet d'exporter sous forme d'un fichier .csv la valeur minimum de t�ches rejet�s pour chaque it�ration
	 * @param pathData le chemin relatif du fichier du jeu de donn�es
	 * @param pathExport le chemin relatif pour l'export des resultats
	 * @param nbRep le nombre de fois on lancer notre algorithm g�n�tique
	 * @param maxIt le nombre de repetition dans l'algorithm g�n�tique
	 * @param sizePopulation le taille de la population
	 * @param mutationRate le pourcentage de mutation
	 * @param cloneRate le pourcentage de clones acceptable dans la population
	 * @throws IOException Probl�me avec le fichier
	 */
	public void exportToCSVGraphSol(String pathData, String pathExport, int nbRep, int maxIt, int sizePopulation, double mutationRate, double cloneRate) throws IOException{
		List<String> listFile = new ArrayList<>();
		List<int[]> listResult = new ArrayList<>();
		Graphic algoGraph = new Graphic();
		BufferedWriter sourceFile = new BufferedWriter(new FileWriter(pathExport));
		String line;
		
		listRepertory(new File(pathData), listFile);
		
		for(String file : listFile) {
			Instance instance = new Instance(file);
			for(int i = 0; i < nbRep; i++) {
				listResult.add(algoGraph.graphConvergenceCurve(instance, maxIt, sizePopulation, cloneRate, mutationRate));
				instance.cleanInstance();
			}
		}
		for(int j = 0; j < maxIt; j++) {
			line = "";
			for(int i = 0; i < nbRep; i++) {
				line += listResult.get(i)[j] + ";";
			}
			sourceFile.write(line);
			sourceFile.newLine();
			sourceFile.flush();
		}
		sourceFile.close();
	}
	/**
	 * Permet de remplir la liste de tous les fichiers dans un r�pertoire
	 * @param repertory le r�pertoire
	 * @param listFile la liste des fichiers
	 */
	public static void listRepertory (File repertory, List<String> listFile) { 
		if(repertory.isFile()) {
			listFile.add(repertory.getPath());
		}
 
	    if (repertory.exists() && repertory.isDirectory()) { 
	        File[] files = repertory.listFiles(); 
	        for(File file : files) {
	        	listRepertory(file, listFile);
	        }
	         
	    }  
	}
}
