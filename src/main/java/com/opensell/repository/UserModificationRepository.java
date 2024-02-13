package com.opensell.repository;

import com.opensell.entities.User;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Olivier
 */
@Repository
@Transactional
public interface UserModificationRepository extends CrudRepository<User, Integer> {

    @Modifying
    @Query(value = "update user u set u.email = ?1 where u.id_user = ?2", nativeQuery = true)
    public abstract int updateUserEmail(String newEmail, Integer id_user);

    @Modifying
    @Query(value = "update user u set u.user_name = ?1 where u.id_user = ?2", nativeQuery = true)
    public abstract int updateUserUserName(String newUserName, Integer id_user);

    @Modifying
    @Query(value = "update user u set u.pwd = ?1 where u.id_user = ?2", nativeQuery = true)
    public abstract int updateUserPwd(String newPwd, Integer id_user);

    @Modifying
    @Query(value = "update user_info u set u.first_name = ?1 where u.id_user_info = ?2", nativeQuery = true)
    public abstract int updateUserFirstName(String newFirstName, Integer id_user);

    @Modifying
    @Query(value = "update user_info u set u.last_name = ?1 where u.id_user_info = ?2", nativeQuery = true)
    public abstract int updateUserLastName(String newLastName, Integer id_user);

    @Modifying
    @Query(value = "update user_info u set u.phone_number = ?1 where u.id_user_info = ?2", nativeQuery = true)
    public abstract int updateUserPhoneNumber(String newPhoneNumber, Integer id_user);

    @Modifying
    @Query(value = "update user_info u set u.primary_address = ?1 where u.id_user_info = ?2", nativeQuery = true)
    public abstract int updateUserPrimaryAddress(String newAddress, Integer id_user);

    @Modifying
    @Query(value = "update user_info u set u.icon_path = ?1 where u.id_user_info = ?2", nativeQuery = true)
    public abstract int updateUserIconPath(String newIconPath, Integer id_user);


}
