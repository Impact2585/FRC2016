package org.impact2585.frc2016.input;

import edu.wpi.first.wpilibj.Joystick;

import org.impact2585.lib2585.XboxConstants;

/**
 * Uses Xbox controller for input
 */
public class XboxInput extends InputMethod{

	private Joystick controller;
	
	/**
	 * Constructor that initializes new joystick
	 */
	public XboxInput() {
		controller = new Joystick(0);
	}
	
	/**Constructor that sets joystick controller to joystick
	 * @param joystick the joystick the input is using
	 */
	public XboxInput(Joystick joystick) {
		controller = joystick;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return -controller.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
	 */
	@Override
	public double rotationValue() {
		return controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#setInverted()
	 */
	@Override
	public boolean invert() {
		return controller.getRawButton(XboxConstants.LEFT_JOYSTICK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#outake()
	 */
	@Override
	public boolean outake() {
		return controller.getRawButton(XboxConstants.X_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#intake()
	 */
	@Override
	public boolean intake() {
		return controller.getRawButton(XboxConstants.A_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeTowardsBot()
	 */
	@Override
	protected double analogMoveIntakeTowardsBot() {
		return controller.getRawAxis(XboxConstants.RIGHT_TRIGGER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
	 */
	@Override
	protected double analogMoveIntakeAwayFromBot() {
		return controller.getRawAxis(XboxConstants.LEFT_TRIGGER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#toggleSpeed()
	 */
	@Override
	public boolean toggleArmSpeed() {
		return controller.getRawButton(XboxConstants.RIGHT_JOYSTICK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#toggleRotationExponent()
	 */
	@Override
	public boolean toggleRotationExponent() {
		return controller.getRawButton(XboxConstants.BACK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#unwindWinch()
	 */
	@Override
	public boolean unwindWinch() {
		return controller.getRawButton(XboxConstants.RIGHT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#windWinch()
	 */
	@Override
	public boolean windWinch() {
		return controller.getRawButton(XboxConstants.LEFT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#liftUp()
	 */
	@Override
	public boolean liftUp() {
		return controller.getRawButton(XboxConstants.Y_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#liftDown()
	 */
	@Override
	public boolean liftDown() {
		return controller.getRawButton(XboxConstants.B_BUTTON);
	}
}
