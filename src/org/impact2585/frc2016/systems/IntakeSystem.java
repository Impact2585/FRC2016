package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * Ignore the class name, this is actually an IO shooter
 */
public class IntakeSystem implements RobotSystem, Runnable{
	private InputMethod input;
	private SpeedController leftWheel;
	private SpeedController rightWheel;
	private SpeedController leftArm;
	private SpeedController rightArm;
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		input = environ.getInput();
		leftWheel = new Victor(RobotMap.INTAKE_LEFT_WHEEL);
		rightWheel = new Victor(RobotMap.INTAKE_RIGHT_WHEEL);
		leftArm = new Victor(RobotMap.INTAKE_LEFT_ARM);
		rightArm = new Victor(RobotMap.INTAKE_RIGHT_ARM);
		
	}
	
	/**Sets the motors controlling the wheels on intake to speed
	 * @param speed the speed to set the motors to
	 */
	public void spinWheels(double speed) {
		leftWheel.set(speed);
		rightWheel.set(-speed);
	}
	
	/**Sets the motors controlling the arms for the intake to speed
	 * @param speed the speed to set the motors to
	 */
	public void moveArms(double speed) {
		leftArm.set(speed);
		rightArm.set(-speed);
	}
	
	/**Sets the input of the system to newInput
	 * @param newInput the new input to set to
	 */
	protected void setInput(InputMethod newInput) {
		input = newInput;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(input.intake() && !input.outtake()) {
			spinWheels(1);
		} else if(input.outtake() && !input.intake()) {
			spinWheels(-1);
		} else {
			spinWheels(0);
		}
		
		if(input.moveTowardsBot() && !input.moveAwayFromBot()) {
			moveArms(0.3);
		} else if(input.moveAwayFromBot() && !input.moveTowardsBot()) {
			moveArms(-0.3);
		} else {
			moveArms(0);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(leftWheel instanceof SensorBase) {
			SensorBase motor = (SensorBase) leftWheel;
			motor.free();
		}
		
		if(rightWheel instanceof SensorBase) {
			SensorBase motor = (SensorBase) rightWheel;
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
	}

	

}
