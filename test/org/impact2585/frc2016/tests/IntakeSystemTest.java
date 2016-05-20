package org.impact2585.frc2016.tests;



import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.IntakeSystem;
import org.impact2585.lib2585.Toggler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * the test for the intake system
 */
public class IntakeSystemTest {
	private InputMethod input;
	private boolean digitalMoveArmAwayFromBot;
	private boolean digitalMoveArmTowardsBot;
	private double analogMoveArmAwayFromBot;
	private double analogMoveArmTowardsBot;
	private boolean moveWheelsForward;
	private boolean moveWheelsBackwards;
	private TestIntakeSystem ioshooter;
	private double wheelSpeed;
	private boolean toggleArmSpeed;
	private boolean isLeftLimitSwitchClosed;
	private boolean isRightLimitSwitchClosed;
	private boolean isShootingSwitchClosed;
	private boolean ignoreIntakeLimitSwitch;
	private boolean shoot;
	private boolean turnLeverReverse;
	private double leverSpeed;
	private boolean turnLeverForward;
	private boolean toggleManualIntake;
	private double rightArmSpeed;
	private double leftArmSpeed;
	private double leftIntakeSpeed;
	private double rightIntakeSpeed;
	
	
	/**
	 * sets up the intake system test
	 */
	@Before
	public void setUp() {
		ioshooter = new TestIntakeSystem();
		input = new InputTest();
		ioshooter.setInput(input);
		
		// initialize togglers
		ioshooter.setIntakeControlToggler(new Toggler(false));
		ioshooter.setLimitSwitchToggler(new Toggler(false));
		ioshooter.setSpeedToggler(new Toggler(false));
	}

	/**
	 * the tests to run for the intake system
	 */
	@Test
	public void test() {
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if the shooter shoots
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && sameIntakeSpeed(0));
		
