package org.impact2585.frc2016.tests;


import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.WheelSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test for the wheel system
 */
public class WheelSystemTest {
	private TestWheelSystem drivetrain;
	private InputTest input;
	private double forwardDistance;
	private double rotationValue;
	
	
	/**
	 * Initializes the test wheel system and input
	 */
	@Before
	public void setUp() {
		drivetrain = new TestWheelSystem();
		input = new InputTest();
		drivetrain.setInput(input);
		forwardDistance = 0;
		rotationValue = 0;
	}

	/**
	 * Tests initial state, deadzone, rotating, and driving forward
	 */
	@Test
	public void test() {
				
		//tests if the robot isn't moving at the start
		drivetrain.run();
		Assert.assertTrue(forwardDistance == 0 && rotationValue == 0);
		
		//tests deadzone
		forwardDistance = 0.14;
		rotationValue = 0.14;
		drivetrain.run();
		Assert.assertTrue(forwardDistance == 0 && rotationValue == 0);
		
		//tests forward driving
		forwardDistance = 0.5;
		drivetrain.run();
		Assert.assertTrue(0.5 == forwardDistance && rotationValue == 0);
		
		//tests turning
		rotationValue = 1;
		forwardDistance = 0;
		drivetrain.run();
		Assert.assertTrue(forwardDistance == 0 && rotationValue == 1);
		
		//tests turning and driving simultaneously
		rotationValue = 1;
		forwardDistance = 10;
		Assert.assertTrue(forwardDistance == 10 && rotationValue == 1);
	}
	
	/**
	 * testable version of wheelsystem
	 */
	private class TestWheelSystem extends WheelSystem {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod controller) {
			super.setInput(controller);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#getInput()
		 */
		@Override
		public InputMethod getInput() {
			return super.getInput();
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#drive(double, double)
		 */
		@Override
		public void drive(double driveforward, double rotation) {
			forwardDistance = driveforward;
			rotationValue = rotation;
		}
		
	}
	
	/**
	 * input version for testing
	 */
	private class InputTest implements InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
		 */
		@Override
		public double forwardMovement() {
			return forwardDistance;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
		 */
		@Override
		public double rotationValue() {
			return rotationValue;
		}
		
	}
}

