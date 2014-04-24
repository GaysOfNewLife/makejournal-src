package cn.com.makejournal.newlife.platform.commons.entity;

public class Tree {

	private String id;
	private String pId;
	private String name;
	private boolean open;

	public Tree(String id, String pId, String name, boolean open) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
