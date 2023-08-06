package com.epam.esm.entity;

import java.util.Objects;

public class SearchCriteria {

	private String tag;
	private String name;
	private String description;

	public SearchCriteria() {
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SearchCriteria that = (SearchCriteria) o;
		return Objects.equals(tag, that.tag) &&
				Objects.equals(name, that.name) &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tag, name, description);
	}

	@Override
	public String toString() {
		return "SearchCriteria{" +
				"tag='" + tag + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
