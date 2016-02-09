package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;


/**
 * Drivetrain for the robot
 */
public class WheelSystem implements RobotSystem, Runnable{
	
	private RobotDrive drivetrain;
	private double currentRampForward;
	private double rotationValue;
	public static final double DEADZONE = 0.15;
	private InputMethod input;
	private boolean inverted;
	private boolean prevInvert;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		drivetrain = new RobotDrive(new Victor(RobotMap.FRONT_LEFT_DRIVE), new Victor(RobotMap.REAR_LEFT_DRIVE), new Victor(RobotMap.FRONT_RIGHT_DRIVE), new Victor(RobotMap.REAR_RIGHT_DRIVE));
		input = environ.getInput();
		inverted = false;
		prevInvert = false;
	}
	
	/**Sets new input method
	 * @param controller the type of input method
	 */
	protected synchronized void setInput(InputMethod controller) {
		input = controller;
	}
	
	/**
	 * @returns the input method that this system is using
	 */
	public InputMethod getInput() {
		return input;
	}
	
	
	/** Drives forward and turns
	 * @param driveForward distance to drive forward
	 * @param rotate how much the robot will turn
	 */
	public void drive(double driveForward, double rotate) {
		drivetrain.arcadeDrive(driveForward, -rotate);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(input.invert() && !prevInvert) {
			inverted ^= true;
		}
		
		prevInvert = input.invert();
		
		currentRampForward = input.forwardMovement();
		rotationValue = input.rotationValue();
		
		if(currentRampForward < DEADZONE && currentRampForward > -DEADZONE)
			currentRampForward = 0;
		if(rotationValue < DEADZONE && rotationValue > -DEADZONE)
			rotationValue = 0;
		
		if(inverted) {
			drive(-currentRampForward, rotationValue);
		}
		else {
			drive(currentRampForward, rotationValue);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivetrain.free();
	}

}
