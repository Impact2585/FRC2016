package org.impact2585.frc2016.input;

/**
 * Classes implement this if they are a type of input
 */
public interface InputMethod {
	
	/**
	 * @returns how far the robot should move
	 */
	public default double forwardMovement() {
		return 0;
	}
	
	/**
	 * @returns how much the robot should robot
	 */
	public default double rotationValue() {
		return 0;
	}
	
	/**
	 * @returns true if the robot drivetrain should invert
	 */
	public default boolean invert() {
		return false;
	}
	
	/**
	 * @returns true if the top arm should rotate forward
	 */
	public default boolean topArmForward() {
		return false;
	}
	
	/**
	 * @returns true if the top arm should rotate backwards
	 */
	public default boolean topArmBackward() {
		return false;
	}
	
	/**
	 * @returns how far the bottom arm should move forward
	 */
	public default double backArmForwardValue() {
		return 0;
	}
	
	/**
	 * @returns how far the back arm should move backwards
	 */

	public default double backArmBackwardValue() {
		return 0;
	}
	
	/**
	 * @returns true if the two wheels should spin in the direction that one should shoot
	 */
	public default boolean outtake() {
		return false;
	}
	
	/**
	 * @returns true if the two wheels should spin in the direction that one should intake
	 */
	public default boolean intake() {
		return false;
	}
	
	/**
	 * @returns true if the intake arms should move towards the bot
	 */
	public default boolean moveTowardsBot() {
		return false;
	}
	
	/**
	 * @returns true if the intake arms should mvoe away from the bot
	 */
	public default boolean moveAwayFromBot() {
		return false;
	}

}
