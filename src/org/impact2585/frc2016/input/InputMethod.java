package org.impact2585.frc2016.input;

/**
 * Classes implement this if they are a type of input
 */
public interface InputMethod {
	
	/**
	 * @returns how far the robot should move
	 */
	public abstract double forwardMovement();
	
	/**
	 * @returns how much the robot should robot
	 */
	public abstract double rotationValue();
	
	/**
	 * @returns true if the robot drivetrain should invert
	 */
	public abstract boolean invert();

}
