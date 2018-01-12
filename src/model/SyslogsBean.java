package model;

public class SyslogsBean {
	private int id;
	int myId; //begin 1 and continous;
 	private int uniqid;
	private int size;
	private int used;
	private int isCurrent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUniqid() {
		return uniqid;
	}
	public void setUniqid(int uniqid) {
		this.uniqid = uniqid;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}
	public int getMyId() {
		return myId;
	}
	public void setMyId(int myId) {
		this.myId = myId;
	}
	
}
