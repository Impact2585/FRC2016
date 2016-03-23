package org.impact2585.frc2016;

/**
 * this is where the constants for the robot are
 */
public interface RobotMap {

	public static final int FRONT_LEFT_DRIVE = 1;
	public static final int REAR_LEFT_DRIVE = 2;
	public static final int FRONT_RIGHT_DRIVE = 3;
	public static final int REAR_RIGHT_DRIVE = 4;
	
	public static final int TOP_ARM = 5;
	public static final int BOTTOM_ARM = 7;
	
	public static final int INTAKE_WHEEL = 6;
	public static final int INTAKE_LEFT_ARM = 9;
	public static final int INTAKE_RIGHT_ARM = 0;
	
	public static final int LEVER = 8;

	public static final int ARM_LIMIT_SWITCH = 1;
	public static final int LEFT_INTAKE_LIMIT_SWITCH = 2;
	public static final int RIGHT_INTAKE_LIMIT_SWITCH = 3;
	
	public static final int BOTTOM_ARM_ENCODER_PORT_A = 4;
	public static final int BOTTOM_ARM_ENCODER_PORT_B = 5;
	public static final int TOP_ARM_ENCODER_PORT_A = 6;
	public static final int TOP_ARM_ENCODER_PORT_B = 7;
	public static final int INTAKE_ARM_ENCODER_PORT_A = 8;
	public static final int INTAKE_ARM_ENCODER_PORT_B  = 9;
	public static final AutonomousExecuter CURRENT_AUTON = AutonomousExecuter.BASIC;
	

}
