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
	private boolean digitalTopArmForward;
	private boolean digitalTopArmBackward;
	private double analogTopArmForward;
	private double reversibleBottomArm;
	private TestArmSystem arm;
	private InputMethod input;
	private boolean toggleSpeed;

	/**
	 * sets up the test
	 */
	@Before
	public void setUp() {
		topArmSpeed = 0;
		bottomArmSpeed = 0;
		bottomArmForwardValue = 0;
		bottomArmBackwardValue = 0;
		analogTopArmForward = 0;
		reversibleBottomArm = 0;
		input = new InputTest();
		arm = new TestArmSystem();
		arm.setInput(input);
		toggleSpeed = false;
	}

	/**
	 * runs various tests for the arm system
	 */
	@Test
	public void test() {

		//tests if the arms do not move initially
		digitalTopArmForward = false;
		digitalTopArmBackward = false;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the top arm moves
		digitalTopArmForward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == 0);

		//tests if the top arm moves backwards
		digitalTopArmForward = false;
		digitalTopArmBackward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == -ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == 0);

		//tests if the top arm does not move if both top arm input buttons are pressed
		digitalTopArmForward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the bottom arm moves forward
		bottomArmForwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == ArmSystem.BOTTOM_ARM_SPEED);

		//tests if the bottom arm moves backwards
		bottomArmForwardValue = 0;
		bottomArmBackwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == -ArmSystem.BOTTOM_ARM_SPEED);

		//tests if the bottom arm doesn't move if the both of the buttons for the bottom arm are pressed
		bottomArmForwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);

		//tests if the bottom and top arm can move simultaneously
		bottomArmBackwardValue = 0;
		digitalTopArmBackward = false;
		arm.run();
		Assert.assertTrue(topArmSpeed == ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == ArmSystem.BOTTOM_ARM_SPEED);
		
		//turns off the digital input
		bottomArmForwardValue = 0;
		digitalTopArmForward = false;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);
		
		//tests the analog input moving the top arm
		analogTopArmForward = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == analogTopArmForward * ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == 0);
		
		//tests if the input returns 0 if analog input and digital input are both used
		digitalTopArmForward = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == 0);
		
		//tests if the analog input moves the bottom arm
		digitalTopArmForward = false;
		analogTopArmForward = 0;
		reversibleBottomArm = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == reversibleBottomArm * ArmSystem.BOTTOM_ARM_SPEED);
		
		//tests if the bottom arm can move backwards with analog input
		reversibleBottomArm = -0.5;
		arm.run();
		Assert.assertTrue(topArmSpeed == 0 && bottomArmSpeed == reversibleBottomArm * ArmSystem.BOTTOM_ARM_SPEED);
		
		//tests if the top arm can move backwards with analog input while the bottom arm is moving
		analogTopArmForward = -1;
		arm.run();
		Assert.assertTrue(topArmSpeed == analogTopArmForward * ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == reversibleBottomArm * ArmSystem.BOTTOM_ARM_SPEED);
		
		//tests if the the speed multiplier is turned off
		reversibleBottomArm = 0;
		analogTopArmForward = 0;
		digitalTopArmForward = true;
		toggleSpeed = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == 1 && bottomArmBackwardValue == 0);
		
		//tests if the speed does not toggle if Harris keeps holding down the button
		arm.run();
		Assert.assertTrue(topArmSpeed == 1 && bottomArmBackwardValue == 0);
		
		//tests if the speed multiplier is not toggled and if it works for analog input
		toggleSpeed = false;
		digitalTopArmForward = false;
		analogTopArmForward = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == analogTopArmForward && bottomArmBackwardValue == 0);
		
		//tests if the speed multiplier can be toggled on and if it works for the bottom arm 
		toggleSpeed = true;
		bottomArmForwardValue = 1;
		arm.run();
		Assert.assertTrue(topArmSpeed == analogTopArmForward * ArmSystem.TOP_ARM_SPEED && bottomArmSpeed == bottomArmForwardValue * ArmSystem.BOTTOM_ARM_SPEED);
		
		//tests if the speed multiplier can be toggled off for the bottom and top arm
		toggleSpeed = false;
		arm.run();
		bottomArmForwardValue = 0;
		reversibleBottomArm = -1;
		toggleSpeed = true;
		arm.run();
		Assert.assertTrue(topArmSpeed == analogTopArmForward && bottomArmSpeed == reversibleBottomArm);
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
	private class InputTest extends InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalTopArmForward()
		 */
		@Override
		public boolean digitalTopArmForward() {
			return digitalTopArmForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalTopArmBackward()
		 */
		@Override
		public boolean digitalTopArmBackward() {
			return digitalTopArmBackward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#backArmForwardValue()
		 */
		@Override
		public double bottomArmForwardValue() {
			return bottomArmForwardValue;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#bottomArmBackwardValue()
		 */
		@Override
		public double bottomArmBackwardValue() {
			return bottomArmBackwardValue;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogTopArm()
		 */
		@Override
		public double analogTopArm() {
			return analogTopArmForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#reversibleBottomArmValue()
		 */
		@Override
		public double reversibleBottomArmValue() {
			return reversibleBottomArm;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#toggleSpeed()
		 */
		@Override
		public boolean toggleSpeed() {
			return toggleSpeed;
		}
		
		
	}

}
