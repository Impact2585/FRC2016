package org.impact2585.frc2016.input;

import org.impact2585.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Use this if you are using two xbox controllers as input
 */
public class PartnerXboxInput extends InputMethod {
	private Joystick controller1;
	private Joystick controller2;

	/**
	 * constructer that initializes the two joysticks
	 */
	public PartnerXboxInput() {
		controller1 = new Joystick(0);
		controller2 = new Joystick(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return -controller1.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
	 */
	@Override
	public double rotationValue() {
		return controller1.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#invert()
	 */
	@Override
	public boolean invert() {
		return controller1.getRawButton(XboxConstants.Y_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#outake()
	 */
	@Override
	public boolean outake() {
		return controller2.getRawButton(XboxConstants.LEFT_BUMPER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#intake()
	 */
	@Override
	public boolean intake() {
		return controller2.getRawButton(XboxConstants.RIGHT_BUMPER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.impact2585.frc2016.input.InputMethod#analogMoveIntakeTowardsBot()
	 */
	@Override
	public double analogMoveIntakeTowardsBot() {
		return controller2.getRawAxis(XboxConstants.RIGHT_TRIGGER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
	 */
	@Override
	public double analogMoveIntakeAwayFromBot() {
		return controller2.getRawAxis(XboxConstants.LEFT_TRIGGER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#analogTopArm()
	 */
	@Override
	public double analogTopArm() {
		return -controller2.getRawAxis(XboxConstants.RIGHT_Y_AXIS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#reversibleBottomArmValue()
	 */
	@Override
	public double reversibleBottomArmValue() {
		return -controller2.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#toggleSpeed()
	 */
	@Override
	public boolean toggleSpeed() {
		return controller2.getRawButton(XboxConstants.START_BUTTON);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#ignoreIntakeLimitSwitch()
	 */
	@Override
	public boolean ignoreIntakeLimitSwitch(){
		return controller2.getRawButton(XboxConstants.X_BUTTON);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#ignoreArmLimitSwitch()
	 */
	@Override
	public boolean ignoreArmLimitSwitch(){
		return controller2.getRawButton(XboxConstants.A_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#turnLever()
	 */
	@Override
	public boolean shoot() {
		return controller2.getRawButton(XboxConstants.B_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#turnLeverReverse()
	 */
	@Override
	public boolean turnLeverReverse() {
		return controller2.getRawButton(XboxConstants.Y_BUTTON);
	}
	
	
}