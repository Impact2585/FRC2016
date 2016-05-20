package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.lib2585.Toggler;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 * Drivetrain for the robot
 */
public class WheelSystem implements RobotSystem, Runnable{
	
	private RobotDrive drivetrain;
	private double currentRampForward;
	private double rotationValue;
	public static final double DEADZONE = 0.15;
	public static final double RAMP = 0.6;
	public static final double PRIMARY_ROTATION_EXPONENT = 1;
	public static final double SECONDARY_ROTATION_EXPONENT = 0.5;
	private boolean usePrimaryRotationExponent = true;
	private InputMethod input;
	private Toggler toggler;
	private Toggler rotationExponentToggler;
	private boolean inverted;
	private double desiredRampForward;
	private PIDSubsystem forwardPIDSystem;
	private double timeDrivingForward;
	private double distanceDrivenForward;
	private AccelerometerSystem accel;
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		drivetrain = new RobotDrive(new Victor(RobotMap.FRONT_LEFT_DRIVE), new Victor(RobotMap.REAR_LEFT_DRIVE), new Victor(RobotMap.FRONT_RIGHT_DRIVE), new Victor(RobotMap.REAR_RIGHT_DRIVE));
		input = environ.getInput();
		accel = environ.getAccelerometerSystem();
		forwardPIDSystem = new PIDSubsystem(0.45, 0.375, 0) {

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return distanceDrivenForward;
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#usePIDOutput(double)
			 */
			@Override
			protected void usePIDOutput(double output) {
				drive(output, 0);
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
			 */
			@Override
			protected void initDefaultCommand() {
				
			}};
			
			toggler = new Toggler(inverted);
			rotationExponentToggler = new Toggler(usePrimaryRotationExponent);
	}
	
	/**Sets new input method
	 * @param controller the type of input method
	 */
	protected synchronized void setInput(InputMethod controller) {
		input = controller;
	}
	
	/**
	 * @return the accel
	 */
	public AccelerometerSystem getAccel() {
		return accel;
	}

	/**
	 * @param accel the accel to set
	 */
	public void setAccel(AccelerometerSystem accel) {
		this.accel = accel;
	}

	/**
	 * @returns the input method that this system is using
	 */
	public InputMethod getInput() {
		return input;
	}
	

	/**
	 * @return the toggler
	 */
	protected synchronized Toggler getInverterToggler() {
		return toggler;
	}

	/**
	 * @param toggler the toggler to set
	 */
	protected synchronized void setInverterToggler(Toggler toggler) {
		this.toggler = toggler;
	}
	
	/**
	 * @return the toggler
	 */
	protected synchronized Toggler getRotationExponentTogger() {
		return rotationExponentToggler;
	}

	/**
	 * @param toggler the toggler to set
	 */
	protected synchronized void setRotationExponentToggler(Toggler toggler) {
		this.rotationExponentToggler = toggler;
	}

	/**drives forward for distance
	 * @param distance the distance in meters to drive
	 * @return true if the drivetrain has driven the distance false if the drivetrain has not reached the distance
	 */
	public boolean driveDistanceForward(double distance) {
		if(timeDrivingForward == 0) {
			initializeForwardPID(distance);
			timeDrivingForward = System.currentTimeMillis();
			return false;
		}
		if(distanceDrivenForward < distance) {
			distanceDrivenForward += accel.getXSpeed() * (System.currentTimeMillis() - timeDrivingForward) * 1000;
			timeDrivingForward = System.currentTimeMillis();
			return false;
		} else {
			timeDrivingForward = 0;
			distanceDrivenForward = 0;
			forwardPIDSystem.getPIDController().reset();
			forwardPIDSystem.disable();
			return true;
		}
		
	}

	/**
	 * @return the distanceDrivenForward
	 */
	public double getDistanceDrivenForward() {
		return distanceDrivenForward;
	}

	/**Sets the distance that the drivetrain has driven
	 * @param distanceDrivenForward the distanceDrivenForward to set
	 */
	public void setDistanceDrivenForward(double distanceDrivenForward) {
		this.distanceDrivenForward = distanceDrivenForward;
	}

	/**Starts the forward PID subsystem
	 * @param setpoint
	 */
	public void initializeForwardPID(double setpoint) {
		forwardPIDSystem.enable();
		forwardPIDSystem.setSetpoint(setpoint);
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
		inverted = toggler.toggle(input.invert());
		
		//toggles the rotation exponent if the input tells it to and the previous invert command was false
		usePrimaryRotationExponent = rotationExponentToggler.toggle(input.toggleRotationExponent());
		
		//sets desiredRampForward, and rotationValue to the input values
		desiredRampForward = input.forwardMovement();
		rotationValue = input.rotationValue();
		
		//sets desiredRampForward and rotationValue to zero if they are below deadzone
		if(desiredRampForward < DEADZONE && desiredRampForward > -DEADZONE)
			desiredRampForward = 0;
		if(rotationValue < DEADZONE && rotationValue > -DEADZONE)
			rotationValue = 0;
		
		// adjusts sensitivity of the turns 
		rotationValue = Math.signum(rotationValue) * Math.abs(Math.pow(rotationValue, usePrimaryRotationExponent ? PRIMARY_ROTATION_EXPONENT : SECONDARY_ROTATION_EXPONENT));
		
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
		forwardPIDSystem.disable();
		forwardPIDSystem.getPIDController().free();
	}

}
