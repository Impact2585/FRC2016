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
		if(digitalTopArmForward() && !digitalTopArmBackward() && analogTopArm() == 0) {
			return 1;
		} else if (!digitalTopArmForward() && digitalTopArmBackward() && analogTopArm() == 0) {
			return -1; 
		} else if (analogTopArm() != 0 && !digitalTopArmForward() && !digitalTopArmBackward()) {
			return analogTopArm();
		} else {
			return 0;
		}
	}

	/**
	 * @returns the analog input for moving the top arm
	 */
	public double analogTopArm() {
		return 0;
	}

	/**
	 * @returns true if the top arm should rotate forward
	 */
	public boolean digitalTopArmForward() {
		return false;
	}

	/**
	 * @returns true if the top arm should rotate backwards
	 */
	public boolean digitalTopArmBackward() {
		return false;
	}

	/**
	 * @returns the value to move the shoulder part of the arm system
	 */
	public double moveBottomArm() {
		if(bottomArmBackwardValue() > 0 && bottomArmForwardValue() == 0 && reversibleBottomArmValue() == 0) {
			return -bottomArmBackwardValue();
		} else if(bottomArmBackwardValue() == 0 && bottomArmForwardValue() > 0 && reversibleBottomArmValue() == 0) {
			return bottomArmForwardValue();
		} else if(bottomArmBackwardValue() == 0 && bottomArmForwardValue() == 0 && reversibleBottomArmValue() != 0) {
			return reversibleBottomArmValue();
		} else {
			return 0;
		}
	}

	/**
	 * @returns how far the bottom arm should move forward
	 */
	public double bottomArmForwardValue() {
		return 0;
	}

	/**
	 * @returns how far the back arm should move backwards
	 */
	public double bottomArmBackwardValue() {
		return 0;
	}

	/**
	 * @returns the analog input for the back arm if we are using two controllers
	 */
	public double reversibleBottomArmValue() {
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
	public boolean toggleSpeed() {
		return false;
	}

	/**
	 * @returns 1 or -1 if the input is digital or returns the analog input if the input is analog
	 */
	public double moveIntake() {
		if(digitalMoveIntakeAwayFromBot() && !digitalMoveIntakeTowardsBot()) {
			return 1;
		} else if (digitalMoveIntakeTowardsBot() && !digitalMoveIntakeAwayFromBot()) {
			return -1; 
		} else if((analogMoveIntakeAwayFromBot() > 0 && analogMoveIntakeTowardsBot() == 0)) {
			return analogMoveIntakeAwayFromBot();
		} else if (analogMoveIntakeTowardsBot() > 0 && analogMoveIntakeAwayFromBot() == 0) {
			return -analogMoveIntakeTowardsBot();
		} else {
			return 0;
		}
	}

	/**
	 * @returns the distance to move the intake towards the bot if user is using analog input
	 */
	public double analogMoveIntakeTowardsBot() {
		return 0;
	}

	/**
	 * @returns the distance to move intake's away from the bot if user is using analog input
	 */
	public double analogMoveIntakeAwayFromBot() {
		return 0;
	}

	/**
	 * @returns true if the intake's arms should move towards the bot
	 */
	public boolean digitalMoveIntakeTowardsBot() {
		return false;
	}

	/**
	 * @returns true if the intake's arms should move away from the bot
	 */
	public boolean digitalMoveIntakeAwayFromBot() {
		return false;
	}
	
	/**
	 * @returns true if the intake should ignore the limit switch, false otherwise
	 */
	public boolean ignoreIntakeLimitSwitch(){
		return false;
	}
	
	/**
	 * @returns true if the Arm should ignore the limit switch, false otherwise
	 */
	public boolean ignoreArmLimitSwitch(){
		return false;
	}
	
	/**
	 * @returns true if the lever should spin forward, false otherwise
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
}
