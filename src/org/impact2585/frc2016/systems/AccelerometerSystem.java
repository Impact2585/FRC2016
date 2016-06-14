package org.impact2585.frc2016.systems;


import org.impact2585.frc2016.Environment;
import org.impact2585.lib2585.MPU6050;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system gets the accelerometer and gyro values from the MPU6050
 */
public class AccelerometerSystem implements RobotSystem, Runnable{

	private MPU6050 accelerometer;
	private long prevTime;
	private double deltaTime;

	private double prevXVelocity;
	private double prevYVelocity;
	private double prevZVelocity;

	private double xVelocity;
	private double yVelocity;
	private double zVelocity;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		accelerometer = new MPU6050();
		SmartDashboard.putBoolean("Connection Successful: ", false);
		
		prevTime = System.currentTimeMillis();

	}

	/**
	 * @return the accelerometer
	 */
	public MPU6050 getAccelerometer() {
		return accelerometer;
	}
	
	/**
	 * @return whether the accelerometer has updated successfully
	 */
	public boolean update() {
		return accelerometer.update();
	}
	/**
	 * @param accelerometer the accelerometer to set
	 */
	public void setAccelerometer(MPU6050 accelerometer) {
		this.accelerometer = accelerometer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//wait for the FIFO count to reach the dmp packet size
		if(update()) {
			accessSmartDashboard();
			deltaTime = (System.currentTimeMillis() - prevTime)/1000.0;
			prevTime = System.currentTimeMillis();
			xVelocity = prevXVelocity + getXAccel() * deltaTime;
			yVelocity = prevYVelocity + getYAccel() * deltaTime;
			zVelocity = prevZVelocity + getZAccel() * deltaTime;
			prevXVelocity = xVelocity;
			prevYVelocity = yVelocity;
			prevZVelocity = zVelocity;
		}
	}

	/**
	 * Resets the velocities
	 */
	public void reset() {
		prevXVelocity = 0;
		prevYVelocity = 0;
		prevZVelocity = 0;
		zVelocity = 0;
		yVelocity = 0;
		xVelocity = 0;
	}

	/**Sets the previous time
	 * @param time the new time to set prevtime to
	 */
	public void setPrevTime(long time) {
		prevTime = time;
	}

	/**
	 * @return the change in time in seconds
	 */
	public double getChangedTime() {
		return deltaTime;
	}


	/**
	 * Puts the processed accelerometer and gyro values to the SmartDashboard
	 */
	public void accessSmartDashboard() {
		SmartDashboard.putNumber("X axis Acceleration: ", getXAccel());
		SmartDashboard.putNumber("Y axis Acceleration: ", getYAccel());
		SmartDashboard.putNumber("Z axis Acceleration: ", getZAccel());

		SmartDashboard.putNumber("Yaw: ", accelerometer.getYaw());
		SmartDashboard.putNumber("Pitch: ", accelerometer.getPitch());
		SmartDashboard.putNumber("Roll: ", accelerometer.getRoll());
	}

	/**
	 * @return the speed in the x axis
	 */
	public double getXSpeed() {
		return xVelocity;
	}

	/**
	 * @return the speed in the y axis
	 */
	public double getYSpeed() {
		return yVelocity;
	}

	/**
	 * @return the speed in the z-axis
	 */
	public double getZSpeed() {
		return zVelocity;
	}

	/**
	 * @returns the acceleration in the x-axis in m/s^2
	 */
	public double getXAccel() {
		return accelerometer.getXAccel();
	}

	/**
	 * @returns the acceleration in the y-axis in m/s^2
	 */
	public double getYAccel() {
		return accelerometer.getYAccel();
	}

	/**
	 * @returns the acceleration in the z-axis in m/s^2
	 */
	public double getZAccel() {
		return accelerometer.getZAccel();
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		accelerometer.destroy();
	}
}

	