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
	public static final double RAMP = 0.6;
	public static final double ROTATION_EXPONENT = 1;
	private InputMethod input;
	private boolean inverted;
	private boolean prevInvert;
	private double desiredRampForward;


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
		
		//inverts if the input tells it to and the previous invert command was false
		if(input.invert() && !prevInvert) {
			inverted ^= true;
		}
		
		//sets prevInvert, desiredRampForward, and rotationValue to the input values
		prevInvert = input.invert();
		desiredRampForward = input.forwardMovement();
		rotationValue = input.rotationValue();
		
		//sets desiredRampForward and rotationValue to zero if they are below deadzone
		if(desiredRampForward < DEADZONE && desiredRampForward > -DEADZONE)
			desiredRampForward = 0;
		if(rotationValue < DEADZONE && rotationValue > -DEADZONE)
			rotationValue = 0;
		
		// adjusts sensitivity of the turns 
		if(rotationValue > 0)
			rotationValue = Math.pow(rotationValue, ROTATION_EXPONENT);
		if(rotationValue < 0){
			rotationValue = -1*Math.abs(Math.pow(rotationValue, ROTATION_EXPONENT));
		}
		
		
		if(desiredRampForward != 0) {
			
		
			//inverts the desiredRampForward if the wheel system should be inverted
			if(inverted) {
				desiredRampForward *= -1;
			}
		
			//ramps up or down depending on the difference between the desired ramp and the current ramp multiplied by the RAMP constant
			if(currentRampForward < desiredRampForward) {
				double inc = desiredRampForward - currentRampForward;
			
				if(inc <= 0.01)
					currentRampForward = desiredRampForward;
				else
					currentRampForward += (inc*RAMP);
			} else if (currentRampForward > desiredRampForward) {
				double decr = currentRampForward - desiredRampForward;
			
				if(decr > 0.01)
					currentRampForward -= (decr*RAMP);
				else
					currentRampForward = desiredRampForward;
			}
		}else
			//sets currentRampForward immediately to 0 if the input is 0
			currentRampForward = 0;
		
		drive(currentRampForward, rotationValue);

	}
	
	/**
	 * @returns the current ramp forward
	 */
	public double getCurrentRampForward() {
		return currentRampForward;
	}
	
	/**sets the current ramp forward to rampForward
	 * @param rampForward the distance the robot is moving forward
	 */
	public void setCurrentRampForward(double rampForward) {
		currentRampForward = rampForward;
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivetrain.free();
	}

}
