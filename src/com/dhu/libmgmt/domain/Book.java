package com.dhu.libmgmt.domain;

public class Book {
	private int id;

	private String name, publisher, author, status;
	public static String BORROWED = "已借出";
	public static String UNBORROWED = "未借出";

	public Book(int id, String name, String author, String publisher,
			String status) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.status = status;
	}

	public String toString() {
		return String.format("%s\t%s\t%s\t%s", id, name, author, publisher,
				status);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
