package com.marcs.service.enums;

public enum StoreRegion {
	EAST(1), WEST(2), SOUTH(3), SOUTHCENTRAL(4), ALL(5), NONE(6);

	private int id;

	StoreRegion(int type) {
		this.id = type;
	}

	public int id() {
		return id;
	}

    public static StoreRegion getRegion(int id) {
        for(StoreRegion r : StoreRegion.values()) if(r.id == id) return r;
        return NONE;
    }
}
