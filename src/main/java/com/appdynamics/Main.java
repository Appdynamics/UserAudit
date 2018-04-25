package com.appdynamics;

import com.appdynamics.pojo.Group;
import com.appdynamics.pojo.Role;
import com.appdynamics.pojo.User;
import com.appdynamics.util.DBWrapper;
import com.appdynamics.util.CSVWrapper;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		String controllerUrl = args[0];
		String username = args[1];
		String password = args[2];
		String accountName = args[3];

		DBWrapper dbWrapper = new DBWrapper(controllerUrl, username, password, accountName);

		if(dbWrapper.connectDB()) {
			List<User> users = dbWrapper.getUsers();
			List<Role> roles = dbWrapper.getRoles();
			List<Group> groups = dbWrapper.getGroups();

			dbWrapper.disconnectDB();

			CSVWrapper.buildReport(users, roles, groups);
		}
	}

}
