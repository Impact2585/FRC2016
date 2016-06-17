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
	private boolean liftUp;
	private boolean liftDown;
	private double winchSpeed;
	private double liftSpeed;
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
		
		//tests to see if the winch winds and the system can lift
		windWinch = true;
		liftUp = true;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == LiftSystem.SPEED_MULTIPLIER && liftSpeed == LiftSystem.SPEED_MULTIPLIER);
		
		//test to see if the winch and lift motor won't run if Harris presses both input buttons
		unwindWinch = true;
		liftDown = true;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == 0 && liftSpeed == 0);
		
		//test to see if the winch and lift motor can unwind and move down
		windWinch = false;
		liftUp = false;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == -LiftSystem.SPEED_MULTIPLIER && liftSpeed == -LiftSystem.SPEED_MULTIPLIER);
		
		//test to see if the winch and lift motor can be set back to 0
		unwindWinch = false;
		liftDown = false;
		liftsystem.run();
		Assert.assertTrue(winchSpeed == 0 && liftSpeed == 0);
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

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.LiftSystem#setLiftMotorSpeed(double)
		 */
		@Override
		public void setLiftMotorSpeed(double speed) {
			liftSpeed = speed;
		}
		
	}
	
	/**
	 * testable input for the lift system
	 */
	private class InputTest extends InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#liftUp()
		 */
		@Override
		public boolean liftUp() {
			return liftUp;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#liftDown()
		 */
		@Override
		public boolean liftDown() {
			return liftDown;
		}

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
