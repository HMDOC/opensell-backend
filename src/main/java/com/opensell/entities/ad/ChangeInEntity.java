package com.opensell.entities.ad;
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
	 * @param isChangeable This where you put the other condition that the new attribute need to respect. Example :
	 * (entityDto.getTitle().length() <= 255 && entityRepository.findByTitle(entityDto.getTitle()) == null)
	 * 
	 * @author Achraf
	 */
	public static <T> byte checkModifError(ChangeInEntity<T> changeInEntity, T attribute, boolean isChangeable) {
		if(isChangeable) {
			// The value as not been changed by the user.
			if(attribute == null) return 0;
			
			else {
				// The data was successfully changed.
				if(changeInEntity != null) {
					changeInEntity.setAttribute(attribute);
					return 1;
				}
				
				// Function call error, adSetAttribut cannot be null. 
				else return 2;
			}
		} 
		
		// The value cannot be changed because it do not respect the SQL restriction.
		else return 3;
	}
	
	/**
	 * This function should contain a call to a setter of an other class.
	 * 
	 * @param t The attribute you want to pass in the other class setter.
	 * @author Achraf
	 */
	public void setAttribute(T t);
}