package org.d3.core.packet;

import java.io.Serializable;

public class BasePacket implements Packet, Serializable{

	private String 		cid;
	private int 		act;
	private	int			act_min;
	private String		from;
	private String 		vs;
	private String 		target;
	private Object		tuple;
	private long 		timeStamp;
	
	public BasePacket(){}
	
	public BasePacket(String cid, int act, String vs, Object tuple) {
		super();
		this.cid = cid;
		this.act = act;
		this.vs = vs;
		this.tuple = tuple;
		this.timeStamp = System.currentTimeMillis();
	}
	
	public BasePacket(String cid, int act, int act_min, String from, String vs, Object tuple) {
		super();
		this.cid = cid;
		this.act = act;
		this.act_min = act_min;
		this.from = from;
		this.vs = vs;
		this.tuple = tuple;
		this.timeStamp = System.currentTimeMillis();
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getAct() {
		return act;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public String getVs() {
		return vs;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public Object getTuple() {
		return tuple;
	}

	public void setTuple(Object tuple) {
		this.tuple = tuple;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getAct_min() {
		return act_min;
	}

	public void setAct_min(int act_min) {
		this.act_min = act_min;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return "BasePacket [cid=" + cid + ", act=" + act + ", vs=" + vs
				+ ", source=" + tuple + ", timeStamp=" + timeStamp + "]";
	}
	
	private static final long serialVersionUID = -38147247115869567L;
	
}