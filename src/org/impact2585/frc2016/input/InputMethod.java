package org.impact2585.frc2016.input;

/**
 * Classes extend this if they are a type of input
 */
public abstract class InputMethod {

	/**
	 * @returns how far the robot should move
	 */
	public double forwardMovement() {
		return 0;
	}

	/**
	 * @returns how much the robot should rotate
	 */
	public double rotationValue() {
		return 0;
	}

	/**
	 * @returns true if the robot's drivetrain should invert
	 */
	public boolean invert() {
		return false;
	}


	/**
	 * @returns the value to move the wrist part of the arm system
	 */
	public double moveTopArm() {
		return AnalogDigitalInputProcessor.processAnalogJoyStick(digitalTopArmForward(), digitalTopArmBackward(), analogTopArm());
	}

	/**
	 * @returns the analog input for moving the top arm
	 */
	protected double analogTopArm() {
		return 0;
	}

	/**
	 * @returns true if the top arm should rotate forward
	 */
	protected boolean digitalTopArmForward() {
		return false;
	}

	/**
	 * @returns true if the top arm should rotate backwards
	 */
	protected boolean digitalTopArmBackward() {
		return false;
	}

	/**
	 * @returns the value to move the shoulder part of the arm system
	 */
	public double moveBottomArm() {
		return AnalogDigitalInputProcessor.processDoubleAnalog(bottomArmTowardBot(), bottomArmAwayFromBot(), reversibleBottomArmValue());
	}

	/**
	 * @returns how far the bottom arm should move forward
	 */
	protected double bottomArmAwayFromBot() {
		return 0;
	}

	/**
	 * @returns how far the back arm should move backwards
	 */
	protected double bottomArmTowardBot() {
		return 0;
	}

	/**
	 * @returns the analog input for the back arm if we are using two controllers
	 */
	protected double reversibleBottomArmValue() {
		return 0;
	}

	/**
	 * @returns true if the two wheels should spin in the direction that one should shoot
	 */
	public boolean outake() {
		return false;
	}

	/**
	 * @returns true if the two wheels should spin in the direction that one should intake
	 */
	public boolean intake() {
		return false;
	}
	
	/**
	 * @returns true if the speed multiplier of the arms should be toggled on or off
	 */
	public boolean toggleArmSpeed() {
		return false;
	}
	
	/**
	 * @return true if the speed multiplier for the intake arms should be toggled  on or off
	 */
	public boolean toggleIntakeArmSpeed() {
		return false;
	}

	/**
	 * @returns 1 or -1 if the input is digital or returns the analog input if the input is analog
	 */
	public double moveIntake() {
		return AnalogDigitalInputProcessor.processAnalogTriggers(digitalMoveIntakeAwayFromBot(), digitalMoveIntakeTowardsBot(), analogMoveIntakeAwayFromBot(), analogMoveIntakeTowardsBot());
	}

	/**
	 * @returns the distance to move the intake towards the bot if user is using analog input
	 */
	protected double analogMoveIntakeTowardsBot() {
		return 0;
	}

	/**
	 * @returns the distance to move intake's away from the bot if user is using analog input
	 */
	protected double analogMoveIntakeAwayFromBot() {
		return 0;
	}

	/**
	 * @returns true if the intake's arms should move towards the bot
	 */
	protected boolean digitalMoveIntakeTowardsBot() {
		return false;
	}

	/**
	 * @returns true if the intake's arms should move away from the bot
	 */
	protected boolean digitalMoveIntakeAwayFromBot() {
		return false;
	}
	
	/**
	 * @returns true if the state of ignoring the intake arm limit switch should be toggled, false if otherwise
	 */
	public boolean toggleIntakeLimitSwitch(){
		return false;
	}
	
	/**
	 * @returns true if the Arm should ignore the limit switch, false otherwise
	 */
	public boolean ignoreArmLimitSwitch(){
		return false;
	}
	
	/**
	 * @returns true if the lever undergo the shooting procedure(move forward then move back)
	 */
	public boolean shoot(){
		return false;
	}
	
 	/**
	 * @returns true if lever should spin in reverse, false otherwise
	 */
	public boolean turnLeverReverse(){
		return false;
	}
	
	/**
	 * @returns true if the lever should spin forward, false otherwise
	 */
	public boolean turnLeverForward() {
		return false;
	}
	
	/**
	 * @return true if the intake is being moved manually
	 */
	public boolean manualIntakeControl(){
		return false;
	}
	
	/**
	 * @return how much the right intake arm should move
	 */
	public double rightIntake(){
		return 0;
	}
	
	/**
	 * @return how much the left intake arm should move
	 */
	public double leftIntake(){
		return 0;
	}
	
	/**
	 * @return true if the rotation exponent for the drivetrain should be toggled
	 */
	public boolean toggleRotationExponent() {
		return false;
	}
	
	/**
	 * @return true if the winch should unwind
	 */
	public boolean unwindWinch() {
		return false;
	}
	
	/**
	 * @return true if the winch should wind up
	 */
	public boolean windWinch() {
		return false;
	}
}
