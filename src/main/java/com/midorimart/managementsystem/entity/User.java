package com.midorimart.managementsystem.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "[User]")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(unique = true)
    private String email;
    @Column(name = "full_name")
    private String fullname;
    private String password;
    @Column(name = "phone_number")
    private String phonenumber;
    private String address;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "deleted")
    private int deleted;



    public List<String> getAddress() {
        return this.address!= null?Arrays.asList(this.address.split(";")):null;
    }

    public void setAddress(List<String> addresses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String address : addresses) {
            stringBuilder.append(address).append(";");
        }
        this.address = stringBuilder.substring(0, stringBuilder.length()-1).toString();
    }

}
