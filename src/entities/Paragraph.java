package entities;

public class Paragraph {
	private String paraID;
	private String paraText;
	private String note;

	public Paragraph() {

	}

	public Paragraph(String paraID, String paraText) {
		this.paraID = paraID;
		this.paraText = paraText;
	}

	public Paragraph(String paraID, String paraText, String note) {
		this.paraID = paraID;
		this.paraText = paraText;
		this.note = note;
	}

	public String getParaID() {
		return this.paraID;
	}

	public void setParaID(String paraID) {
		this.paraID = paraID;
	}

	public String getParaText() {
		return this.paraText;
	}

	public void setParaText(String text) {
		this.paraText = text;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String toString() {
		return "ID: " + paraID + "\n\n" + "Text: " + paraText;
	}
}
