package com.appdynamics.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@JsonPropertyOrder({"accountId", "id", "name", "displayName", "email", "roleName"})
public class User {

	private String accountId;
	private String id;
	private String name;
	private String displayName;
	private String email;
	private String roleName;

}
