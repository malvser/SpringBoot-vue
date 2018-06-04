package com.malov.serg.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author serg
 */
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

   private static final long serialVersionUID = -169258812805375171L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


   private Long originalid;


   private String alias;


   private String name;


   private String firstName;


   private String secondName;


   private String lastName;


   @Temporal(TemporalType.DATE)
   private Date createDate;


   private String refined;


   private Long sortNo;


   private Long hidden;


   private String password;


   private String salt;
   
   public Employee(){
      
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getOriginalid() {
      return originalid;
   }

   public void setOriginalid(Long originalid) {
      this.originalid = originalid;
   }

   public String getAlias() {
      return alias;
   }

   public void setAlias(String alias) {
      this.alias = alias;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getSecondName() {
      return secondName;
   }

   public void setSecondName(String secondName) {
      this.secondName = secondName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public String getRefined() {
      return refined;
   }

   public void setRefined(String refined) {
      this.refined = refined;
   }

   public Long getSortNo() {
      return sortNo;
   }

   public void setSortNo(Long sortNo) {
      this.sortNo = sortNo;
   }

   public Long getHidden() {
      return hidden;
   }

   public void setHidden(Long hidden) {
      this.hidden = hidden;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getSalt() {
      return salt;
   }

   public void setSalt(String salt) {
      this.salt = salt;
   }

   @Override
   public String toString() {
      return "Employee{" + "id=" + id + ", originalid=" + originalid + ", alias=" + alias + ", name=" + name + ", firstName=" + firstName + ", secondName=" + secondName + ", lastName=" + lastName + ", createDate=" + createDate + ", refined=" + refined + ", sortNo=" + sortNo + ", hidden=" + hidden + ", password=" + password + ", salt=" + salt + '}';
   }
   
}
