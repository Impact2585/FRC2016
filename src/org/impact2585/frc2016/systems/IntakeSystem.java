package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;


/**
 * Ignore the class name, this is actually an IO shooter
 */
public class IntakeSystem implements RobotSystem, Runnable{
	private InputMethod input;
	private SpeedController wheels;
	private SpeedController leftArm;
	private SpeedController rightArm;
	private SpeedController lever;
	private DigitalInput leftLimitSwitch;
	private DigitalInput rightLimitSwitch;
	public static final double ARM_SPEED = 0.3;
	public static final long LEVER_TIME = 2000;
	private boolean disableSpeedMultiplier;
	private boolean prevSpeedToggle;
	private Encoder encoder;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		input = environ.getInput();
		wheels = new Talon(RobotMap.INTAKE_WHEEL);
		leftArm = new Talon(RobotMap.INTAKE_LEFT_ARM);
		rightArm = new Talon(RobotMap.INTAKE_RIGHT_ARM);
		lever = new Victor(RobotMap.LEVER);
		leftLimitSwitch = new DigitalInput(RobotMap.LEFT_INTAKE_LIMIT_SWITCH);
		rightLimitSwitch = new DigitalInput(RobotMap.RIGHT_INTAKE_LIMIT_SWITCH);
		disableSpeedMultiplier = false;
		prevSpeedToggle = false;
		setEncoder(new Encoder(RobotMap.INTAKE_ARM_ENCODER_PORT_A, RobotMap.INTAKE_ARM_ENCODER_PORT_B));
	}
	
	/**Sets the motors controlling the wheels on intake to speed
	 * @param speed the speed to set the motors to
	 */
	public void spinWheels(double speed) {
		wheels.set(speed);
	}
	
	/**Sets the motors controlling the arms for the intake to speed
	 * @param speed the speed to set the motors to
	 */
	public void moveArms(double speed) {
		leftArm.set(speed);
		rightArm.set(-speed);
	}
	
	public void spinLever(double speed){
		lever.set(speed);
	}
	/**
	 * @returns true if the left limit switch is pressed
	 */
	public boolean isLeftSwitchClosed() {
		return leftLimitSwitch.get();
	}
	
	/**
	 * @returns true if the right limit switch is pressed
	 */
	public boolean isRightSwitchClosed() {
		return rightLimitSwitch.get();
	}
	
	/**
	 * @returns true if the right or left limit switch is closed
	 */
	public boolean isSwitchClosed() {
		return isRightSwitchClosed() || isLeftSwitchClosed();
	}
	
	/**Sets the input of the system to newInput
	 * @param newInput the new input to set to
	 */
	protected void setInput(InputMethod newInput) {
		input = newInput;
	}

	/**
	 * @return the encoder
	 */
	public Encoder getEncoder() {
		return encoder;
	}

	/**
	 * @param encoder the encoder to set
	 */
	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(input.intake() && !input.outake()) {
			spinWheels(1);
		} else if(input.outake() && !input.intake()) {
			spinWheels(-1);
		} else {
			spinWheels(0);
		}
		
		double intakeArmSpeed = input.moveIntake();
		if(input.toggleSpeed() && !prevSpeedToggle)
			disableSpeedMultiplier = !disableSpeedMultiplier;
		if(!disableSpeedMultiplier) {
			intakeArmSpeed *= ARM_SPEED;
		}
		
		
		if(input.shoot()){
			spinLever(0.5);
		} else if(input.turnLeverReverse()){
			spinLever(-0.5);
		} else {
			spinLever(0);
		}
	
			
		if(isSwitchClosed() && intakeArmSpeed < 0 && !input.ignoreIntakeLimitSwitch()) {
			intakeArmSpeed = 0;
		}
		
		moveArms(intakeArmSpeed);
		prevSpeedToggle = input.toggleSpeed();
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(wheels instanceof SensorBase) {
			SensorBase motor = (SensorBase) wheels;
			motor.free();
		}
		
		if(lever instanceof SensorBase) {
			SensorBase motor = (SensorBase) lever;
			motor.free();
		}
		
		if(leftArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) leftArm;
			motor.free();
		}
		
		if(rightArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) rightArm;
			motor.free();
		}
		leftLimitSwitch.free();
		rightLimitSwitch.free();
		encoder.free();
	}

}
