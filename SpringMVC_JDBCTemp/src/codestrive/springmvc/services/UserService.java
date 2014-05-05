package codestrive.springmvc.services;

import java.util.List;

import codestrive.springmvc.domain.User;

public interface UserService {
	public void insertData(User user);

	public List<User> getUserList();

	public void deleteData(String id);

	public User getUser(String id);

	public void updateData(User user);
}
