package main;

import java.io.IOException;
import java.util.Scanner;

import model.Algorithm;
import model.ExportCSV;
import model.GeneticAlgorithm;
import model.Instance;
/**
 * Dans cette classe nous laçons l'exécution de notre algorithme génétique et l'exportation des résultats dans un fichier csv.
 * @author martindlfuente
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		GeneticAlgorithm geneticAlgo = new GeneticAlgorithm();
		Algorithm algo = new Algorithm();
		ExportCSV csv = new ExportCSV();
		String pathData = "data/100";
		String pathExport = "csv/testResultFinal100.csv";
		double debut = System.currentTimeMillis();
		
		System.out.println("saisir le nombre de répétition de l'algoritme génétique :");
		Scanner sc = new Scanner(System.in);
		int nbRep = sc.nextInt();
		System.out.println("saisir le nombre d'itérations dans l'algo génétique :");
		int maxIt = sc.nextInt();
		System.out.println("saisir la taille de la population :");
		int sizePop = sc.nextInt();
		System.out.println("saisir la proba de mutation :");
		double mutation = sc.nextDouble();
		System.out.println("saisir la proba de clones :");
		double clone = sc.nextDouble();
		
		csv.exportToCSVGraphSol("data/20/3/2/instance0-20-3-2.data", "csv/tesResultGraph.csv", nbRep, maxIt, sizePop, mutation, clone);
		
		double time = ((System.currentTimeMillis()-debut)) / 1000;
		System.out.println("Temps d'exécution: " + String.valueOf(time) + "s");
	}
}
