package com.appdynamics.util;

import com.appdynamics.pojo.Group;
import com.appdynamics.pojo.Role;
import com.appdynamics.pojo.User;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.io.FileUtils;

public class CSVWrapper {

	public static void buildReport(List users, List roles, List groups) {
		CsvMapper mapper = new CsvMapper();

		CsvSchema userSchema = mapper.schemaFor(User.class).withHeader();
		CsvSchema roleSchema = mapper.schemaFor(Role.class).withHeader();
		CsvSchema groupSchema = mapper.schemaFor(Group.class).withHeader();

		try {
			String usersCSVString = mapper.writer(userSchema).writeValueAsString(users);
			String rolesCSVString = mapper.writer(roleSchema).writeValueAsString(roles);
			String groupCSVString = mapper.writer(groupSchema).writeValueAsString(groups);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

			FileUtils.writeStringToFile(new File("output/" + sdf.format(timestamp) + "-users.csv"), usersCSVString, "UTF-8");
			FileUtils.writeStringToFile(new File("output/" + sdf.format(timestamp) + "-groups.csv"), rolesCSVString, "UTF-8");
			FileUtils.writeStringToFile(new File("output/" + sdf.format(timestamp) + "-roles.csv"), groupCSVString, "UTF-8");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
