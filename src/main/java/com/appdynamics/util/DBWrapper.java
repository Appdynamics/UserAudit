package com.appdynamics.util;

import com.appdynamics.pojo.Group;
import com.appdynamics.pojo.Role;
import com.appdynamics.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBWrapper {

	private Connection conn;

	private String host;
	private String username;
	private String password;
	private String accountName;

	private Map<Queries, String> queries = new HashMap<>();

	private enum Queries {
		GET_USERS,
		GET_ROLES,
		GET_GROUPS
	}

	public DBWrapper(String host, String username, String password, String accountName) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.accountName = accountName;

		queries.put(Queries.GET_USERS,
				"SELECT * " +
				"FROM   user, " +
				"       user_account_role_mapping, " +
				"       account_role, " +
				"       account " +
				"WHERE  user.id = user_account_role_mapping.user_id " +
				"       AND account_role.id = user_account_role_mapping.account_role_id " +
				"       AND account_role.account_id = account.id " +
				"       AND account.NAME = '" + this.accountName + "' " +
				"ORDER  BY user.name, " +
				"          account_role.name ASC;");
		queries.put(Queries.GET_ROLES,
				"SELECT * " +
				"FROM   account_role, " +
				"       permission " +
				"WHERE  account_role.id = permission.account_role_id " +
				"ORDER  BY account_role.name ASC;");
		queries.put(Queries.GET_GROUPS,
				"SELECT * " +
				"FROM   user_group, " +
				"       user, " +
				"       user_user_group_mapping, " +
				"       user_group_account_role_mapping, " +
				"       account_role " +
				"WHERE  user_group.id = user_user_group_mapping.user_group_id " +
				"       AND user_user_group_mapping.user_id = USER.id " +
				"       AND user_group_account_role_mapping.account_role_id = account_role.id " +
				"       AND user_group_account_role_mapping.user_group_id = user_group.id " +
				"ORDER  BY user_group.name, " +
				"          user.name ASC;");
	}

	public boolean connectDB() {
		System.out.println("Connecting to DB");
		String url = "jdbc:mysql://" + host + "/controller";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
		} catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void disconnectDB() {
		try {
			System.out.println("Disconnecting from DB");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			System.out.println("Gathering user info");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(queries.get(Queries.GET_USERS));
			while (rs.next()) {
				User user = new User();
				user.setAccountId(rs.getString("user.account_id"));
				user.setId(rs.getString("id"));
				user.setDisplayName(rs.getString("user.display_name"));
				user.setName(rs.getString("user.name"));
				user.setEmail(rs.getString("user.email"));
				user.setId(rs.getString("user.id"));
				user.setRoleName(rs.getString("account_role.name"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>();
		try {
			System.out.println("Gathering role info");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(queries.get(Queries.GET_ROLES));
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getString("account_role.id"));
				role.setName(rs.getString("account_role.name"));
				role.setPermissionName(rs.getString("permission_action"));
				role.setPermissionEntity(rs.getString("entity_type"));
				role.setEntityId(rs.getString("entity_id"));
				roles.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}

	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<>();
		try {
			System.out.println("Gathering group info");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(queries.get(Queries.GET_GROUPS));
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getString("user_group.id"));
				group.setName(rs.getString("user_group.name"));
				group.setDescription(rs.getString("user_group.description"));
				group.setRoleName(rs.getString("account_role.name"));
				group.setUserName(rs.getString("user.name"));
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

}
