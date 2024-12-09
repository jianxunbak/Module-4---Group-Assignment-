package group3.group3_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import group3.group3_assignment.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
