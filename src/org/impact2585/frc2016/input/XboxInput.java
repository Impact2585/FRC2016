package org.impact2585.frc2016.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Uses Xbox controller for input
 */
public class XboxInput implements InputMethod{

	private Joystick controller;
	
	/**
	 * Constructor that initializes new joystick
	 */
	public XboxInput() {
		controller = new Joystick(1);
	}
	
	/**Constructor that sets joystick controller to joystick
	 * @param joystick
	 */
	public XboxInput(Joystick joystick) {
		controller = joystick;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return controller.getRawAxis(0);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
	 */
	@Override
	public double rotationValue() {
		return controller.getRawAxis(1);
	}
	
}
