package com.mainapp.model;

import java.util.List;

public class Hateoas {

	private List<Link> links;
	
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
	
	public String get(String key) {
		for(Link l: this.links) {
			if(l.getRel() != null && l.getRel().equals(key)) {
				return l.getHref();
			}
		}
		return "";
	}
	
}
