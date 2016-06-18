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
		return controller1.getRawButton(XboxConstants.RIGHT_JOYSTICK_BUTTON);
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
	protected double analogMoveIntakeTowardsBot() {
		return controller2.getRawAxis(XboxConstants.RIGHT_TRIGGER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
	 */
	@Override
	protected double analogMoveIntakeAwayFromBot() {
		return controller2.getRawAxis(XboxConstants.LEFT_TRIGGER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#toggleSpeed()
	 */
	@Override
	public boolean toggleArmSpeed() {
		return controller2.getRawButton(XboxConstants.START_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#ignoreIntakeLimitSwitch()
	 */
	@Override
	public boolean toggleIntakeLimitSwitch() {
		return controller2.getRawButton(XboxConstants.X_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#ignoreArmLimitSwitch()
	 */
	@Override
	public boolean ignoreArmLimitSwitch() {
		return controller2.getRawButton(XboxConstants.RIGHT_JOYSTICK_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#turnLever()
	 */
	@Override
	public boolean shoot() {
		return controller2.getRawButton(XboxConstants.B_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#turnLeverReverse()
	 */
	@Override
	public boolean turnLeverReverse() {
		return controller2.getRawButton(XboxConstants.A_BUTTON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2016.input.InputMethod#turnLeverForward()
	 */
	@Override
	public boolean turnLeverForward() {
		return controller2.getRawButton(XboxConstants.Y_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#manualIntakeControl()
	 */
	@Override
	public boolean manualIntakeControl() {
		return controller2.getRawButton(XboxConstants.BACK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#rightIntake()
	 */
	@Override
	public double rightIntake() {
		return controller2.getRawAxis(XboxConstants.RIGHT_TRIGGER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#leftIntake()
	 */
	@Override
	public double leftIntake() {
		return controller2.getRawAxis(XboxConstants.LEFT_TRIGGER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#toggleIntakeArmSpeed()
	 */
	@Override
	public boolean toggleIntakeArmSpeed() {
		return controller2.getRawButton(XboxConstants.LEFT_JOYSTICK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#toggleRotationExponent()
	 */
	@Override
	public boolean toggleRotationExponent() {
		return controller1.getRawButton(XboxConstants.START_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#unwindWinch()
	 */
	@Override
	public boolean unwindWinch() {
		return controller1.getRawButton(XboxConstants.Y_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#windWinch()
	 */
	@Override
	public boolean windWinch() {
		return controller1.getRawButton(XboxConstants.BACK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#liftUp()
	 */
	@Override
	public boolean liftUp() {
		return controller1.getRawButton(XboxConstants.A_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#liftDown()
	 */
	@Override
	public boolean liftDown() {
		return controller1.getRawButton(XboxConstants.X_BUTTON);
	}


}