		//tests if the intake intakes
		moveWheelsBackwards = true;
		moveWheelsForward = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == -1 && sameIntakeSpeed(0));
		
		//tests if the wheels don't move if the person pushes both buttons
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if the arms move forward
		moveWheelsForward = false;
		moveWheelsBackwards = false;
		digitalMoveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(IntakeSystem.ARM_SPEED));
		
		//tests if the arms move backwards
		digitalMoveArmAwayFromBot = false;
		digitalMoveArmTowardsBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(-IntakeSystem.ARM_SPEED));
		
		//tests if the arms don't move if the users pushes both buttons
		digitalMoveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if the arms and the wheels can both move simultaneously
		digitalMoveArmTowardsBot = false;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && sameIntakeSpeed(IntakeSystem.ARM_SPEED));
		
		//turn off the digital input
		moveWheelsForward = false;
		digitalMoveArmAwayFromBot = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if arms move away with the analog input
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(IntakeSystem.ARM_SPEED));
		
		//tests if the arms move towards the bot with analog input
		analogMoveArmTowardsBot = 1;
		analogMoveArmAwayFromBot = 0;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(-IntakeSystem.ARM_SPEED));
		
		//tests if the arms don't move at all if Harris pushes both triggers
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if the input arms can move while the input runs
		analogMoveArmAwayFromBot = 0;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && sameIntakeSpeed(-IntakeSystem.ARM_SPEED));
		
		//tests if the arm speed multiplier can be toggled off
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && leftIntakeSpeed == -1);
		
		//tests if the arm speed multiplier doesn't toggle if Harris keeps pushing the button
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && leftIntakeSpeed == -1);
		
		//tests if the arm speed multiplier toggles on
		moveWheelsForward = false;
		toggleArmSpeed = false;
		ioshooter.run();
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(-IntakeSystem.ARM_SPEED));
		
		//tests if the arm speed multiplier toggles off for digital input
		toggleArmSpeed = false;
		ioshooter.run();
		analogMoveArmTowardsBot = 0;
		digitalMoveArmTowardsBot = true;
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && leftIntakeSpeed == -1);
		
		//tests if the intake arms do not move forward if both limit switches are pressed
		isLeftLimitSwitchClosed = true;
		isRightLimitSwitchClosed = true;
		digitalMoveArmTowardsBot = false;
		analogMoveArmTowardsBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && sameIntakeSpeed(0));
		
		//tests if the intake arms do not move forward if only one limit switch is pressed and if the intake still works
		moveWheelsForward = true;
		isLeftLimitSwitchClosed = false;
		analogMoveArmAwayFromBot = 0;
		analogMoveArmTowardsBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && sameIntakeSpeed(0));
		
		//tests if the arms can move back if the limit switch is pressed
		analogMoveArmAwayFromBot = 1;
		analogMoveArmTowardsBot = 0;
		moveWheelsForward = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && leftIntakeSpeed == 1);
		
		//tests if the arms can move forward if the limit switch is no longer pressed
		analogMoveArmTowardsBot = 0.7;
		analogMoveArmAwayFromBot = 0;
		isRightLimitSwitchClosed = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && leftIntakeSpeed == -analogMoveArmTowardsBot);
		
		//tests if the arms can move forward if both limit switches and x button are pressed
		isLeftLimitSwitchClosed = true;
		isRightLimitSwitchClosed = true;
		ignoreIntakeLimitSwitch = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && leftIntakeSpeed == -analogMoveArmTowardsBot);
		
		//tests if the arms can move forward if one limit switch and the x button are pressed
		isLeftLimitSwitchClosed = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && leftIntakeSpeed == -analogMoveArmTowardsBot);
		
		//tests if the lever can automatically turn forward to shoot then reverse back after B button is pressed
		shoot = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 1.0);
		ioshooter.setStartTime(System.currentTimeMillis()-IntakeSystem.FORWARD_LEVER_TIME);
		ioshooter.run();
		Assert.assertTrue(leverSpeed == -1.0);
		
		//tests that the lever stops moving after the time
		ioshooter.setStartTime(System.currentTimeMillis() - (IntakeSystem.FORWARD_LEVER_TIME + IntakeSystem.BACKWARDS_LEVER_TIME));
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0);
		
		//tests if lever will turn in reverse if A button is pressed
		shoot = false;
		isShootingSwitchClosed = false;
		turnLeverReverse = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == -0.5 );
		
		//tests if lever will not run if neither B nor A buttons are pressed
		turnLeverReverse = false;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0);
		
		//tests if the lever will go forward if it gets forward input
		turnLeverForward = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0.5);
		
		//tests if forward input can interrupt the timed lever
		turnLeverForward = false;
		shoot = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 1);
		turnLeverForward = true;
		shoot = false;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0.5);
		turnLeverForward = false;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0);
		
		//tests if the limit switch is ignored for forward input
		turnLeverForward = true;
		isShootingSwitchClosed = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0.5);
		
		//tests if manual intake control works
		toggleManualIntake = true;
		rightArmSpeed = 0.5;
		leftArmSpeed = 0.5;
		ioshooter.run();
		Assert.assertTrue(sameIntakeSpeed(0.5));
		
		//tests if manual intake stays on
		toggleManualIntake = true;
		analogMoveArmAwayFromBot = 0;
		analogMoveArmTowardsBot = 0;
		digitalMoveArmAwayFromBot = false;
		digitalMoveArmTowardsBot = false;
		ioshooter.run();
		Assert.assertTrue(sameIntakeSpeed(0.5));
		toggleManualIntake = false;
		ioshooter.run();
		Assert.assertTrue(sameIntakeSpeed(0.5));
		
		//tests if manual intake can be toggled off
		toggleManualIntake = true;
		ioshooter.run();
		Assert.assertTrue(sameIntakeSpeed(0));
	}
	
	/**
	 * @param should the speed that both intake arms should be at
	 * @return true if both intake speeds equal should, false otherwise
	 */
	public boolean sameIntakeSpeed(double should){
		return leftIntakeSpeed == should && rightIntakeSpeed == should;
	}
	
	/**
	 * A testable intake system
	 */
	private class TestIntakeSystem extends IntakeSystem{

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#spinWheels(double)
		 */
		@Override
		public void spinWheels(double speed) {
			wheelSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isLeftSwitchClosed()
		 */
		@Override
		public boolean isLeftSwitchClosed() {
			return isLeftLimitSwitchClosed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isRightSwitchClosed()
		 */
		@Override
		public boolean isRightSwitchClosed() {
			return isRightLimitSwitchClosed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isShootingSwitchClosed()
		 */
		@Override
		public boolean isShootingSwitchClosed() {
			return isShootingSwitchClosed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected void setInput(InputMethod newInput) {
			super.setInput(newInput);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#spinLever(double)
		 */
		@Override
		public void spinLever(double speed) {
			leverSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setStartTime(long)
		 */
		@Override
		protected void setStartTime(long startTime) {
			super.setStartTime(startTime);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#accessSmartDashboard()
		 */
		@Override
		public void accessSmartDashboard() {
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#moveRightArm(double)
		 */
		@Override
		public void moveRightArm(double speed) {
			rightIntakeSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#moveLeftArm(double)
		 */
		@Override
		public void moveLeftArm(double speed) {
			leftIntakeSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setSpeedToggler(org.impact2585.lib2585.Toggler)
		 */
		@Override
		protected synchronized void setSpeedToggler(Toggler speedToggler) {
			super.setSpeedToggler(speedToggler);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setLimitSwitchToggler(org.impact2585.lib2585.Toggler)
		 */
		@Override
		protected synchronized void setLimitSwitchToggler(
				Toggler limitSwitchToggler) {
			super.setLimitSwitchToggler(limitSwitchToggler);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setIntakeControlToggler(org.impact2585.lib2585.Toggler)
		 */
		@Override
		protected synchronized void setIntakeControlToggler(
				Toggler intakeControlToggler) {
			super.setIntakeControlToggler(intakeControlToggler);
		}
		
		
	}	
	
	/**
	 * input version for testing
	 */
	private class InputTest extends InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#outake()
		 */
		@Override
		public boolean outake() {
			return moveWheelsBackwards;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#intake()
		 */
		@Override
		public boolean intake() {
			return moveWheelsForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeTowardsBot()
		 */
		@Override
		public boolean digitalMoveIntakeTowardsBot() {
			return digitalMoveArmTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeAwayFromBot()
		 */
		@Override
		public boolean digitalMoveIntakeAwayFromBot() {
			return digitalMoveArmAwayFromBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeTowardsBot()
		 */
		@Override
		public double analogMoveIntakeTowardsBot() {
			return analogMoveArmTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
		 */
		@Override
		public double analogMoveIntakeAwayFromBot() {
			return analogMoveArmAwayFromBot;
		}

		
		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#toggleIntakeArmSpeed()
		 */
		@Override
		public boolean toggleIntakeArmSpeed() {
			return toggleArmSpeed;
		}
		
		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#ignoreIntakeLimitSwitch()
		 */
		@Override
		public boolean toggleIntakeLimitSwitch(){
			return ignoreIntakeLimitSwitch;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#turnLever()
		 */
		@Override
		public boolean shoot() {
			return shoot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#turnLeverReverse()
		 */
		@Override
		public boolean turnLeverReverse(){
			return turnLeverReverse;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#turnLeverForward()
		 */
		@Override
		public boolean turnLeverForward() {
			return turnLeverForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#rightIntake()
		 */
		@Override
		public double rightIntake() {
			return rightArmSpeed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#leftIntake()
		 */
		@Override
		public double leftIntake() {
			return leftArmSpeed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#manualIntakeControl()
		 */
		@Override
		public boolean manualIntakeControl() {
			return toggleManualIntake;
		}
			
	}
	
}
