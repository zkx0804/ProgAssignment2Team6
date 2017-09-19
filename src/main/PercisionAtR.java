package main;

import static java.lang.Math.min;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import dataManage.ReadDataSet;
import entities.RankDoc;

public class PercisionAtR {

	public static void main(String[] args) {

		System.out.println("Start reading data set...");
		System.setProperty("file.encoding", "UTF-8");

		String booleanStr = args[0];
		Boolean useDefaultEngine = true;

		try {
			useDefaultEngine = Boolean.valueOf(booleanStr);
		} catch (Throwable e) {
			System.out.println("Can't parse input value, run using default engine.");
			useDefaultEngine = true;
		}

		HashMap<String, ArrayList<RankDoc>> rankedMap = GetRankingResult.getRankingResultMap(useDefaultEngine);

		TreeMap<String, ArrayList<String>> relevantDocMap = ReadDataSet.getAllrelevantWithQueryId();

		HashMap<String, Double> pAtRByQuery = new HashMap<String, Double>();
		System.out.println("QueryId     |     RPec");
		for (String queryId : relevantDocMap.keySet()) {
			if (rankedMap.get(queryId) != null && relevantDocMap.get(queryId) != null) {

				double pAtR = getPrecisionAtR(rankedMap.get(queryId), relevantDocMap.get(queryId));

				pAtRByQuery.put(queryId, pAtR);
				System.out.println(queryId + "     |     " + pAtR);

			} else {
				System.out.println("There are no ranked documents or relevant documents for query: " + queryId);
			}

		}

		wirteResultToFile(pAtRByQuery, useDefaultEngine);

	}

	public static double getPrecisionAtR(ArrayList<RankDoc> rankedList, ArrayList<String> relevantDocList) {

		int tp_count = 0;
		double prec = 0;
		int r_number = relevantDocList.size();

		if (r_number > 0) {
			List<RankDoc> firstRDocs = rankedList.subList(0, min(rankedList.size(), r_number));

			for (RankDoc doc : firstRDocs) {
				if (relevantDocList.contains(doc.getParahId())) {
					tp_count++;
				}
			}
			prec = (double) tp_count / r_number;

		}

		return prec;
	}

	public static void wirteResultToFile(HashMap<String, Double> resultMap, boolean useDefaultEngine) {
		if (!resultMap.isEmpty()) {
			String fileName = "result.txt";
			if (useDefaultEngine) {
				fileName = "result_RPrec_defaultScoreFunc.txt";
			} else {
				fileName = "result_RPrec_customScoreFun.txt";
			}
			String filePath = "./" + fileName;
			BufferedWriter bWriter = null;
			FileWriter fWriter = null;

			try {
				fWriter = new FileWriter(filePath);
				bWriter = new BufferedWriter(fWriter);

				for (String queryId : resultMap.keySet()) {

					String line = String.format("%-40s %20s \r\n", queryId, resultMap.get(queryId));
					bWriter.write(line);
					bWriter.newLine();
				}

				System.out.println("Write all ranking result to file: " + fileName);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bWriter != null) {
						bWriter.close();
					}
					if (fWriter != null) {
						fWriter.close();
					}
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}

		}

	}
}
