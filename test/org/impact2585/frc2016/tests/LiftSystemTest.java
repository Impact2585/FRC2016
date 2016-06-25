package org.impact2585.frc2016.tests;


import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.LiftSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * unit test for the lift system
 */
public class LiftSystemTest {
	private boolean windWinch;
	private boolean unwindWinch;
	private double winchSpeed;
	private TestLiftSystem liftsystem;
	private InputMethod input;
	
	/**
	 * Set up the lift system test
	 */
	@Before
	public void setUp() {
		input = new InputTest();
		liftsystem = new TestLiftSystem();
		liftsystem.setInput(input);
	}

	/**
	 * test the lift system
	 */
	@Test
	public void test() {
		
		//tests to see if the winch winds
		windWinch = true;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == LiftSystem.SPEED_MULTIPLIER);
		
		//test to see if the winch motor won't run if Harris presses both input buttons
		unwindWinch = true;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == 0);
		
		//test to see if the winch motor can unwind and move down
		windWinch = false;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == -LiftSystem.SPEED_MULTIPLIER);
		
		//test to see if the winch can be set back to 0
		unwindWinch = false;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == 0);
	}
	
	/**
	 * testable lift system
	 */
	private class TestLiftSystem extends LiftSystem {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.LiftSystem#setWinchMotorSpeed(double)
		 */
		@Override
		public void setWinchMotorSpeed(double speed) {
			winchSpeed = speed;
		}
		
	}
	
	/**
	 * testable input for the lift system
	 */
	private class InputTest extends InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#unwindWinch()
		 */
		@Override
		public boolean unwindWinch() {
			return unwindWinch;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#windWinch()
		 */
		@Override
		public boolean windWinch() {
			return windWinch;
		}	
	}
}
