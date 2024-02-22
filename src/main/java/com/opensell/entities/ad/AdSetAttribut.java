package com.opensell.entities.ad;

/**
 * It is to use a lambda function to change a value of an ad from a function.
 * 
 * @author Achraf
 */
public interface AdSetAttribut<T> {
	public static <T> byte checkModifError(AdSetAttribut<T> adSetAttribut, T t, boolean isChangeable) {
		if(isChangeable) {
			// The value as not been changed by the user.
			if(t == null) return 0;
			
			else {
				// The data was successfully changed.
				if(adSetAttribut != null) {
					adSetAttribut.setAttribut(t);
					return 1;
				}
				
				// Function call error, adSetAttribut cannot be null. 
				else return 2;
			}
		} 
		
		// The value cannot be changed because it do not respect the SQL restriction.
		else return 3;
	}
	
	public void setAttribut(T t);
}