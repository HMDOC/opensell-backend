package com.opensell.entities.verification;

/**
 * Interface that contain a function to check condition related to an Entity, the purpose of this 
 * interface is to make a lambda function implementation possible when trying to instantiate :
 * <code>VerifyEntity verifyEntity = () -> false;</code>
 *
 * @author Achraf
 */
public interface VerifyEntity {
	public boolean isVerified() throws Exception;
}