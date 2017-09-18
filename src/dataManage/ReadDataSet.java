package dataManage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import co.nstant.in.cbor.CborException;
import edu.unh.cs.treccar.Data;
import edu.unh.cs.treccar.read_data.DeserializeData;
import entities.Page;
import entities.Paragraph;

public class ReadDataSet {

	// Only used for testing reading data set.
	// public static void main(String[] args) throws Exception {
	// System.out.println("Start reading data set...");
	// System.setProperty("file.encoding", "UTF-8");
	//
	// readingDataFiles();
	//
	// // ArrayList<Paragraph> list = new ArrayList<Paragraph>();
	// // list = getAllParagraphFromDataSet();
	// // System.out.println("List size: " + list.size());
	// // System.out.println(list);
	//
	// }

	// Get all pages data as object list.
	public static ArrayList<Page> getAllPagesFromDataSet() {
		ArrayList<Page> pageList = new ArrayList<Page>();

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

	// Only for test.
	public static void readingDataFiles() throws CborException {
		File file = new File("./DataSet/test200/train.test200.cbor.paragraphs");
		FileInputStream fis = null;
		Data.Paragraph testP = null;
		try {
			fis = new FileInputStream(file);
			System.out.println("Total file size to read (in bytes) : " + fis.available());

			for (Data.Paragraph p : DeserializeData.iterableParagraphs(fis)) {
				System.out.println(p.getParaId());
				// System.out.print(p.getEntitiesOnly());

				// System.out.println(p.getBodies());
				System.out.println();

				if (p.getParaId().equalsIgnoreCase("fee82b9f00a6f7b83b9611fcdb4d11fb091d930a")) {
					testP = p;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Find match:");
		System.out.println(testP.getTextOnly());
		System.out.println("============");
		System.out.println(testP.getBodies());
	}

}
