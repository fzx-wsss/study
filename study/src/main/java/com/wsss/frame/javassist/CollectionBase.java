package com.wsss.frame.javassist;


public class CollectionBase<T> {
    /**
     * 注入实体单元
     */
    @Context(unitName="collection-entity")
    protected String em;

	public void setEm(String em) {
		this.em = em;
	}
    
    
}

