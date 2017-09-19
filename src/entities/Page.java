package entities;

public class Page {
	private String pageId;
	private String pageName;

	public Page() {
	}

	public Page(String id, String name) {
		this.setPageId(id);
		this.setPageName(name);
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
