package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.kauailabs.navx.frc.AHRS;
import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurntableControl {
	CANTalon turn;
	AHRS imu;
	AnalogGyro gyro;
	double reconciled;
	double setpoint;
	boolean setting;
	double maxSpeed = .5;
	double target;

	public TurntableControl(CANTalon turn, AHRS imu, AnalogGyro gyro) {
		this.turn = turn;
		this.imu = imu;
		this.gyro = gyro;
	}

	public Command teleopTurntable(InputDevice joy) {
		return new Command() {
			@Override
			public boolean execute() {
				reconciled = gyro.getAngle() + imu.getAngle();
				if (reconciled < -180)
					reconciled += 360;
				if (reconciled > 180)
					reconciled -= 360;
				SmartDashboard.putBoolean("clear", isClear());
				SmartDashboard.putNumber("gyro", -gyro.getAngle());
				SmartDashboard.putNumber("reconciled", reconciled);
				SmartDashboard.putNumber("imu", imu.getAngle());
				if (joy.getButton(10).isTriggered() /*|| joy.getButton(1).isTriggered()*/
						|| joy.getButton(3).isTriggered()) {
					setAngle(0);
				}
				if (joy.getButton(8).isTriggered()) {
					setting = false;
					gyro.calibrate();
					gyro.reset();
					imu.reset();
				}
				if (Math.abs(joy.getAxis(4).read()) > .1) {
					setting = false;
				}
				if (setting) {
					if (Math.abs(reconciled - target) < 1) {
						setting = false;
					} else {
						double setSpeed = Math.abs(reconciled - target) / 70;
						if (setSpeed > maxSpeed)
							setSpeed = maxSpeed;
						setSpeed *= Math.signum(reconciled - target);
						turn.set(setSpeed);
					}
				} else {
					turn.changeControlMode(TalonControlMode.PercentVbus);
					if (Math.abs(reconciled) >= 90) {
						if (Math.signum(joy.getAxis(4).read()) != -Math.signum(reconciled)) {
							turn.set(joy.getAxis(4).read() / 4);
						} else {
							turn.set(0);
						}
					} else {
						turn.set(joy.getAxis(4).read() / 4);
					}
				}
				return false;
			}
		};
	}

	public void moveAngle(double angle) {
		setAngle(reconciled + angle);
	}
	public Command moveAngleCommand(double angle){
		return Command.create(()->{
			moveAngle(angle);
			return true;
		});
	}
	public void setAngle(double angle) {
		target = angle;
		setting = true;
	}

	public boolean isClear() {
		return Math.abs(reconciled) < 5;
	}

}