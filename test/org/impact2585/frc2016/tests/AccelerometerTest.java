package org.impact2585.frc2016.tests;

import org.impact2585.frc2016.systems.AccelerometerSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The test for the AccelerometerSystem
 */
public class AccelerometerTest {
	private double xAccel;
	private double yAccel;
	private double zAccel;
	private AccelerometerSystem accel;
	
	/**
	 * Sets up the Accelerometer Test
	 */
	@Before
	public void setUp() {
		xAccel = 0;
		yAccel = 0;
		zAccel = 0;
		accel = new TestAccelerometer();
	}

	/**
	 * Currently tests if the accelerometer velocity calculations are correct
	 */
	@Test
	public void test() {
		accel.setPrevTime(System.currentTimeMillis());
		// tests that velocities are zero
		accel.run();
		Assert.assertTrue(accel.getXSpeed() == 0 && accel.getYSpeed() == 0 && accel.getZSpeed() == 0);
		
		//tests that the velocities increase if the accelerometer detects positive acceleration
		xAccel = 1;
		yAccel = 1;
		zAccel = 1;
		accel.setPrevTime(System.currentTimeMillis() - 20);
		accel.run();
		Assert.assertTrue(accel.getXSpeed() == accel.getChangedTime() && accel.getYSpeed() == accel.getChangedTime() && accel.getZSpeed() == accel.getChangedTime());
		Assert.assertTrue(accel.getChangedTime() == 0.02);
		
		//tests that the velocities decrease
		accel.reset();
		accel.setPrevTime(System.currentTimeMillis() - 20);
		xAccel = -1;
		yAccel = -1;
		zAccel = -1;
		accel.run();
		Assert.assertTrue(accel.getXSpeed() == -accel.getChangedTime() && accel.getYSpeed() == -accel.getChangedTime() && accel.getZSpeed() == -accel.getChangedTime());
		Assert.assertTrue(accel.getChangedTime() == 0.02);
		
		//tests that the velocities can change with differing acceleration
		accel.setPrevTime(System.currentTimeMillis() - 50);
		xAccel = 1;
		yAccel = 0.5;
		zAccel = 2;
		accel.run();
		Assert.assertTrue(accel.getXSpeed() == calculateVelocity(-0.02, 1, 0.05) && accel.getYSpeed() == calculateVelocity(-0.02, 0.5, 0.05) && accel.getZSpeed() == calculateVelocity(-0.02, 2, 0.05));
	}
	
	/**
	 * @param vinitial The velocity time seconds before
	 * @param acceleration the acceleration
	 * @param time the time between the initial velocity and the velocity now
	 * @return the new velocity
	 */
	public double calculateVelocity(double vinitial, double acceleration, double time) {
		return vinitial + acceleration * time;
	}

	/**
	 *Testable version of the Accelerometer System
	 */
	private class TestAccelerometer extends AccelerometerSystem {

		
		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#getRawValues()
		 */
		@Override
		public void readRawValues() {
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#getFIFOcount()
		 */
		@Override
		public short getFIFOcount() {
			return AccelerometerSystem.DMP_PACKET_SIZE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#
		 * accessSmartDashboard()
		 */
		@Override
		public void accessSmartDashboard() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#getXAccel()
		 */
		@Override
		public double getXAccel() {
			return xAccel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#getYAccel()
		 */
		@Override
		public double getYAccel() {
			return yAccel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.impact2585.frc2016.systems.AccelerometerSystem#getZAccel()
		 */
		@Override
		public double getZAccel() {
			return zAccel;
		}

	}

}
