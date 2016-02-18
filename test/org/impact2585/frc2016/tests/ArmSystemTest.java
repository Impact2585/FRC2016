package org.impact2585.frc2016.tests;

import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.ArmSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * the unit test for the arm system
 */
public class ArmSystemTest {
	private double topArmSpeed;
	private double bottomArmSpeed;
	private double bottomArmForwardValue;
	private double bottomArmBackwardValue;
	private boolean topArmForward;
	private boolean topArmBackward;
	private TestArmSystem arm;
	private InputMethod input;

	/**
	 * sets up the test
	 */
	@Before
	public void setUp() {
		topArmSpeed = 0;
		bottomArmSpeed = 0;
		bottomArmForwardValue = 0;
		bottomArmBackwardValue = 0;
		input = new InputTest();
		arm = new TestArmSystem();
		arm.setInput(input);		
	}

	/**
	 * runs various tests for the arm system
	 */
	@Test
	public void test() {

		//tests if the arms do not move initially
		topArmForward = false;
		topArmBackward = false;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the top arm moves
		topArmForward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0.5 && bottomArmSpeed == 0);

		//tests if the top arm moves backwards
		topArmForward = false;
		topArmBackward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == -0.5 && bottomArmSpeed == 0);

		//tests if the top arm does not move if both top arm input buttons are pressed
		topArmForward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the bottom arm moves forward
		bottomArmForwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0.7);

		//tests if the bottom arm moves backwards
		bottomArmForwardValue = 0;
		bottomArmBackwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == -0.7);

		//tests if the bottom arm doesn't move if the both of the buttons for the bottom arm are pressed
		bottomArmForwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the bottom and top arm can move simultaneously
		bottomArmBackwardValue = 0;
		topArmBackward = false;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0.5 && bottomArmSpeed == 0.7);
	}

	/**
	 * testable version of the arm system
	 */
	private class TestArmSystem extends ArmSystem {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.ArmSystem#setTopArmSpeed(double)
		 */
		@Override
		public void setTopArmSpeed(double speed) {
			topArmSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.ArmSystem#setBottomArmSpeed(double)
		 */
		@Override
		public void setBottomArmSpeed(double speed) {
			bottomArmSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.ArmSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected void setInput(InputMethod newInput) {
			super.setInput(newInput);
		}

	}

	/**
	 * input version for testing
	 */
	private class InputTest implements InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#topArmForward()
		 */
		@Override
		public boolean topArmForward() {
			return topArmForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#topArmBackward()
		 */
		@Override
		public boolean topArmBackward() {
			return topArmBackward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#backArmForwardValue()
		 */
		@Override
		public double backArmForwardValue() {
			return bottomArmForwardValue;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#backArmBackwardValue()
		 */
		@Override
		public double backArmBackwardValue() {
			return bottomArmBackwardValue;
		}

	}

}
