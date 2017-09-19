package dataManage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import co.nstant.in.cbor.CborException;
import edu.unh.cs.treccar.Data;
import edu.unh.cs.treccar.read_data.DeserializeData;
import entities.Page;
import entities.Paragraph;

public class ReadDataSet {

	// Only used for testing reading data set.
	public static void main(String[] args) throws Exception {
		System.out.println("Start reading data set...");
		System.setProperty("file.encoding", "UTF-8");

		// ArrayList<Page> list = new ArrayList<Page>();

		// list = getAllPagesFromDataSet();

		// for (Page p : list) {
		// System.out.println(p.getPageId() + " " + p.getPageName());
		// }

		TreeMap<String, ArrayList<String>> result = getAllrelevantWithQueryId();

		System.out.println(result);
	}

	// Get all pages data as object list.
	public static ArrayList<Page> getAllPagesFromDataSet() {
		ArrayList<Page> pageList = new ArrayList<Page>();
		String dataFilePath = "./DataSet/test200/train.test200.cbor.outlines";

		FileInputStream stream = readingDataFiles(dataFilePath);

		try {
			for (Data.Page dataP : DeserializeData.iterableAnnotations(stream)) {
				// System.out.println(dataP.getParaId());
				// System.out.print(dataP.getEntitiesOnly());
				// System.out.println(dataP.getTextOnly());
				// System.out.println();
				Page p = new Page();
				p.setPageId(dataP.getPageId());
				p.setPageName(dataP.getPageName());
				pageList.add(p);

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("Get " + pageList.size() + " pages in total by Treccar-tool.");

		return pageList;
	}

	// Get all paragraph data as object list.
	public static ArrayList<Paragraph> getAllParagraphFromDataSet() {
		ArrayList<Paragraph> pList = new ArrayList<Paragraph>();
		String dataFilePath = "./DataSet/test200/train.test200.cbor.paragraphs";

		FileInputStream stream = readingDataFiles(dataFilePath);
		try {
			for (Data.Paragraph dataP : DeserializeData.iterableParagraphs(stream)) {
				// System.out.println(dataP.getParaId());
				// System.out.print(dataP.getEntitiesOnly());
				// System.out.println(dataP.getTextOnly());
				// System.out.println();
				Paragraph p = new Paragraph();
				p.setParaID(dataP.getParaId());
				p.setParaText(dataP.getTextOnly());
				pList.add(p);

			}
		} catch (CborException e) {
			e.printStackTrace();
		}
		System.out.println("Get " + pList.size() + " paragraphs in total by Treccar-tool.");
		return pList;
	}

	// Use TreeMap for sorted key.
	public static TreeMap<String, ArrayList<String>> getAllrelevantWithQueryId() {
		TreeMap<String, ArrayList<String>> result = new TreeMap<String, ArrayList<String>>();

		String dataFilePath = "./DataSet/test200/train.test200.cbor.article.qrels";
		BufferedReader reader = null;
		try {
			FileInputStream stream = readingDataFiles(dataFilePath);
			reader = new BufferedReader(new InputStreamReader(stream));

			String line = reader.readLine();
			while (line != null) {
				String[] strList = line.split("\\s");
				String queryId = strList[0];
				String docId = strList[2];
				int isRelevant = Integer.valueOf(strList[3]);

				if (result.containsKey(queryId)) {
					if (isRelevant > 0) {
						ArrayList<String> relevDocs = result.get(queryId);
						relevDocs.add(docId);
						result.put(queryId, relevDocs);
					}
				} else {
					ArrayList<String> relevDocs = new ArrayList<String>();
					if (isRelevant > 0) {
						relevDocs.add(docId);
					}
					result.put(queryId, relevDocs);
				}

				line = reader.readLine();
			}

		} catch (Throwable e) {

		}

		return result;
	}

	public static FileInputStream readingDataFiles(String dataFilesPath) {
		File file = new File(dataFilesPath);
		try {
			FileInputStream fis = new FileInputStream(file);
			System.out.println("Total file size to read (in bytes) : " + fis.available());
			return fis;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}

	}

}
