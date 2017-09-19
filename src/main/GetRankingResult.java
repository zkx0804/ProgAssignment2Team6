package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import dataManage.ParagraphsIndexer;
import dataManage.ReadDataSet;
import dataManage.SearchEngine;
import entities.Page;
import entities.Paragraph;
import entities.RankDoc;

public class GetRankingResult {

	private static String default_teamName = "Team6";
	private static int default_size = 100;

	/*
	 * Main class for Question 1;
	 * 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start reading data set...");
		System.setProperty("file.encoding", "UTF-8");

		String booleanStr = args[0];
		Boolean useDefaultEngine = true;
		String method_name = "";

		ArrayList<RankDoc> rankResult = new ArrayList<RankDoc>();
		try {
			useDefaultEngine = Boolean.valueOf(booleanStr);
		} catch (Throwable e) {
			System.out.println("Can't parse input value, run using default engine.");
			useDefaultEngine = true;
		}

		ArrayList<Page> pageList = ReadDataSet.getAllPagesFromDataSet();
		ArrayList<Paragraph> dataList = ReadDataSet.getAllParagraphFromDataSet();

		try {
			ParagraphsIndexer indexer = new ParagraphsIndexer();
			indexer.rebuildIndexes(dataList);

			if (useDefaultEngine) {
				System.out.println("Use Lucene Default Scoring Function...");
				method_name = "Default";
			} else {
				System.out.println("Use Custom Scoring Function...");
				method_name = "Custom";
			}

			for (Page page : pageList) {
				System.out.println("Search with search query ===> " + page.getPageName());

				String queryStr = page.getPageName();
				SearchEngine se = new SearchEngine(useDefaultEngine);
				TopDocs topDocs = se.performSearch(queryStr, default_size);
				System.out.println("Result found: " + topDocs.totalHits);

				ScoreDoc[] hits = topDocs.scoreDocs;
				for (int i = 0; i < hits.length; i++) {
					Document doc = se.getDocument(hits[i].doc);

					RankDoc rank = new RankDoc();
					rank.setQueryId(page.getPageId());
					rank.setParahId(doc.get("id"));
					rank.setRank(i + 1);
					rank.setScore(hits[i].score);
					String nameStr = default_teamName + "-" + method_name;
					rank.setTeamMethodName(nameStr);

					rankResult.add(rank);
				}
				System.out.println("Search done");
				System.out.print("==========================");
			}

			System.out.println("All Search Done! Get " + rankResult.size() + " rankings in total.");

			wirteResultToFile(rankResult, useDefaultEngine);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	/*
	 * Return a HashMap for all rank result, use queryId for key.
	 * 
	 */
	public static HashMap<String, ArrayList<RankDoc>> getRankingResultMap(boolean useDefaultEngine) {

		// <pageId/queryId, list of ranked doc>
		HashMap<String, ArrayList<RankDoc>> resultMap = new HashMap<String, ArrayList<RankDoc>>();

		String method_name = "";

		ArrayList<Page> pageList = ReadDataSet.getAllPagesFromDataSet();
		ArrayList<Paragraph> dataList = ReadDataSet.getAllParagraphFromDataSet();

		try {
			ParagraphsIndexer indexer = new ParagraphsIndexer();
			indexer.rebuildIndexes(dataList);

			if (useDefaultEngine) {
				System.out.println("Use Lucene Default Scoring Function...");
				method_name = "Default";
			} else {
				System.out.println("Use Custom Scoring Function...");
				method_name = "Custom";
			}

			for (Page page : pageList) {
				System.out.println("Search with search query ===> " + page.getPageName());

				ArrayList<RankDoc> rankResult = new ArrayList<RankDoc>();

				String queryStr = page.getPageName();
				SearchEngine se = new SearchEngine(useDefaultEngine);
				TopDocs topDocs = se.performSearch(queryStr, default_size);
				System.out.println("Result found: " + topDocs.totalHits);

				ScoreDoc[] hits = topDocs.scoreDocs;
				for (int i = 0; i < hits.length; i++) {
					Document doc = se.getDocument(hits[i].doc);

					RankDoc rank = new RankDoc();
					rank.setQueryId(page.getPageId());
					rank.setParahId(doc.get("id"));
					rank.setRank(i + 1);
					rank.setScore(hits[i].score);
					String nameStr = default_teamName + "-" + method_name;
					rank.setTeamMethodName(nameStr);

					rankResult.add(rank);
				}

				if (!resultMap.containsKey(page.getPageId())) {
					resultMap.put(page.getPageId(), rankResult);
				} else {
					ArrayList<RankDoc> exisitingRank = resultMap.get(page.getPageId());
					exisitingRank.addAll(rankResult);
					resultMap.put(page.getPageId(), exisitingRank);
				}
				System.out.println("Search done. Get " + rankResult.size() + " result for " + page.getPageId());
				System.out.print("==========================");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	public static void wirteResultToFile(ArrayList<RankDoc> resultList, boolean useDefaultEngine) {
		if (!resultList.isEmpty()) {
			String fileName = "result.qrels";
			if (useDefaultEngine) {
				fileName = "result-defaultScoreFunc.qrels";
			} else {
				fileName = "result-customScoreFun.qrels";
			}
			String filePath = "./" + fileName;
			BufferedWriter bWriter = null;
			FileWriter fWriter = null;

			try {
				fWriter = new FileWriter(filePath);
				bWriter = new BufferedWriter(fWriter);

				for (RankDoc rank : resultList) {
					// String line = String.format("%-20s %5s %20s %10s %10s
					// %20s \r\n", rank.getQueryId(), "Q0",
					// rank.getParahId(), rank.getRank(), rank.getScore(),
					// rank.getTeamMethodName());

					String space = " ";
					String line = rank.getQueryId() + space + "Q0" + space + rank.getParahId() + space + rank.getRank()
							+ space + rank.getScore() + space + rank.getTeamMethodName();
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
