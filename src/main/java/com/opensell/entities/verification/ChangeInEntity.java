package com.opensell.entities.verification;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * It is to an interface to make it more easy to change the value of an Entity attribute
 * if certain condition are respected.
 *
 * @author Achraf
 */
public interface ChangeInEntity<T> {
	/**
	 * It is to use a lambda function to change a value of an Entity from a function, if condition are respected.
	 * 
	 * @param changeInEntity The interface with the implementation of 
	 * the function <code>setAttribute(T t)</code>, 
	 * the implementation should call a setter of an Entity. Example :
	 * <code>ChangeInEntity< T > changeInEntity = (title) -> entity.setTitle(title)</code>.
	 * 
	 * @param attribute This is a parameter that contain the new value you want to assign to 
	 * the attribute of the entity.
	 * 
	 * @param verifyEntity This an interface that contain a function named <code>isVerified()</code>. The purpose of 
	 * this interface is to be able to implement the interface by using lambda function that will contain step of verification. This function should 
	 * return true if we can call the setter or false if we cannot.
	 * parameter <code>attribute</code> can 
	 * @author Achraf
	 */
	public static <T> VerifyCode checkModifError(ChangeInEntity<T> changeInEntity, T attribute, VerifyEntity verifyEntity) throws Exception {
		if(changeInEntity != null && verifyEntity != null) {
			if(verifyEntity.isVerified()) {
				// The value as not been changed by the user.
				if(attribute == null) return VerifyCode.OK;
				
				// The data was successfully changed.
				else {
					changeInEntity.setAttribute(attribute);
					return VerifyCode.OK;
				}
			} 
			
			// The value cannot be changed because it do not respect the SQL restriction.
			else return VerifyCode.SQL_ERROR;
		} 
		
		// Bad Call of the function
		else throw new Exception("The params changeInEntity and verifyEntity cannot be null.");
	}
	
	/**
	 * This function should contain a call to a setter of an other class.
	 * 
	 * @param t The attribute you want to pass in the other class setter.
	 * @author Achraf
	 */
	public void setAttribute(T t);
}