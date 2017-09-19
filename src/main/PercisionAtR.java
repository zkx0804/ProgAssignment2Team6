package main;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataManage.ReadDataSet;
import entities.RankDoc;

public class PercisionAtR {

	public static void main(String[] args) {

		HashMap<String, ArrayList<RankDoc>> resultList = GetRankingResult.getRankingResultMap(true);

		HashMap<String, ArrayList<String>> relevantDocMap = ReadDataSet.getAllrelevantWithQueryId();

	}

	public static float getPrecisionAtR(int r_number, String queryId, HashMap<String, ArrayList<RankDoc>> resultMap,
			HashMap<String, ArrayList<String>> relevantDocMap) {

		int tp_count = 0;
		float prec = 0;
		List<RankDoc> docList = resultMap.get(queryId);

		ArrayList<String> relvantIdList = relevantDocMap.get(queryId);

		if (relvantIdList.size() > 0) {
			List<RankDoc> firstRDocs = docList.subList(0, min(docList.size(), r_number));

			for (RankDoc doc : firstRDocs) {
				if (relvantIdList.contains(doc.getParahId())) {
					tp_count++;
				}
			}
			prec = tp_count / r_number;

		}

		return prec;
	}
}
