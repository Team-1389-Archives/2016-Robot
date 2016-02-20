package com.team1389.y2016.robot.test;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.Timer;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.webserver.chart.Chart;
import com.team1389.base.webserver.chart.DataPoint;
import com.team1389.base.webserver.chart.WebChartManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class PIDTestCommand extends Command{
	
	PIDConstants constants;
	CANTalon talon;
	InputDevice joystick;
	StringBuilder builder;
	Chart output;
	Timer timer;
	double target;
	boolean speedOrPosition;
	double mod;
	boolean decouple;
	
	public PIDTestCommand(PIDConstants constants, CANTalon talon, InputDevice joystick, double target, boolean speedOrPosition, double mod, boolean decouple) {
		this.constants = constants;
		this.talon = talon;
		this.joystick = joystick;
		this.builder = new StringBuilder();
		this.output = new Chart(.001, .1, "time", "output");
		WebChartManager.getInstance().addChart("pidTester", output);
		timer = new Timer();
		this.target = target;
		this.speedOrPosition = speedOrPosition;
		this.mod = mod;
		this.decouple = decouple;
	}
	
	@Override
	public void initialize() {
		StringBuilder builder = new StringBuilder();
		builder.append("p=");
		builder.append(constants.p);
		builder.append(" i=");
		builder.append(constants.i);
		builder.append(" d=");
		builder.append(constants.d);
		builder.append(" kv=");
		builder.append(constants.kv);
		builder.append(" ka=");
		builder.append(constants.ka);
		builder.append(" target=");
		builder.append(target);
		System.out.println(builder.toString());
		
		talon.configNominalOutputVoltage(0, 0);
		talon.configMaxOutputVoltage(12);
		
		talon.setProfile(0);//sets which pid gains to use
		talon.setP(constants.p);
		talon.setI(constants.i);
		talon.setD(constants.d);
		talon.setF(constants.kv);
		
		timer.zero();
	}

	@Override
	public boolean execute() {
		/* get gamepad axis */
    	double leftYstick = joystick.getAxis(1).read();
    	double motorOutput = talon.getOutputVoltage() / talon.getBusVoltage();
    	/* prepare line to print */
		builder.append("\tout:");
		builder.append(motorOutput);
		if (speedOrPosition){
			builder.append("\tspd:");
			builder.append(talon.getSpeed());
		} else {
			builder.append("\tpos:");
			builder.append(talon.getPosition());
		}
        
        if(joystick.getButton(1).isTriggered()){
        	double target = leftYstick * this.target; /* 1500 RPM in either direction */
        	talon.changeControlMode(speedOrPosition? TalonControlMode.Speed : TalonControlMode.Position);
        	if (!decouple){
        		talon.setP(0);
        		talon.setI(0);
        		talon.setD(0);
        		talon.setF(0);
        	}
       		talon.set(target * mod); /* 1500 RPM in either direction */
        	builder.append("\tset:");
        	builder.append(target);

        	/* append more signals to print when in speed mode. */
            builder.append("\terr:");
            builder.append(talon.getClosedLoopError());
            builder.append("\ttrg:");
            builder.append(target);
            builder.append("\tstpt:");
            builder.append(talon.getSetpoint());
            
            output.addPoint(new DataPoint(timer.get(), talon.getPosition()));
        } else {
        	/* Percent voltage mode */
        	talon.changeControlMode(TalonControlMode.PercentVbus);
        	if (!decouple){
        		talon.set(leftYstick);
        	}
        	builder.append("\tset:");
        	builder.append(leftYstick);
        	builder.append("\tpvb=");
        	builder.append(leftYstick);
        }
        
        if (joystick.getButton(2).isTriggered()){
        	talon.setPosition(0);
        }

        System.out.println(builder.toString());

        builder.setLength(0);
		
		return false;
	}

}
