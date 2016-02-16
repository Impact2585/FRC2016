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
	
	/**
	 * @returns true if the top arm should rotate forward
	 */
	public abstract boolean topArmForward();
	
	/**
	 * @returns true if the top arm should rotate backwards
	 */
	public abstract boolean topArmBackward();
	
	/**
	 * @returns how far the bottom arm should move forward
	 */
	public abstract double backArmForwardValue();
	
	/**
	 * @returns how far the back arm should move backwards
	 */
	public abstract double backArmBackwardValue();

}
