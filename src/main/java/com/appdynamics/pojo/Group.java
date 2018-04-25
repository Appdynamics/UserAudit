package com.appdynamics.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@JsonPropertyOrder({"id", "name", "description", "userName", "roleName"})
public class Group {

	private String id;
	private String name;
	private String description;
	private String userName;
	private String roleName;

}
