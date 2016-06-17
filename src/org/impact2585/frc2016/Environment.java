package org.impact2585.frc2016;

import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.input.PartnerXboxInput;
import org.impact2585.frc2016.systems.AccelerometerSystem;
import org.impact2585.frc2016.systems.ElectricalSystem;
import org.impact2585.frc2016.systems.IntakeSystem;
import org.impact2585.frc2016.systems.LiftSystem;
import org.impact2585.frc2016.systems.WheelSystem;
import org.impact2585.lib2585.RobotEnvironment;

/**
 * sets robot's systems 
 */
public class Environment extends RobotEnvironment{

	private static final long serialVersionUID = -8268997098529757749L;
	private InputMethod input;
	private WheelSystem wheels;
	private IntakeSystem intake;
	private ElectricalSystem panel;
	private AccelerometerSystem accelerometer;
	private LiftSystem lift;
	
	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/** Constructor that takes a robot argument
	 * @param robot controls whether the robot is in auton or teleop mode and sets the environment
	 */
	public Environment(Robot robot) {
		super(robot);
		wheels = new WheelSystem();
		input = new PartnerXboxInput();
		wheels.init(this);
		intake = new IntakeSystem();
		intake.init(this);
		panel = new ElectricalSystem();
		panel.init(this);
		accelerometer = new AccelerometerSystem();
		accelerometer.init(this);
		lift = new LiftSystem();
		lift.init(this);
	}
	
	/**
	 * @returns inputMethod
	 */
	public InputMethod getInput(){
		return this.input;
	}
	
	/**
	 * @returns the wheel system
	 */
	public WheelSystem getWheelSystem() {
		return wheels;
	}
	
	/**Sets wheel system
	 * @param wheelsystem the drivetrain
	 */
	public void setWheelSystem(WheelSystem wheelsystem) {
		wheels = wheelsystem;
	}
		
	/**
	 * @returns the intake system
	 */
	public IntakeSystem getIntakeSystem() {
		return intake;
	}
	
	/**Sets the intake system to the intake system passed in the parameter
	 * @param intakesystem the new intakesystem to set
	 */
	public void setIntakeSystem(IntakeSystem intakesystem) {
		intake = intakesystem;
	}
	
	/**
	 * @returns the eletrical system of the robot
	 */
	public ElectricalSystem getElectricalSystem() {
		return panel;
	}
	
	/**Sets the eletrical system
	 * @param electricystem the new eletrical system to set
	 */
	public void setElectricalSystem(ElectricalSystem electricystem) {
		panel = electricystem;
	}
	
	/**
	 * @returns the accelerometer system
	 */
	public AccelerometerSystem getAccelerometerSystem() {
		return accelerometer;
	}
	
	/**sets the accelerometer system to accel
	 * @param accel new accelerometer system to set
	 */
	public void setAccelerometerSystem(AccelerometerSystem accel) {
		accelerometer = accel;
	}

	/**
	 * @return the lift system
	 */
	public LiftSystem getLiftSystem() {
		return lift;
	}

	/**
	 * @param lift the lift system to set
	 */
	public void setLiftSystem(LiftSystem lift) {
		this.lift = lift;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheels.destroy();
		intake.destroy();
		panel.destroy();
		accelerometer.destroy();
		lift.destroy();
	}
	
}
