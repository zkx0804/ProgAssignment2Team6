package dataManage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import entities.Paragraph;

public class ParagraphsIndexer {

	public ParagraphsIndexer() {

	}

	private IndexWriter indexWriter = null;

	public IndexWriter getIndexWriter(boolean create) throws IOException {
		if (indexWriter == null) {
			Directory indexDir = FSDirectory.open(Paths.get("./DataSet/Index/"));
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

			if (create) {
				// Create a new index, remove any previously index doc.
				config.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new doc to existing index.
				config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			indexWriter = new IndexWriter(indexDir, config);

		}
		return indexWriter;
	}

	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
			System.out.println("IndexWriter closed.");
		}
	}

	public void indexParagraph(Paragraph p) throws IOException {
		if (p != null) {
			// System.out.println("Indexing paragraph -->ID: " + p.getParaID());
			IndexWriter writer = getIndexWriter(false);
			Document doc = new Document();
			doc.add(new StringField("id", p.getParaID(), Store.YES));
			doc.add(new TextField("text", p.getParaText(), Store.YES));

			doc.add(new TextField("content", p.getParaText(), Store.NO));

			writer.addDocument(doc);
		} else {
			System.out.println("Obj Paragraph is empty. Skipepd");
		}

	}

	public void rebuildIndexes(ArrayList<Paragraph> paraList) throws IOException {
		getIndexWriter(true);

		if (!paraList.isEmpty()) {
			for (Paragraph p : paraList) {
				indexParagraph(p);
			}
			closeIndexWriter();
		} else {
			System.out.println("No Paragraphs found. Exit.");
		}
	}

}
