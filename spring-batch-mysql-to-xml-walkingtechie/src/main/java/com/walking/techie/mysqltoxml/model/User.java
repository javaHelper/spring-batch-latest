package com.walking.techie.mysqltoxml.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User u")
})
@Table(name = "user")
@Data
@Entity
public class User {

  @Id
  int id;
  String username;
  String password;
  int age;
}
