package com.opensell.entities;

import com.opensell.entities.user.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private int idUser;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date joinedDate;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pwd;

    /**
     * @Source <a href="https://stackoverflow.com/questions/28207359/how-to-set-default-boolean-value-in-jpa">Source</a>
     * "@ColumnDefault("0")"
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isVerified;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActivated;

    @Column(nullable = false, unique = true)
    private String profilLink;
    
    @OneToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

}
