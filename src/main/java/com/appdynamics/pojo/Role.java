package com.appdynamics.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@JsonPropertyOrder({"id", "name", "permissionName", "permissionEntity", "entityId"})
public class Role {

	private String id;
	private String name;
	private String permissionName;
	private String permissionEntity;
	private String entityId;

}
